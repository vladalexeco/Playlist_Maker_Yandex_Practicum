package ru.vladalexeco.playlistmaker.search.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import ru.vladalexeco.playlistmaker.search.data.dto.TrackSearchResponse

interface ITunesApi {
    @GET("/search?entity=song")
    suspend fun search(
        @Query("term") text: String
    ): TrackSearchResponse
}