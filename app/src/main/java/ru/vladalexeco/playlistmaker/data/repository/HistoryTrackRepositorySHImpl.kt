package ru.vladalexeco.playlistmaker.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import ru.vladalexeco.playlistmaker.domain.models.Track
import ru.vladalexeco.playlistmaker.domain.repository.HistoryTrackRepositorySH

const val KEY_FOR_HISTORY_LIST = "KEY_FOR_HISTORY_LIST"
const val SHARED_PREFERENCES = "SHARED_PREFERENCES"

class HistoryTrackRepositorySHImpl(private val context: Context): HistoryTrackRepositorySH {

    private val sharedPreferences: SharedPreferences? = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)

    override fun getTrackListFromSH(): Array<Track> {
        val json = sharedPreferences?.getString(KEY_FOR_HISTORY_LIST, null) ?: return emptyArray()
        return Gson().fromJson(json, Array<Track>::class.java)
    }

    override fun saveTrackListToSH(historyList: ArrayList<Track>) {
        val json = Gson().toJson(historyList)
        sharedPreferences?.edit()
            ?.putString(KEY_FOR_HISTORY_LIST, json)
            ?.apply()
    }
}