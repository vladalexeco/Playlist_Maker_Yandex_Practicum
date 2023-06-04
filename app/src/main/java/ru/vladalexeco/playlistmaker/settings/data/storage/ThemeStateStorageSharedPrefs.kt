package ru.vladalexeco.playlistmaker.settings.data.storage

import android.content.Context

const val SHARED_PREFERENCES = "SHARED_PREFERENCES"
const val KEY_FOR_APP_THEME = "KEY_FOR_APP_THEME"

class ThemeStateStorageSharedPrefs(context: Context): ThemeStateStorage {

    private val sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)

    override fun getThemeStateStorage(): Boolean {
        return sharedPreferences.getBoolean(KEY_FOR_APP_THEME, false)
    }

    override fun saveThemeStateStorage(state: Boolean) {
        sharedPreferences.edit()
            .putBoolean(ru.vladalexeco.playlistmaker.KEY_FOR_APP_THEME, state)
            .apply()
    }
}