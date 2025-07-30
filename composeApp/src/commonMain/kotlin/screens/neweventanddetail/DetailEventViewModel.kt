package screens.neweventanddetail

import data.MatchRepository
import data.room.EventEntity
import kotlinx.coroutines.CoroutineExceptionHandler
import moe.tlaster.precompose.viewmodel.ViewModel
import utils.PlatformUtils

class DetailEventViewModel(
    private val repository: MatchRepository,
    private val platformUtils: PlatformUtils
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->

    }

    suspend fun insertEvent(item: EventEntity) {
        repository.insertEvent(item)
    }

    suspend fun deleteEventById(id: Long) {
        repository.deleteEventById(id)
    }
    fun getEventId(): Long {
        return repository.getEventId()
    }

    suspend fun getEventById(id: Long): EventEntity?{
        return repository.getEventById(id)
    }
    suspend fun updateIsEventWasPinned(id: Long, isPinned: Boolean){
        repository.updateIsEventWasPinned(id,isPinned)
    }
}