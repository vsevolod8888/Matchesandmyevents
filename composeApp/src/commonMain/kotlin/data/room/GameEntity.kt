package data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import screens.main.item.Event

@Entity
data class GameEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val textEventMatch: String? = null,
    val colour: Int = 0,
    val isGameWasPinned: Boolean = false,
    val isBetWasSaved: Boolean = false,
    val epochDays: Int = 0,
 //   val newepochDays: Int = 0,
    val timestamp: Int = 0,
 //   val newtimestamp: Int = 0,
    val dateOld: String = "",
    val dateNew: String = "",
    val leagueId: Long = -1L,
    val status: String = "",
    val idteam1: Long = -1L,
    val idteam2: Long = -1L,
    val date: String = "",
    override val startTimeTimeStamp: Long = -1L,
    override val endTimeTimeStamp: Long = -1L,
    val name1: String = "",
    val name2: String = "",
    val score1: String = "",
    val score2: String = "",
    val score1halfTime: String = "",
    val score2halfTime: String = "",
    val pic1: String = "",
    val pic2: String = "",
    val picLeague: String = "",
    val league: String = "",
    val stadium: String = "",
    val referee: String = "–",
    val city: String = "",
    val country: String = "",
    val round: String = "",
    val minutematch: Int = -1,
    val isNotificationsAllowed: Boolean = false,
    val isDialogShown: Boolean? = null,
) : Event
