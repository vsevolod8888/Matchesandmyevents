package data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(item: GameEntity)


    @Query("SELECT count(*) FROM GameEntity")
    suspend fun count(): Int

    @Query("SELECT * FROM GameEntity")
    fun getAllAsFlow(): Flow<List<GameEntity>>

    @Query("DELETE FROM GameEntity WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM GameEntity")
    suspend fun deleteAll()

    @Query("SELECT * FROM GameEntity WHERE id = :id")
    suspend fun getGameById(id: Long): GameEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(items: List<GameEntity>)

    @Query("SELECT * FROM GameEntity WHERE id IN (:ids)")
    suspend fun getGamesByIds(ids: List<Long>): List<GameEntity>
    @Transaction
    suspend fun insertListWithoutUpdatingCertainFields(newItems: List<GameEntity>) {
        val existingItems = getGamesByIds(newItems.map { it.id })
        val itemsToUpdate = newItems.map { newItem ->
            val existingItem = existingItems.find { it.id == newItem.id }
            if (existingItem != null) {
                newItem.copy(
                    textEventMatch = existingItem.textEventMatch,
                    colour = existingItem.colour,
                    isGameWasPinned = existingItem.isGameWasPinned
                )
            } else {
                newItem
            }
        }
        insertList(itemsToUpdate)
    }


    @Query("UPDATE GameEntity SET status = :newStatus WHERE id = :id")
    suspend fun updateGameStatus(id: Long, newStatus: String)

    @Query("UPDATE GameEntity SET textEventMatch = :textEventMatch WHERE id = :id")
    suspend fun updateTextEventMatch(id: Long, textEventMatch: String)

    @Query("UPDATE GameEntity SET colour = :colourIndex WHERE id = :id")
    suspend fun updateColourIndexMatch(id: Long, colourIndex: Int)

    @Query("UPDATE GameEntity SET isGameWasPinned = :isPinned WHERE id = :id")
    suspend fun updateIsGameWasPinned(id: Long, isPinned: Boolean)

    @Query("DELETE FROM GameEntity WHERE isGameWasPinned = 1")
    suspend fun deleteAllPinnedGames()

    @Query("SELECT * FROM GameEntity WHERE id IN (:idsNotToUpdate)")
    suspend fun getAllMatchesNotIn(idsNotToUpdate: List<Long>): List<GameEntity>

}