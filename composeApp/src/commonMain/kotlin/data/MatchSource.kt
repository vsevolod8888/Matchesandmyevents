package data


import data.dto.LeagueDto
import data.dto.MatchDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import utils.ApiKeyDecoder
import utils.TextUtils

class MatchSource(private val client: HttpClient) {
    suspend fun getListOfLeagues(): LeagueDto? {
        return try {
            client.get(TextUtils.MATCH_BASE_URL + "leagues") {
               header("x-apisports-key", ApiKeyDecoder.MATCH_KEY)//.decodeKey()
                   //  parameter("season", 2025)
            }.body()
        } catch (e: Exception) {
            println("zzz $e")
            null
        }
    }

    suspend fun getMatchesByLeagueId(leagueId: Int, from:String, to:String): MatchDto? {
        //  return try {
        return client.get(TextUtils.MATCH_BASE_URL + "fixtures") {
            header("x-apisports-key", "b52f65064201b36ff8c5ac48802940f1")//.decodeKey()
         //   parameter("league", leagueId)
                //   parameter("season", 2025)
            parameter("date", "2025-07-30")//"2024-07-08"
          //  parameter("to", "2025-08-30")//"2024-07-19"
        }.body()
//        } catch (e: Exception) {
//            println("println ${e.message}")
//            null
//        }
    }
}