package screens.leagues

import data.MatchRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import model.Leaguee
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import utils.PlatformUtils

class LeaguesViewModel(private val repository: MatchRepository,private val platformUtils: PlatformUtils) : ViewModel() {
    var filterLeaguesState = listOf(9, 252, 537, 479, 75, 479, 388, 1031, 542, 170, 957, 954, 956, 257, 523)

    private val _uiState = MutableStateFlow(
        LeaguesState(
            listOfLeaguees = emptyFlow(),
            loadingState = LoadingState.LOADING
        )
    )

    val uiState: StateFlow<LeaguesState> = _uiState

    private val exceptionHandler = CoroutineExceptionHandler{ _, _ ->
        _uiState.update {
            it.copy(loadingState = LoadingState.ERROR)
        }
    }

    init {
        viewModelScope.launch {
            getListOfLeagues()
        }
    }


    fun getListOfLeagues() {
        viewModelScope.launch(exceptionHandler) {
            repository.getListOfLeagues()
            repository.listLeagues
                .onEach { leagues ->
                    _uiState.update { uiState ->
                        uiState.copy(
                            listOfLeaguees = repository.listLeagues.map { list ->
                                list.filter { it.leagueId in filterLeaguesState }
                            }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList()),
                            loadingState = if (leagues.isEmpty()) LoadingState.ERROR else LoadingState.DONE
                        )
                    }
                }
                .launchIn(this)
        }
    }

    data class LeaguesState(
        val listOfLeaguees: Flow<List<Leaguee>>,
        val loadingState: LoadingState
    )

    fun saveIntIdList(idList: List<Int>) {
        repository.saveIntIdList(idList)
    }

    fun getIntIdList(): List<Int> {
       return repository.getIntIdList()
    }

    fun setFirstLaunch(b:Int){
        repository.saveFirstLaunch(b)
    }
    fun getFirstLaunch():Int = repository.getFirstLaunch()

    fun exitApp() {
        platformUtils.exitApp()
    }
}

enum class LoadingState { LOADING, DONE, ERROR }