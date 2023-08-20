package ru.vladalexeco.playlistmaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.markodevcic.peko.PermissionRequester
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.vladalexeco.playlistmaker.di.*

const val SHARED_PREFERENCES = "SHARED_PREFERENCES"
const val KEY_FOR_APP_THEME = "KEY_FOR_APP_THEME"
const val BASE_URL = "http://itunes.apple.com"
const val KEY_FOR_HISTORY_LIST = "KEY_FOR_HISTORY_LIST"
const val KEY_FOR_PLAYER = "KEY_FOR_PLAYER"


class App: Application() {

    var darkTheme = false

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                playerModule,
                searchModule,
                settingsModule,
                sharingModule,
                medialibraryModule,
                databaseModule,
                newPlayListModule
            )
        }

        PermissionRequester.initialize(applicationContext)

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