package ru.vladalexeco.playlistmaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.vladalexeco.playlistmaker.di.playerModule
import ru.vladalexeco.playlistmaker.di.searchModule
import ru.vladalexeco.playlistmaker.di.settingsModule
import ru.vladalexeco.playlistmaker.di.sharingModule

const val SHARED_PREFERENCES = "SHARED_PREFERENCES"
const val KEY_FOR_APP_THEME = "KEY_FOR_APP_THEME"


class App: Application() {

    var darkTheme = false

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(playerModule, searchModule, settingsModule, sharingModule)
        }

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