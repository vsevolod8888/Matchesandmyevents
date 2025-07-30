package screens.detail

import data.MatchRepository
import data.room.GameEntity
import kotlinx.coroutines.CoroutineExceptionHandler
import moe.tlaster.precompose.viewmodel.ViewModel
import utils.PlatformUtils

class MatchDetailViewModel(
    private val repository: MatchRepository,
    private val platformUtils: PlatformUtils
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
    }

    suspend fun getGameById(id: Long): GameEntity? {
        return repository.getGameById(id)
    }

    fun getGameId(): Long {
        return repository.getGameId()
    }

    suspend fun updateIsMatchWasPinned(id: Long, isEventWasPinned: Boolean) {
        repository.updateIsMatchWasPinned(id, isEventWasPinned)
    }

    suspend fun updateTextEventMatch(id: Long, textEventMatch: String) {
        repository.updateTextEventMatch(id, textEventMatch)
    }
    suspend fun updateColourIndexMatch(id: Long, colourIndex: Int) {
        repository.updateColourIndexMatch(id, colourIndex)
    }

    fun addIdToListChangedMatches(newId: Long) {
        repository.addIdToListChangedMatches(newId)
    }
}