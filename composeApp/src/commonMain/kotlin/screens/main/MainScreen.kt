package screens.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app_b_0305_24.composeapp.generated.resources.Res
import app_b_0305_24.composeapp.generated.resources.loadingevents
import app_b_0305_24.composeapp.generated.resources.scheduletoday
import app_b_0305_24.composeapp.generated.resources.workschedule
import data.room.BigItemDataClass
import data.room.EventEntity
import data.room.GameEntity
import io.wojciechosak.calendar.config.DayState
import io.wojciechosak.calendar.utils.toMonthYear
import io.wojciechosak.calendar.utils.today
import io.wojciechosak.calendar.view.MyWeekView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.BackHandler
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import screens.commonelements.ButtonPlus
import screens.commonelements.LoadingInfoItem
import screens.commonelements.NoMatchesItem
import screens.commonelements.SnackBarNoInternet
import screens.commonelements.ToolBar
import screens.main.item.ChoosenWeekItem
import screens.main.item.ItemBig
import screens.main.item.ItemForPinnedEvents
import theme.AccentPrimary
import theme.AccentPrimaryTwo
import theme.AccentSecondary
import theme.LayerOne
import theme.TextStyleLocal
import theme.TextWhite
import utils.convertDayOfWeek
import utils.toTitleCase

@OptIn(ExperimentalResourceApi::class)
@Composable
fun MainScreen(
    goToSettings: () -> Unit,
    goToDetailMatch: () -> Unit,
    goToNewEvent: () -> Unit,
    goToDetailEvent: () -> Unit
) {
    val viewModel: MainViewModel =
        koinViewModel(MainViewModel::class)
    //  var selectedDayToEpoch by remember { mutableStateOf<Int?>(null) }
    val today = LocalDate.today()
    var scope = rememberCoroutineScope()

    val selectedDay = viewModel.selectedDate.collectAsState()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var eventsFlow = viewModel.eventsFlow.collectAsState()
    var gamesFlow = viewModel.gamesFlow.collectAsState()

    val maxDate = today.plus(13, DateTimeUnit.DAY)
    var selectedWeek by remember { mutableStateOf<LocalDate?>(LocalDate.today()) }
    var isScrolling by remember { mutableStateOf(false) }
    val bigList by uiState.listOfBig.collectAsState(initial = emptyList())
    val bigListPinned by uiState.listOfBigPinned.collectAsState(
        initial = BigItemDataClass(
            0,
            0,
            emptyList()
        )
    )
    LaunchedEffect(selectedDay) {
        viewModel.getMatchesOfAllLeagues(today.toString(), maxDate.toString())
    }
    LaunchedEffect(Unit) {
        viewModel.getMatchesOfAllLeagues(today.toString(), maxDate.toString())
    }

//    LaunchedEffect(Unit) {
//        println("LaunchedEffect $today,$maxDate,")
//        viewModel.setSelectedDay(selectedDay.value)
//    }

    Column(modifier = Modifier.fillMaxSize()) {
        ToolBar(
            stringResource(Res.string.workschedule),
            onArrowBackClick = { },
            goToDeleteDialog = { },
            goToSettings = { goToSettings() },
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.background(LayerOne)
                    .fillMaxWidth().wrapContentHeight(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Column(
                    modifier = Modifier.padding(horizontal = 16.dp).background(LayerOne)
                        .fillMaxWidth().wrapContentHeight(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    val clickMutex = Mutex()
                    ChoosenWeekItem(
                        onClickPrevious = {
                            if (!isScrolling) {
                                isScrolling = true
                                scope.launch(Dispatchers.IO) {
                                    try {
                                        clickMutex.withLock {
                                            val newDate = selectedWeek?.minus(1, DateTimeUnit.WEEK)
                                            if (newDate != null && newDate >= today) {
                                                selectedWeek = newDate
                                            }
                                            delay(250)
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
                                            val newDate = selectedWeek?.plus(1, DateTimeUnit.WEEK)
                                            if (newDate != null && newDate <= maxDate) {
                                                selectedWeek = newDate
                                            }
                                            delay(250)
                                        }
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                    } finally {
                                        isScrolling = false
                                    }
                                }
                            }
                        },
                        name = "${selectedWeek?.dayOfMonth.toString()} ${
                            selectedWeek?.month.toString().toTitleCase().take(3)
                        } - ${
                            selectedWeek?.plus(
                                6,
                                DateTimeUnit.DAY
                            )?.dayOfMonth.toString()
                        } ${
                            selectedWeek?.plus(6, DateTimeUnit.DAY)?.month.toString().toTitleCase()
                                .take(3)
                        }, ${selectedWeek?.year}",

                        isCurrentWeek = if (selectedWeek == LocalDate.today()) false else true
                    )
                    MyWeekView(
                        startDate = selectedWeek ?: LocalDate.today(),
                        minDate = LocalDate.today(),
                        maxDate = LocalDate.today().plus(1, DateTimeUnit.WEEK),
                        modifier = Modifier.fillMaxWidth().height(56.dp)
                            .pointerInput(Unit) {
                                coroutineScope {
                                    launch {
                                        while (true) {
                                            awaitPointerEventScope {
                                                awaitPointerEvent()
                                            }
                                        }
                                    }
                                }
                            },
                    ) { state ->

                        CalendarDa(
                            state = state,
                            modifier = Modifier
                                .height(56.dp)
                                .wrapContentWidth(),
                            isSelected = selectedDay.value == state.date,
                            onClick = {
                                viewModel.setSelectedDay(state.date)
//                                if (!isScrolling) {
//                                    isScrolling = true
//                                    scope.launch {
//                                        viewModel.setSelectedDay(state.date)
//                                     //   selectedDayToEpoch = state.date.toEpochDays()
//                                        isScrolling = false
//                                    }
//                                }
                            },
                            isDotVisible = eventsFlow.value.any {
                                it.epochDays == state.date.toEpochDays().toInt()

                            } || gamesFlow.value.any {
                                it.epochDays == state.date.toEpochDays().toInt()
                            },
                        )
                    }
                    Spacer(modifier = Modifier.height(7.dp))
                }
            }
        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
            ButtonPlus(onClick = {
                goToNewEvent()
            })

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                when (uiState.loadingState) {
                    LoadingMainState.DONE -> {
                        if (bigListPinned.listItems.isNotEmpty()) {
                            ItemForPinnedEvents(bigListPinned,
                                onClick = { game ->
                                    when (game) {
                                        is EventEntity -> {
                                            viewModel.savEventId(game.id)
                                            goToDetailEvent()
                                        }

                                        is GameEntity -> {
                                            viewModel.saveGameId(game.id)
                                            goToDetailMatch()
                                        }

                                    }
                                })
                        }
                        if (bigList.isEmpty()) {
                            NoMatchesItem()
                        }
                        Box(contentAlignment = Alignment.BottomCenter) {
                            Column(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                if (selectedDay.value == today) {
                                    Text(
                                        text = stringResource(Res.string.scheduletoday),
                                        modifier = Modifier.padding(
                                            start = 16.dp,
                                            bottom = 16.dp,
                                            top = 16.dp
                                        ).fillMaxWidth()
                                            .wrapContentHeight(),
                                        color = AccentSecondary,
                                        style = TextStyleLocal.semibold18,
                                        textAlign = TextAlign.Start
                                    )
                                } else {
                                    Spacer(modifier = Modifier.height(16.dp))
//                                    Text(
//                                        text = "Not today",
//                                        modifier = Modifier.padding(
//                                            start = 16.dp,
//                                            bottom = 16.dp,
//                                            top = 16.dp
//                                        ).fillMaxWidth()
//                                            .wrapContentHeight(),
//                                        color = AccentSecondary,
//                                        style = TextStyleLocal.semibold18,
//                                        textAlign = TextAlign.Start
//                                    )
                                }

                                LazyColumn(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.Top,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    itemsIndexed(
                                        items = bigList,
                                    ) { index, item ->
                                        ItemBig(
                                            bigItemEntity = item,
                                            onClick = { game ->
                                                when (game) {
                                                    is EventEntity -> {
                                                        viewModel.savEventId(game.id)
                                                        goToDetailEvent()
                                                    }

                                                    is GameEntity -> {
                                                        viewModel.saveGameId(game.id)
                                                        goToDetailMatch()
                                                    }
                                                }
                                            }
                                        )
                                        Spacer(modifier = Modifier.height(12.dp))
                                    }
                                }
                            }
                        }
                    }

                    LoadingMainState.LOADING -> {
                        LoadingInfoItem(textLoading = stringResource(Res.string.loadingevents))
                    }

                    LoadingMainState.ERROR -> {
                        SnackBarNoInternet(
                            onRefreshClick = {
                                viewModel.getMatchesOfAllLeagues(
                                    today.toString(),
                                    maxDate.toString()
                                )
                                viewModel.setSelectedDay(selectedDay.value)
                                //     selectedDayToEpoch = selectedDay.value.toEpochDays()
                            },
                        )
                    }
                }

            }
            if (uiState.loadingState != LoadingMainState.ERROR) {
                ButtonPlus(onClick = {
                    scope.launch { }
                    goToNewEvent()
                })
            }
        }
    }
    BackHandler {
        viewModel.exitApp()
    }
}


@Composable
fun CalendarDa(
    state: DayState,
    interactionSource: MutableInteractionSource = MutableInteractionSource(),
    onClick: () -> Unit = {},
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    isDotVisible: Boolean = true,
) = with(state) {
    val today = LocalDate.today()
    LaunchedEffect(Unit) {
    }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedButton(
            onClick = onClick,
            modifier = Modifier.fillMaxHeight().width(28.dp),
            shape = RoundedCornerShape(5.dp),
            border = BorderStroke(0.dp, LayerOne),
            contentPadding = PaddingValues(0.dp),
            interactionSource = interactionSource,
            enabled = enabled,
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = if (isSelected) AccentPrimary else LayerOne,
            ),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    date.dayOfMonth.toString(),
                    color = if (isSelected) AccentPrimaryTwo else TextWhite,
                    style = if (date == today) TextStyleLocal.regular16 else TextStyleLocal.regular16,
                    textAlign = TextAlign.Center
                )
                Spacer(
                    modifier =
                    Modifier.size(2.dp)
                )
                Text(
                    convertDayOfWeek(date.dayOfWeek.toString()),
                    color = if (isSelected) AccentPrimaryTwo else TextWhite,
                    style = if (date == today) TextStyleLocal.regular12 else TextStyleLocal.regular12,
                    textAlign = TextAlign.Center
                )
                if (isDotVisible) {
                    Canvas(
                        modifier =
                        Modifier
                            .padding(bottom = 4.dp, top = 2.dp)
                            .size(6.dp)
                            .align(Alignment.CenterHorizontally),
                        onDraw = { drawCircle(color = AccentPrimaryTwo) },
                    )
                } else {
                    Spacer(
                        modifier =
                        Modifier
                            .padding(bottom = 4.dp, top = 2.dp)
                            .size(4.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}
