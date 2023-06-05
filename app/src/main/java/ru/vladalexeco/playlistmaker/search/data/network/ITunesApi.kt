package ru.vladalexeco.playlistmaker.search.data.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.vladalexeco.playlistmaker.search.data.dto.TrackSearchResponse

interface ITunesApi {
    @GET("/search?entity=song")
    fun search(
        @Query("term") text: String
    ): Call<TrackSearchResponse>
}