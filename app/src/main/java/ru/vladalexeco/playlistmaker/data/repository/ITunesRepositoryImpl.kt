package ru.vladalexeco.playlistmaker.data.repository

import android.view.View
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.vladalexeco.playlistmaker.R
import ru.vladalexeco.playlistmaker.data.interfaces.ResponseFromServerListener
import ru.vladalexeco.playlistmaker.data.models.ITunesServerResponse
import ru.vladalexeco.playlistmaker.data.retrofit.ITunesApi
import ru.vladalexeco.playlistmaker.data.retrofit.ServerResponse
import ru.vladalexeco.playlistmaker.data.retrofit.TrackResponse
import ru.vladalexeco.playlistmaker.domain.models.Track
import ru.vladalexeco.playlistmaker.domain.repository.ITunesRepository

class ITunesRepositoryImpl: ITunesRepository {

    private val baseUrl = "http://itunes.apple.com"

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val trackService = retrofit.create(ITunesApi::class.java)

    private var serverResponseListener: ResponseFromServerListener? = null

    fun setResponseFromServerListener(listener: ResponseFromServerListener) {
        serverResponseListener = listener
    }

    fun removeResponseFromServerListener() {
        serverResponseListener = null
    }

    override fun getTracks(textRequest: String) {

        var serverResponse: ServerResponse? = null
        val tracks: ArrayList<Track> = ArrayList()
        var serverResponseCode: Int? = null

        trackService.search(text=textRequest)
            .enqueue(object: Callback<TrackResponse> {
                override fun onResponse(
                    call: Call<TrackResponse>,
                    response: Response<TrackResponse>
                ) {

                    serverResponse = ServerResponse.SUCCESS
                    serverResponseCode = response.code()

                    when (response.code()) {

                        200 -> {

                            if (response.body()?.tracks?.isNotEmpty() == true) {
                                tracks.addAll(response.body()?.tracks!!)
                            }
                        }
                    }

                    serverResponseListener?.onResponseFromServerOccurred(ITunesServerResponse(tracks, serverResponse, serverResponseCode))

                }

                override fun onFailure(
                    call: Call<TrackResponse>,
                    t: Throwable
                ) {
                    serverResponse = ServerResponse.FAILED
                    serverResponseListener?.onResponseFromServerOccurred(ITunesServerResponse(tracks, serverResponse, serverResponseCode))
                }

            })
    }
}