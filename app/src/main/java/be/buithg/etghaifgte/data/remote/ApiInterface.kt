package be.buithg.etghaifgte.data.remote

import be.buithg.etghaifgte.domain.models.CricketData
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("matches")
    suspend fun getLiveScore(@Query("apikey") apikey :String) : CricketData


}