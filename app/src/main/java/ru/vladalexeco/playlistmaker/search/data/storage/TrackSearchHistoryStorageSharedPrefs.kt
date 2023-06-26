package ru.vladalexeco.playlistmaker.search.data.storage

import android.content.SharedPreferences
import com.google.gson.Gson
import ru.vladalexeco.playlistmaker.KEY_FOR_HISTORY_LIST
import ru.vladalexeco.playlistmaker.search.data.dto.TrackDto


class TrackSearchHistoryStorageSharedPrefs(private val sharedPreferences: SharedPreferences?): TrackSearchHistoryStorage {

    override fun getTracksFromStorage(): Array<TrackDto> {
        val json = sharedPreferences?.getString(KEY_FOR_HISTORY_LIST, null) ?: return emptyArray()
        return Gson().fromJson(json, Array<TrackDto>::class.java)
    }

    override fun saveTracksToStorage(tracks: ArrayList<TrackDto>) {
        val json = Gson().toJson(tracks)
        sharedPreferences?.edit()
            ?.putString(KEY_FOR_HISTORY_LIST, json)
            ?.apply()
    }
}