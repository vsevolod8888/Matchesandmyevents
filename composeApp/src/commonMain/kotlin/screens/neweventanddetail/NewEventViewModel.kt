package screens.neweventanddetail

import data.MatchRepository
import data.room.EventEntity
import kotlinx.coroutines.CoroutineExceptionHandler
import moe.tlaster.precompose.viewmodel.ViewModel
import utils.PlatformUtils

class NewEventViewModel(
    private val repository: MatchRepository,
    private val platformUtils: PlatformUtils
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
    }

    suspend fun insertEvent(item: EventEntity) {
        repository.insertEvent(item)
    }

}