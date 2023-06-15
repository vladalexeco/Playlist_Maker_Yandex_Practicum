package ru.vladalexeco.playlistmaker.search.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.vladalexeco.playlistmaker.search.data.dto.Response
import ru.vladalexeco.playlistmaker.search.data.dto.TrackSearchRequest
import ru.vladalexeco.playlistmaker.search.data.dto.TrackSearchResponse

class RetrofitNetworkClient(private val context: Context, private val trackService: ITunesApi): NetworkClient {

//    private val baseUrl = "http://itunes.apple.com"

//    private val retrofit = Retrofit.Builder()
//        .baseUrl(baseUrl)
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()

//    private val trackService = retrofit.create(ITunesApi::class.java)

    override fun doRequest(dto: Any): Response {
        if (!isConnected()) {
            return Response().apply { resultCode = -1 }
        }
        if (dto !is TrackSearchRequest) {
            return Response().apply { resultCode = 400 }
        }

        val response = trackService.search(dto.expression).execute()

        val body = response.body()

        return body?.apply { resultCode = response.code() } ?: Response().apply { resultCode = response.code() }

    }

    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            }
        }
        return false
    }

}