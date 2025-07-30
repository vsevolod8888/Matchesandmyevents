package screens.neweventanddetail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app_b_0305_24.composeapp.generated.resources.Res
import app_b_0305_24.composeapp.generated.resources.detailinfo
import app_b_0305_24.composeapp.generated.resources.pinanevent
import app_b_0305_24.composeapp.generated.resources.save
import app_b_0305_24.composeapp.generated.resources.theeventhasbeensuccesfullypinned
import app_b_0305_24.composeapp.generated.resources.theeventhasbeensuccesfullyremoved
import app_b_0305_24.composeapp.generated.resources.unpintheevent
import data.dto.getTimeZoneOffsetInMilliSeconds
import data.room.EventEntity
import io.wojciechosak.calendar.utils.today
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import moe.tlaster.precompose.koin.koinViewModel
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import screens.commonelements.RedButton
import screens.commonelements.ToolBar
import screens.commonelements.PopUpPin
import screens.main.item.ChoosenWeekItemSmall
import screens.neweventanddetail.items.ItemChooseColour
import screens.neweventanddetail.items.ItemChooseTime
import screens.neweventanddetail.items.LimitedTextFieldDet
import theme.AccentPrimaryTwo
import theme.BackgroundMain
import theme.LayerOne
import theme.LayerThree
import theme.TextSecondary
import theme.TextStyleLocal
import theme.TextWhite
import utils.toTitleCase

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DetailEventScreen(onClickBackFromDetailEvent: () -> Unit) {
    val viewModel: DetailEventViewModel = koinViewModel(DetailEventViewModel::class)
    var idGame by remember { mutableStateOf(viewModel.getEventId()) }
    var selectedEventToShow by remember { mutableStateOf<EventEntity?>(null) }

    LaunchedEffect(Unit) {
        selectedEventToShow = viewModel.getEventById(idGame)
    }

    var textTittle by remember { mutableStateOf("") }
    var textDescription by remember { mutableStateOf("") }


    val keyboardController = LocalSoftwareKeyboardController.current
    var scope = rememberCoroutineScope()
    val maxNumbersEditTextTittle = 25
    val today = LocalDate.today()
    val maxDate = today.plus(13, DateTimeUnit.DAY)
    var isScrolling by remember { mutableStateOf(false) }
    var expandedTime by remember { mutableStateOf(false) }
    var toShowPopUpPinned = remember { mutableStateOf(false) }

    var selectedDayBeginingOfTheCalendar by remember { mutableStateOf<LocalDate?>(LocalDate.today()) }
    var dayUserSelected by remember { mutableStateOf("Pick a day") }
    val suggestionsDays = listOf(
        "Monday",
        "Tuesday",
        "Wednesday",
        "Thursday",
        "Friday",
        "Saturday",
        "Sunday",
    )

    var selectedDate by remember { mutableStateOf<LocalDate?>(LocalDate.today()) }

    var indexOfColour = remember { mutableStateOf(1) }
    val startHours = remember { mutableStateOf(0) }
    val startMinutes = remember { mutableStateOf(0) }
    val endHours = remember { mutableStateOf(0) }
    val endMinutes = remember { mutableStateOf(0) }

    var showErrorTimeDialog by remember { mutableStateOf(false) }
    var showDeleteEventDialog by remember { mutableStateOf(false) }
    var isPinned = remember { mutableStateOf(false) }
    var areFieldsFilled by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(selectedEventToShow) {
        isPinned.value = selectedEventToShow?.isEventWasPinned ?: false
    }
    LaunchedEffect(selectedEventToShow) {
        textTittle = selectedEventToShow?.tittle.toString()
    }
    LaunchedEffect(selectedEventToShow) {
        indexOfColour.value = selectedEventToShow?.colour ?: 0
    }
    LaunchedEffect(selectedEventToShow) {
        textDescription = selectedEventToShow?.textEvent.toString()
    }
    LaunchedEffect(selectedEventToShow) {
        val stringEventDateStart = selectedEventToShow?.startTimeTimeStamp?.let {
            convertFromTimestamp(it + getTimeZoneOffsetInMilliSeconds())
        }

        stringEventDateStart?.let { dateString ->
            val timePart = dateString.substringAfter('T') // "00:00:00"
            val (hours, minutes) = timePart.split(':').map { it.toInt() }

            startHours.value = hours
            startMinutes.value = minutes
        }
    }
    LaunchedEffect(selectedEventToShow) {
        val stringEventDateEnd = selectedEventToShow?.endTimeTimeStamp?.let {
            convertFromTimestamp(it + getTimeZoneOffsetInMilliSeconds())
        }

        stringEventDateEnd?.let { dateString ->
            val timePart = dateString.substringAfter('T') // "00:00:00"
            val (hours, minutes) = timePart.split(':').map { it.toInt() }

            endHours.value = hours
            endMinutes.value = minutes
        }
    }
    LaunchedEffect(selectedEventToShow) {
        selectedEventToShow?.epochDays?.let {
            val eventDate = LocalDate.fromEpochDays(it)
            dayUserSelected = eventDate.dayOfWeek.name.toLowerCase().capitalize()
        }
    }
    LaunchedEffect(selectedEventToShow) {
        selectedEventToShow?.epochDays?.let { eventEpochDays ->
            val eventDate = LocalDate.fromEpochDays(eventEpochDays)
            val today = LocalDate.today()

            if (eventDate.toEpochDays() - today.toEpochDays() > 6) {
                selectedDayBeginingOfTheCalendar =
                    selectedDayBeginingOfTheCalendar?.plus(1, DateTimeUnit.WEEK)
            }
        }
    }

    fun checkFieldsFilled() {
        areFieldsFilled =
            textTittle.isNotEmpty() && textDescription.isNotEmpty() && dayUserSelected != "Pick a day"
    }

    Column(modifier = Modifier.fillMaxSize().clickable { keyboardController?.hide() }) {
        ToolBar(
            stringResource(Res.string.detailinfo),
            onArrowBackClick = { onClickBackFromDetailEvent() },
            goToDeleteDialog = { showDeleteEventDialog = true },
            goToSettings = { },
        )
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(LayerOne),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp)
                            .fillMaxWidth()
                            .height(56.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .border(
                                1.dp, LayerThree, RoundedCornerShape(20.dp)
                            )
                            .background(LayerOne),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextField(
                            value = textTittle,
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent,
                                cursorColor = TextWhite,
                                focusedTextColor = TextWhite,
                                unfocusedTextColor = TextWhite,
                                disabledTextColor = TextWhite,
                            ),

                            textStyle = TextStyleLocal.regular16,
                            onValueChange = {
                                if (it.length.toInt() <= maxNumbersEditTextTittle) textTittle = it
                                checkFieldsFilled()
                            },
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    keyboardController?.hide()
                                }
                            ),
                            singleLine = true,
                            modifier = Modifier
                                .wrapContentSize(),
                            placeholder = {
                                Text(
                                    text = textDescription,
                                    modifier = Modifier.fillMaxHeight()
                                        .wrapContentWidth()
                                        .padding(start = 0.dp, top = 3.dp),
                                    color =
                                    TextWhite,
                                    style = TextStyleLocal.regular16,
                                )
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password
                            )
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(83.dp)
                        .background(LayerOne),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp)
                            .fillMaxWidth()
                            .height(65.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .border(
                                1.dp, LayerThree, RoundedCornerShape(20.dp)
                            )
                            .background(LayerOne),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        val clickMutex = Mutex()
                        Spacer(modifier = Modifier.height(6.dp))
                        ChoosenWeekItemSmall(
                            onClickPrevious = {
                                if (!isScrolling) {
                                    isScrolling = true
                                    scope.launch(Dispatchers.IO) {
                                        try {
                                            clickMutex.withLock {
                                                val newDate =
                                                    selectedDayBeginingOfTheCalendar?.minus(
                                                        1,
                                                        DateTimeUnit.WEEK
                                                    )
                                                if (newDate != null && newDate >= today) {
                                                    selectedDayBeginingOfTheCalendar = newDate
                                                    checkFieldsFilled()
                                                }

                                            }
                                        } catch (e: Exception) {
                                            e.printStackTrace()
                                        } finally {
                                            isScrolling = false
                                        }
                                    }
                                }
                            },
                            onClickNext = {
                                if (!isScrolling) {
                                    isScrolling = true
                                    scope.launch(Dispatchers.IO) {
                                        try {
                                            clickMutex.withLock {
                                                val newDate =
                                                    selectedDayBeginingOfTheCalendar?.plus(
                                                        1,
                                                        DateTimeUnit.WEEK
                                                    )
                                                if (newDate != null && newDate <= maxDate) {
                                                    selectedDayBeginingOfTheCalendar = newDate
                                                    checkFieldsFilled()
                                                }

                                            }
                                        } catch (e: Exception) {
                                            e.printStackTrace()
                                        } finally {
                                            isScrolling = false
                                        }
                                    }
                                }
                            },
                            name = "${selectedDayBeginingOfTheCalendar?.dayOfMonth.toString()} ${
                                selectedDayBeginingOfTheCalendar?.month.toString().toTitleCase()
                                    .take(3)
                            } - ${
                                selectedDayBeginingOfTheCalendar?.plus(
                                    6,
                                    DateTimeUnit.DAY
                                )?.dayOfMonth.toString()
                            } ${
                                selectedDayBeginingOfTheCalendar?.plus(
                                    6,
                                    DateTimeUnit.DAY
                                )?.month.toString().toTitleCase()
                                    .take(3)
                            }, ${selectedDayBeginingOfTheCalendar?.year}",

                            isCurrentWeek = if (selectedDayBeginingOfTheCalendar == LocalDate.today()) false else true
                        )

                        Button(
                            onClick = { expandedTime = !expandedTime },
                            colors = ButtonDefaults.buttonColors(LayerOne),
                            modifier = Modifier.fillMaxHeight().width(300.dp)
                        ) {
                            Text(
                                dayUserSelected,
                                style = TextStyleLocal.semibold18,
                                color = if (dayUserSelected == "Pick a day") LayerThree else TextWhite,
                                textAlign = TextAlign.Center
                            )
                        }

                        Row(
                            modifier = Modifier
                                .width(300.dp)
                                .background(LayerOne)
                                .align(Alignment.CenterHorizontally),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            DropdownMenu(
                                expanded = expandedTime,
                                onDismissRequest = { expandedTime = false },
                                modifier = Modifier
                                    .width(300.dp)
                                    .background(LayerThree)
                            ) {

                                suggestionsDays.forEach { label ->
                                    DropdownMenuItem(
                                        modifier = Modifier.background(if (dayUserSelected == label) AccentPrimaryTwo else LayerThree),
                                        text = {
                                            Text(
                                                modifier = Modifier.fillMaxWidth()
                                                    .wrapContentHeight(),
                                                text = label,
                                                color = if (dayUserSelected == label) TextWhite else TextSecondary,
                                                style = if (dayUserSelected == label) TextStyleLocal.semibold18 else TextStyleLocal.regular16,
                                                textAlign = TextAlign.Center
                                            )
                                        }, onClick = {
                                            expandedTime = false
                                            dayUserSelected = label
                                            checkFieldsFilled()
                                        })
                                }
                            }
                        }
                    }
                }

                ItemChooseTime(
                    hoursStart = startHours,
                    minutesStart = startMinutes,
                    hoursEnd = endHours,
                    minutesEnd = endMinutes,
                    onChooseTime = {
                        checkFieldsFilled()
                    }
                )

                ItemChooseColour(
                    colourIndex = indexOfColour,
                    onChooseColour = { checkFieldsFilled() })

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {

                    Row(
                        modifier = Modifier.padding(16.dp)
                            .fillMaxWidth()
                            .height(140.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .border(
                                1.dp, LayerThree, RoundedCornerShape(20.dp)
                            ),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        LimitedTextFieldDet(
                            textDescription = textDescription,
                            onTextChange = {
                                textDescription = it
                                checkFieldsFilled()
                            },
                            maxNumbersEditTextText = 140 // Example max characters
                        )
                    }
                    Spacer(modifier = Modifier.height(149.dp))
                }

            }
            Column(//buttons
                modifier = Modifier.fillMaxWidth().wrapContentHeight().background(BackgroundMain),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(19.dp))
                RedButton(
                    if (isPinned.value)
                        stringResource(Res.string.unpintheevent)
                    else
                        stringResource(Res.string.pinanevent),
                    onClick = {
                        if (isPinned.value) {
                            toShowPopUpPinned.value = true
                            scope.launch {
                                selectedEventToShow?.let {
                                    viewModel.updateIsEventWasPinned(
                                        it.id,
                                        false
                                    )
                                }
                                isPinned.value = false
                                delay(500)
                                toShowPopUpPinned.value = false
                            }
                        } else {
                            scope.launch {
                                toShowPopUpPinned.value = true
                                selectedEventToShow?.let {
                                    viewModel.updateIsEventWasPinned(
                                        it.id,
                                        true
                                    )
                                }
                                isPinned.value = true
                                delay(500)
                                toShowPopUpPinned.value = false
                            }
                        }
                    },
                    isAllFieldsAllowed = true
                )
                RedButton(
                    stringResource(Res.string.save),
                    onClick = {
                        val selectedDayOfWeek = when (dayUserSelected) {
                            "Monday" -> DayOfWeek.MONDAY
                            "Tuesday" -> DayOfWeek.TUESDAY
                            "Wednesday" -> DayOfWeek.WEDNESDAY
                            "Thursday" -> DayOfWeek.THURSDAY
                            "Friday" -> DayOfWeek.FRIDAY
                            "Saturday" -> DayOfWeek.SATURDAY
                            "Sunday" -> DayOfWeek.SUNDAY
                            else -> null
                        }
                        selectedDayOfWeek?.let { dayOfWeek ->
                            val currentDayOfWeek =
                                selectedDayBeginingOfTheCalendar?.dayOfWeek
                            val daysDifference =
                                (dayOfWeek.ordinal - currentDayOfWeek!!.ordinal + 7) % 7
                            selectedDate =
                                selectedDayBeginingOfTheCalendar?.plus(
                                    daysDifference,
                                    DateTimeUnit.DAY
                                )
                        }

                        if (areFieldsFilled) {
                            val timeBegin = "${selectedDate}T${
                                startHours.value.toString().padStart(2, '0')
                            }:${startMinutes.value.toString().padStart(2, '0')}:00"

                            val timeEnd = "${selectedDate}T${
                                endHours.value.toString().padStart(2, '0')
                            }:${endMinutes.value.toString().padStart(2, '0')}:00"

                            if (LocalDateTime.parse(timeEnd) <= LocalDateTime.parse(
                                    timeBegin
                                )
                            ) {
                                showErrorTimeDialog = true
                            } else {
                                showErrorTimeDialog = false
                                scope.launch(Dispatchers.IO) {
                                    var eventEntity = selectedDate?.toEpochDays()?.let {
                                        selectedEventToShow?.let { it1 ->
                                            EventEntity(
                                                id = it1.id,
                                                isEventWasPinned =isPinned.value,
                                                epochDays = it,
                                                startTimeTimeStamp = convertToTimestamp(
                                                    timeBegin
                                                ) - getTimeZoneOffsetInMilliSeconds(),
                                                endTimeTimeStamp = convertToTimestamp(
                                                    timeEnd
                                                ) - getTimeZoneOffsetInMilliSeconds(),
                                                tittle = textTittle,
                                                textEvent = textDescription,
                                                colour = indexOfColour.value
                                            )
                                        }
                                    }

                                    if (eventEntity != null) {
                                        viewModel.insertEvent(eventEntity)
                                    }
                                }
                                onClickBackFromDetailEvent()
                            }
                        }
                    },
                    isAllFieldsAllowed = areFieldsFilled
                )
                Spacer(modifier = Modifier.height(10.dp))
            }

            if (toShowPopUpPinned.value){
                PopUpPin(if (isPinned.value)stringResource(Res.string.theeventhasbeensuccesfullypinned)else stringResource(Res.string.theeventhasbeensuccesfullyremoved))

            }
        }
        if (showErrorTimeDialog) {
            ErrorTimeDialog(
                onConfirm = {
                    showErrorTimeDialog = false
                },
                onDismiss = {
                    showErrorTimeDialog = false
                }
            )
        }
        if (showDeleteEventDialog) {
            DeleteEventDialog(
                onConfirm = {
                    scope.launch {
                        selectedEventToShow?.id?.let { viewModel.deleteEventById(it) }
                        onClickBackFromDetailEvent()
                    }

                    showDeleteEventDialog = false
                },
                onDismiss = {
                    showDeleteEventDialog = false
                }
            )
        }
    }
}

