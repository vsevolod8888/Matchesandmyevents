package screens.main

import data.MatchRepository
import data.room.BigItemDataClass
import io.wojciechosak.calendar.utils.today
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import screens.main.item.Event
import utils.PlatformUtils

class MainViewModel(
    private val repository: MatchRepository,
    private val platformUtils: PlatformUtils
) : ViewModel() {
    var selectedDate = MutableStateFlow(LocalDate.today())
    fun setSelectedDay(date: LocalDate) {
        selectedDate.value = date
    }

    fun getIntIdList(): Flow<List<Int>> {
        return repository.getIntIdListOfLeagues()
    }

    private val _uiState = MutableStateFlow(
        MainState(
            listOfBig = emptyFlow(),
            listOfBigPinned = emptyFlow(),
            loadingState = LoadingMainState.LOADING
        )
    )
    val uiState: StateFlow<MainState> = _uiState

    private val exceptionHandler = CoroutineExceptionHandler { _, e ->
        _uiState.update {
            it.copy(loadingState = LoadingMainState.ERROR)
        }
    }

    private val _bigItemList = MutableStateFlow<List<BigItemDataClass>>(emptyList())
    val bigItemList: StateFlow<List<BigItemDataClass>> = _bigItemList

    private val _bigItemListPinned = MutableStateFlow(BigItemDataClass(0, 0, emptyList()))
    val bigItemListPinned: StateFlow<BigItemDataClass> = _bigItemListPinned


    val eventsFlow = repository.getListOfEvents().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

//    val gamesFlow = repository.getListOfGames()
//        .map { items -> items.filter { it.leagueId.toInt() in getIntIdList() } }
//        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val gamesFlow = getIntIdList()
        .combine(repository.getListOfGames()) { idList, games ->
            games.filter { it.leagueId.toInt() in idList }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val gamesFlowForPinned = repository.getListOfGames()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        viewModelScope.launch(exceptionHandler) {
            combine(selectedDate, eventsFlow, gamesFlow) { date, events, games ->
                val combinedList = (events + games).filter { event ->
                    val eventDate = Instant.fromEpochMilliseconds(event.startTimeTimeStamp)
                        .toLocalDateTime(TimeZone.currentSystemDefault()).date
                    eventDate == date
                }
                val bigItemsMap = mutableMapOf<Pair<Long, Long>, MutableList<Event>>()
                combinedList.forEach { event ->
                    val key = event.startTimeTimeStamp to event.endTimeTimeStamp
                    if (bigItemsMap.containsKey(key)) {
                        bigItemsMap[key]?.add(event)
                    } else {
                        bigItemsMap[key] = mutableListOf(event)
                    }
                }
                val bigItemList = bigItemsMap.map { (key, events) ->
                    BigItemDataClass(
                        startTimeTimeStamp = key.first,
                        endTimeTimeStamp = key.second,
                        listItems = events
                    )
                }.sortedBy { it.startTimeTimeStamp }

                _bigItemList.value = bigItemList

                _uiState.update { uiState ->
                    uiState.copy(
                        listOfBig = _bigItemList,
                    )
                }
            }.collect {}
        }
    }

    init {
        setSelectedDay(LocalDate.today())
    }

    init {
        viewModelScope.launch(exceptionHandler) {
            combine(eventsFlow, gamesFlowForPinned) { events, games ->
                val pinnedEvents = events.filter { it.isEventWasPinned }
                val pinnedGames = games.filter { it.isGameWasPinned }
                val combinedList = pinnedEvents + pinnedGames
                val pinnedItemList = BigItemDataClass(
                    startTimeTimeStamp = 0,
                    endTimeTimeStamp = 0,
                    listItems = combinedList
                )

                _bigItemListPinned.value = pinnedItemList

                _uiState.update { uiState ->
                    uiState.copy(
                        listOfBigPinned = _bigItemListPinned
                    )
                }
            }.collect {}
        }
    }

    fun getMatchesOfAllLeagues(from:String, to:String) {
        viewModelScope.launch(exceptionHandler) {
            repository.getMatchesOfAllLeagues(from, to)
            _uiState.update { uiState ->
                uiState.copy(
                    loadingState = LoadingMainState.DONE
                )
            }
            selectedDate.value = selectedDate.value

        }
    }


    fun exitApp() {
        platformUtils.exitApp()
    }

    fun saveGameId(gameId: Long) {
        repository.savGameId(gameId)
    }

    fun savEventId(gameId: Long) {
        repository.saveEventId(gameId)
    }

    data class MainState(
        val listOfBig: Flow<List<BigItemDataClass>>,
        val listOfBigPinned: Flow<BigItemDataClass>,
        val loadingState: LoadingMainState
    )
}

enum class LoadingMainState { LOADING, DONE, ERROR }