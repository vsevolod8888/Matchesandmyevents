package data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import data.dto.convertUtcToTimeStamp
import screens.main.item.Event

@Entity
data class EventEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val isEventWasPinned: Boolean = false,
    val epochDays: Int = 0,
    override val startTimeTimeStamp: Long = -1L,
    override val endTimeTimeStamp: Long = -1L,
    val tittle: String = "",
    val textEvent: String = "",
    val colour: Int = 0,

) : Event
