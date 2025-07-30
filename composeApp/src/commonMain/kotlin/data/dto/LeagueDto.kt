package data.dto

import kotlinx.serialization.Serializable

@Serializable
data class LeagueDto(
    val response: List<Response>
){
    @Serializable
    data class Response(
        val league: League,
        )  {
        @Serializable
        data class League(
            val id: Int,
            val logo: String,
            val name: String,
            val type: String
        )
    }
}

