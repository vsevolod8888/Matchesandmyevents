package model

import data.dto.LeagueDto

data class Leaguee(
    val id: Int?,
    val leagueId: Int?,
    val nameOfLeague:String?,
    val logo:String?,
    var isChecked: Boolean = false
)

fun getLeagues(resp: List<LeagueDto.Response?>?): List<Leaguee> {
    val listLeagues = mutableListOf<Leaguee>()
    resp?.forEachIndexed { ind, it ->
        listLeagues.add(
            Leaguee(
                id = ind,
                leagueId = it?.league?.id,
                nameOfLeague = it?.league?.name,
                logo = it?.league?.logo
            )
        )
    }
    return listLeagues
}