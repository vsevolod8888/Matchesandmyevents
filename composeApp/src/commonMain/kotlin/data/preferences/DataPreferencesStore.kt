package data.preferences

import com.liftric.kvault.KVault


class DataPreferencesStore(private val kVault: KVault){
    companion object {
        private const val KEY_FIRST_LAUNCH = "KEY_FIRST_LAUNCH"
        private const val KEY_SOUND = "KEY_SOUND"
        private const val KEY_ID_SET = "KEY_ID_SET"
        private const val KEY_ID_SET_CHANGED_MATCHES = "KEY_ID_SET_CHANGED_MATCHES"
        private const val KEY_GAME_ID = "KEY_GAME_ID"
        private const val KEY_EVENT_ID = "KEY_EVENT_ID"
    }

    fun saveFirstLaunch(int: Int) {
        kVault.set(KEY_FIRST_LAUNCH, int)
    }
    fun getFirstLaunch(): Int = kVault.int(KEY_FIRST_LAUNCH) ?: 1

    fun saveIntIdList(idList: List<Int>) {
        val idString = idList.joinToString(separator = ",")
        kVault.set(KEY_ID_SET, idString)
    }

    fun getIntIdList(): List<Int> {
        val idString = kVault.string(KEY_ID_SET) ?: return emptyList()
        return idString.split(",").mapNotNull { it.toIntOrNull() }
    }
    fun addIdToList(newId: Int) {
        val currentList = getIntIdList().toMutableList()
        if (!currentList.contains(newId)) {
            currentList.add(newId)
            saveIntIdList(currentList)
        }
    }

    fun saveIdListChangedMatches(idList: List<Long>) {
        val idString = idList.joinToString(separator = ",")
        kVault.set(KEY_ID_SET_CHANGED_MATCHES, idString)
    }

    fun getIdListChangedMatches(): List<Long> {
        val idString = kVault.string(KEY_ID_SET_CHANGED_MATCHES) ?: return emptyList()
        return idString.split(",").mapNotNull { it.toLongOrNull() }
    }
    fun addIdToListChangedMatches(newId: Long) {
        val currentList = getIdListChangedMatches().toMutableList()
        if (!currentList.contains(newId)) {
            currentList.add(newId)
            saveIdListChangedMatches(currentList)
        }
    }
    fun saveGameId(gameId: Long) {
        kVault.set(KEY_GAME_ID, gameId)
    }
    fun getGameId(): Long = kVault.long(KEY_GAME_ID) ?: 0L

    fun saveEventId(eventId: Long) {
        kVault.set(KEY_EVENT_ID, eventId)
    }
    fun getEventId(): Long = kVault.long(KEY_EVENT_ID) ?: 0L

}