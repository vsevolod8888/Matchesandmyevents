package data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(item: EventEntity)

    @Query("DELETE FROM EventEntity WHERE id = :id")
    suspend fun deleteEventById(id: Long)

    @Query("DELETE FROM EventEntity")
    suspend fun deleteAllEvents()

    @Query("SELECT * FROM EventEntity WHERE id = :id")
    suspend fun getEventById(id: Long): EventEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListEvents(items: List<EventEntity>)

    @Query("SELECT count(*) FROM EventEntity")
    suspend fun count(): Int

    @Query("SELECT * FROM EventEntity")
    fun getAllEventsAsFlow(): Flow<List<EventEntity>>

    @Query("UPDATE EventEntity SET isEventWasPinned = :isPinned WHERE id = :id")
    suspend fun updateIsEventWasPinned(id: Long, isPinned: Boolean)

}