package data.dto

import data.room.GameEntity
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.offsetAt
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable
import kotlin.time.Duration.Companion.hours

@Serializable
data class MatchDto(
    val response: List<Response>,
) {
    @Serializable
    data class Response(
        val fixture: Fixture,
        val goals: Goals,
        val league: LeagueMatch,
        val score: Score,
        val teams: Teams
    ) {
        fun convertToMatchModelEntity() = GameEntity(
            id = fixture.id.toLong() ?: 0L,
            epochDays = (fixture.timestamp+getTimeZoneOffsetInSeconds()) / 86400,
        //    newepochDays = 19896,//convertUtcToTimeStamp(convertUtcToLocalDateTimeString(fixture.date)).toInt() / 86400,
            timestamp = fixture.timestamp,
       //     newtimestamp = 1719014400,//convertUtcToTimeStamp(convertUtcToLocalDateTimeString(fixture.date)).toInt(),
            dateOld = fixture.date,
            dateNew = convertUtcToLocalDateTimeString(fixture.date),
            leagueId = league.id?.toLong() ?: 0L,
            status = fixture.status.long,
            idteam1 = teams.home.id ?: -1L,
            idteam2 = teams.away.id ?: -1L,
            date = formatDate(fixture.date.substring(0, 10)),
            startTimeTimeStamp = convertUtcToTimeStamp(fixture.date),
            endTimeTimeStamp = convertUtcToTimeStampPlusTwo(fixture.date),
            name1 = teams.home.name,
            name2 = teams.away.name,
            score1 = convertScore(goals.home.toString()),
            score2 = convertScore(goals.away.toString()),
            score1halfTime = convertScore(score.halftime.home.toString()),
            score2halfTime = convertScore(score.halftime.away.toString()),
            pic1 = teams.home.logo,
            pic2 = teams.away.logo,
            picLeague = league.logo,
            league = league.name,
            stadium = fixture.venue.name.orEmpty(),
            city = fixture.venue.city.orEmpty(),
            referee = fixture.referee?:"–",
            round = league.round,
            country = league.country?:"–",
            minutematch = fixture.status.elapsed ?: 0,
        )
    }
}


@Serializable
data class Fixture(
    val date: String,
    val id: Int,
    // val periods: Periods,
    val venue: Venue,
    val referee: String?,
    val status: Status,
    val timestamp: Int,
    val timezone: String,
)

@Serializable
data class Goals(
    val away: Int? = null,
    val home: Int? = null,
)

//@Serializable
//data class Periods(
//    val first: Int,
//    val second: Int
//)
@Serializable
data class Status(
    val elapsed: Int?,
    val long: String,
    val short: String
)

@Serializable
data class Score(
    val fulltime: Fulltime,
    val halftime: Halftime,
)

@Serializable
data class Fulltime(
    val away: Int?,
    val home: Int?
)

@Serializable
data class Halftime(
    val away: Int?,
    val home: Int?
)

@Serializable
data class Teams(
    val away: Awayy,
    val home: Home
)

@Serializable
data class Awayy(
    val id: Long?,
    val logo: String,
    val name: String,
)

@Serializable
data class Home(
    val id: Long,
    val logo: String,
    val name: String,
)

@Serializable
data class LeagueMatch(
    val country: String,
    val flag: String?,
    val id: Int?,
    val logo: String,
    val name: String,
    val round: String,
    val season: Int
)

@Serializable
data class Venue(
    val city: String?,
    val id: Int?,
    val name: String?,
)

fun convertScore(s: String): String {
    return if (s.contains("null"))
        "0"
    else
        s
}

fun formatDate(date: String): String {
    val parts = date.split("-")
    return "${parts[2]}.${parts[1]}.${parts[0]}"
}

fun convertUtcToTimeStamp(utcDateTimeString: String): Long {
    val utcInstant = Instant.parse(utcDateTimeString)

    return utcInstant.toEpochMilliseconds()
}

fun convertUtcToTimeStampPlusTwo(utcDateTimeString: String): Long {
    val utcInstant = Instant.parse(utcDateTimeString)
    val plusTwoHoursInstant = utcInstant.plus(2.hours)

    return plusTwoHoursInstant.toEpochMilliseconds()
}

fun convertTimestampToLocalTime(
    timestamp: Long,
    timeZone: String = TimeZone.currentSystemDefault().id
): String {
    val instant = Instant.fromEpochMilliseconds(timestamp)
    val localDateTime = instant.toLocalDateTime(TimeZone.of(timeZone))
    val localTime = localDateTime.time
    val hour = localTime.hour.toString().padStart(2, '0')
    val minute = localTime.minute.toString().padStart(2, '0')
    return "$hour:$minute"
}

fun convertUtcToLocalDateTimeString(utcDateTimeString: String): String {
    val utcInstant = Instant.parse(utcDateTimeString)
    val localDateTime = utcInstant.toLocalDateTime(TimeZone.currentSystemDefault())
    val formattedDateTime =
        "${localDateTime.toString()}${formatTimeZoneOffset(kotlinx.datetime.TimeZone.currentSystemDefault())}".replace(
            'Z',
            '+'
        )
    return formattedDateTime.toString()
}

fun formatTimeZoneOffset(timeZone: TimeZone): String {
    val now = Clock.System.now()
    val offset = timeZone.offsetAt(now)
    val totalSeconds = offset.totalSeconds
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val sign = if (totalSeconds >= 0) "+" else "-"

    return "${sign}${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}"
}



fun getTimeZoneOffsetInSeconds(): Int {
    val currentMoment = Clock.System.now()
    val systemTimeZone = TimeZone.currentSystemDefault()
    val utcTimeZone = TimeZone.UTC
    val systemOffset = systemTimeZone.offsetAt(currentMoment).totalSeconds
    val utcOffset = utcTimeZone.offsetAt(currentMoment).totalSeconds
    return (systemOffset - utcOffset)
}

fun getTimeZoneOffsetInMilliSeconds(): Int {
    val currentMoment = Clock.System.now()
    val systemTimeZone = TimeZone.currentSystemDefault()
    val utcTimeZone = TimeZone.UTC
    val systemOffset = systemTimeZone.offsetAt(currentMoment).totalSeconds
    val utcOffset = utcTimeZone.offsetAt(currentMoment).totalSeconds
    return (systemOffset - utcOffset)*1000
}


