package ru.vladalexeco.playlistmaker.settings.data.storage

import android.content.Context
import android.content.SharedPreferences
import ru.vladalexeco.playlistmaker.SHARED_PREFERENCES
import ru.vladalexeco.playlistmaker.KEY_FOR_APP_THEME

class ThemeStateStorageSharedPrefs(private val sharedPreferences: SharedPreferences): ThemeStateStorage {

//    private val sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)

    override fun getThemeStateStorage(): Boolean {
        return sharedPreferences.getBoolean(KEY_FOR_APP_THEME, false)
    }

    override fun saveThemeStateStorage(state: Boolean) {
        sharedPreferences.edit()
            .putBoolean(KEY_FOR_APP_THEME, state)
            .apply()
    }
}