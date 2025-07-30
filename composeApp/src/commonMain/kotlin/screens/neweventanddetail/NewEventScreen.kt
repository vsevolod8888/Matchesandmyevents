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
import app_b_0305_24.composeapp.generated.resources.newevent
import app_b_0305_24.composeapp.generated.resources.save
import data.dto.getTimeZoneOffsetInMilliSeconds
import data.room.EventEntity
import io.wojciechosak.calendar.utils.today
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import moe.tlaster.precompose.koin.koinViewModel
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import screens.commonelements.RedButton
import screens.commonelements.ToolBar
import screens.main.item.ChoosenWeekItemSmall
import screens.neweventanddetail.items.ItemChooseColour
import screens.neweventanddetail.items.ItemChooseTime
import screens.neweventanddetail.items.LimitedTextField
import theme.AccentPrimaryTwo
import theme.LayerOne
import theme.LayerThree
import theme.TextSecondary
import theme.TextStyleLocal
import theme.TextWhite
import utils.toTitleCase

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NewEventScreen(onClickBackFromNewEvent: () -> Unit) {
    val viewModel: NewEventViewModel = koinViewModel(NewEventViewModel::class)
    var textTittle by remember { mutableStateOf("") }
    var textDescription by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    var scope = rememberCoroutineScope()
    val maxNumbersEditTextTittle = 25
    val today = LocalDate.today()
    val maxDate = today.plus(13, DateTimeUnit.DAY)
    var isScrolling by remember { mutableStateOf(false) }
    var expandedTime by remember { mutableStateOf(false) }


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


    val indexOfColour = remember { mutableStateOf(1) }
    val startHours = remember { mutableStateOf(0) }
    val startMinutes = remember { mutableStateOf(0) }
    val endHours = remember { mutableStateOf(0) }
    val endMinutes = remember { mutableStateOf(0) }


    var showErrorTimeDialog by remember { mutableStateOf(false) }

    var areFieldsFilled by remember {
        mutableStateOf(false)
    }

    fun checkFieldsFilled() {
        areFieldsFilled =
            textTittle.isNotEmpty() && textDescription.isNotEmpty() && dayUserSelected != "Pick a day"
    }

    Column(modifier = Modifier.fillMaxSize().clickable { keyboardController?.hide() }) {
        ToolBar(
            stringResource(Res.string.newevent),
            onArrowBackClick = { onClickBackFromNewEvent() },
            goToDeleteDialog = { },
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
                                if (it.length <= maxNumbersEditTextTittle) textTittle = it
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
                                    text = "Name event",
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
                                                }
//                                            delay(250)
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
                                                }
//                                            delay(250)
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
                                                modifier = Modifier.padding(start = 16.dp).fillMaxWidth()
                                                    .wrapContentHeight(),
                                                text = label,
                                                color = if (dayUserSelected == label) TextWhite else TextSecondary,
                                                style = if (dayUserSelected == label) TextStyleLocal.semibold18 else TextStyleLocal.regular16,
                                                textAlign = TextAlign.Start
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
                    onChooseTime = {}
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

                        LimitedTextField(
                            textDescription = textDescription,
                            onTextChange = {
                                textDescription = it
                                checkFieldsFilled()
                            },
                            maxNumbersEditTextText = 140 // Example max characters
                        )
                    }
                    Spacer(modifier = Modifier.height(70.dp))
                }
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
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
                            val currentDayOfWeek = selectedDayBeginingOfTheCalendar?.dayOfWeek
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

                            if (LocalDateTime.parse(timeEnd) <= LocalDateTime.parse(timeBegin)) {
                                showErrorTimeDialog = true
                            } else {
                                showErrorTimeDialog = false
                                scope.launch(Dispatchers.IO) {
                                    var eventEntity = selectedDate?.toEpochDays()?.let {
                                        EventEntity(
                                            epochDays = it,
                                            startTimeTimeStamp = convertToTimestamp(timeBegin) - getTimeZoneOffsetInMilliSeconds(),
                                            endTimeTimeStamp = convertToTimestamp(timeEnd) - getTimeZoneOffsetInMilliSeconds(),
                                            tittle = textTittle,
                                            textEvent = textDescription,
                                            colour = indexOfColour.value
                                        )
                                    }

                                    if (eventEntity != null) {
                                        viewModel.insertEvent(eventEntity)
                                    }
                                }
                                onClickBackFromNewEvent()
                            }


                        }

                    },
                    isAllFieldsAllowed = areFieldsFilled
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
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
}


fun convertToTimestamp(dateTimeString: String): Long {
    val localDateTime = LocalDateTime.parse(dateTimeString)
    val instant = localDateTime.toInstant(TimeZone.UTC)
    return instant.toEpochMilliseconds()
}

fun convertFromTimestamp(timestamp: Long): String {
    val instant = Instant.fromEpochMilliseconds(timestamp)
    val localDateTime = instant.toLocalDateTime(TimeZone.UTC)
    return localDateTime.toString()
}