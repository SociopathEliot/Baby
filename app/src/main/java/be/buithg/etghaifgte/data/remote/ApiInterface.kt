package be.buithg.etghaifgte.data.remote

import be.buithg.etghaifgte.domain.models.ScoreboardResponse
import retrofit2.http.Path
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("apis/site/v2/sports/{sport}/{league}/scoreboard")
    suspend fun getScoreboard(
        @Path("sport") sport: String,
        @Path("league") league: String,
        @Query("dates") dates: String? = null,
        @Query("limit") limit: Int = 100
    ): ScoreboardResponse

}