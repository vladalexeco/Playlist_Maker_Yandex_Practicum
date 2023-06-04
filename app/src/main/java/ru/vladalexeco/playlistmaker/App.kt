package ru.vladalexeco.playlistmaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

const val SHARED_PREFERENCES = "SHARED_PREFERENCES"
const val KEY_FOR_APP_THEME = "KEY_FOR_APP_THEME"


class App: Application() {

    var darkTheme = false

    override fun onCreate() {
        super.onCreate()

        val sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE)

        switchTheme(sharedPreferences.getBoolean(KEY_FOR_APP_THEME, false))
    }

    fun switchTheme(darkThemeEnabled: Boolean) {
        darkTheme = darkThemeEnabled
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
}