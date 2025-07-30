package data.impl

import data.MatchRepository
import data.MatchSource
import data.preferences.DataPreferencesStore
import data.room.AppDatabasee
import data.room.EventEntity
import data.room.GameEntity
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import model.Leaguee
import model.getLeagues


class MatchRepositoryImpl(
    private val prefStore: DataPreferencesStore,
    private val appDatabase: AppDatabasee,
    private val matchSource: MatchSource,
) : MatchRepository {
    override var listLeagues: MutableStateFlow<List<Leaguee>> = MutableStateFlow(emptyList())
    var listLeaguesSelected: MutableStateFlow<List<Int>> = MutableStateFlow(prefStore.getIntIdList())

    override fun saveFirstLaunch(int: Int) {
        prefStore.saveFirstLaunch(int)
    }

    override fun getFirstLaunch(): Int {
        return prefStore.getFirstLaunch()
    }

    override suspend fun getListOfLeagues() {
        val responceLeagueList = matchSource.getListOfLeagues()?.response
        listLeagues.value = getLeagues(responceLeagueList)
    }

    override fun saveIntIdList(idList: List<Int>) {
        prefStore.saveIntIdList(idList)
        listLeaguesSelected.value = idList
    }

    override fun getIntIdListOfLeagues():  Flow<List<Int>> {
        return listLeaguesSelected
    }

    override fun getIntIdList(): List<Int> {
        return listLeaguesSelected.value
    }

    override suspend fun deleteAllGamesInDatabase() {
        appDatabase.getDao().deleteAll()
    }

    override suspend fun getMatchesOfAllLeagues( from:String, to:String) {
        coroutineScope {
            val ids = listLeaguesSelected.value.map { it.toLong() }
            ids.forEach { leagueId ->
                val fetchAndInsertJob = launch {
                    val allMatches = mutableListOf<GameEntity>()
                    val matchesResponse = matchSource.getMatchesByLeagueId(leagueId.toInt(), from,to)?.response
                        ?.map { it.convertToMatchModelEntity() }

                    if (matchesResponse != null) {
                        allMatches.addAll(matchesResponse)
                    }

                    val newMatches = allMatches
                    appDatabase.getDao().insertListWithoutUpdatingCertainFields(newMatches)
                }
                fetchAndInsertJob.join()
            }
        }
    }



    override fun getListOfGames(): Flow<List<GameEntity>> {

        return appDatabase.getDao().getAllAsFlow()
    }

    override fun getListOfEvents(): Flow<List<EventEntity>> {
        return appDatabase.getEventDao().getAllEventsAsFlow()
    }

    override fun savGameId(gameId: Long) {
        prefStore.saveGameId(gameId)
    }

    override fun getGameId(): Long {
        return prefStore.getGameId()
    }

    override suspend fun getGameById(id: Long): GameEntity? {
        return appDatabase.getDao().getGameById(id)
    }

    override suspend fun insertEvent(item: EventEntity) {
        appDatabase.getEventDao().insertEvent(item)
    }

    override suspend fun insertMatch(item: GameEntity) {
        appDatabase.getDao().insertGame(item)
    }

    override suspend fun deleteEventById(id: Long) {
        appDatabase.getEventDao().deleteEventById(id)
    }


    override suspend fun deleteAllEvents() {
        appDatabase.getEventDao().deleteAllEvents()
    }


    override suspend fun getEventById(id: Long): EventEntity? {
        return appDatabase.getEventDao().getEventById(id)
    }

    override fun saveEventId(gameId: Long) {
        prefStore.saveEventId(gameId)
    }

    override fun getEventId(): Long {
        return prefStore.getEventId()
    }

    override suspend fun updateIsEventWasPinned(id: Long, isPinned: Boolean) {
        appDatabase.getEventDao().updateIsEventWasPinned(id, isPinned)
    }

    override suspend fun updateIsMatchWasPinned(id: Long, isPinned: Boolean) {
        appDatabase.getDao().updateIsGameWasPinned(id, isPinned)
    }

    override suspend fun updateTextEventMatch(id: Long, textEventMatch: String) {
        appDatabase.getDao().updateTextEventMatch(id, textEventMatch)
    }

    override suspend fun updateColourIndexMatch(id: Long, colourIndex: Int) {
        appDatabase.getDao().updateColourIndexMatch(id, colourIndex)
    }

    override fun saveIdListChangedMatches(idList: List<Long>) {
        prefStore.saveIdListChangedMatches(idList)
    }

    override fun getIdListChangedMatches(): List<Long> {
        return prefStore.getIdListChangedMatches()
    }

    override fun addIdToListChangedMatches(newId: Long) {
        prefStore.addIdToListChangedMatches(newId)
    }
}