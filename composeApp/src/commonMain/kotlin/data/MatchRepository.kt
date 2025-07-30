package data

import data.room.EventEntity
import data.room.GameEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import model.Leaguee


interface MatchRepository {

    var listLeagues: MutableStateFlow<List<Leaguee>>

    fun saveFirstLaunch(int: Int)
    fun getFirstLaunch(): Int

    suspend fun getListOfLeagues()


    fun saveIntIdList(idList: List<Int>)
    fun getIntIdListOfLeagues(): Flow<List<Int>>
    fun getIntIdList(): List<Int>

    suspend fun getMatchesOfAllLeagues(from:String, to:String)
    suspend fun deleteAllGamesInDatabase()
    fun getListOfGames(): Flow<List<GameEntity>>
    fun getListOfEvents(): Flow<List<EventEntity>>

    fun savGameId(gameId: Long)
    fun getGameId(): Long
    fun saveEventId(gameId: Long)
    fun getEventId(): Long
    suspend fun getGameById(id: Long): GameEntity?


    suspend fun insertEvent(item: EventEntity)
    suspend fun insertMatch(item:GameEntity)

    suspend fun deleteEventById(id: Long)


    suspend fun deleteAllEvents()


    suspend fun getEventById(id: Long): EventEntity?

    suspend fun updateIsEventWasPinned(id: Long, isPinned: Boolean)
    suspend fun updateIsMatchWasPinned(id: Long, isPinned: Boolean)

    suspend fun updateTextEventMatch(id: Long, textEventMatch: String)

    suspend fun updateColourIndexMatch(id: Long, colourIndex: Int)
    fun saveIdListChangedMatches(idList: List<Long>)
    fun getIdListChangedMatches(): List<Long>
    fun addIdToListChangedMatches(newId: Long)
}