package ru.vladalexeco.playlistmaker.settings.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import ru.vladalexeco.playlistmaker.App
import ru.vladalexeco.playlistmaker.R
import ru.vladalexeco.playlistmaker.settings.domain.interfaces.ThemeStateInteractor
import ru.vladalexeco.playlistmaker.sharing.domain.interfaces.StringStorageInteractor

class SettingsViewModel(
    val app: Application,
    private val themeStateInteractor: ThemeStateInteractor,
    private val stringStorageInteractor: StringStorageInteractor): AndroidViewModel(app) {

    fun getThemeState(): Boolean {
        return themeStateInteractor.getThemeState()
    }

    fun  saveAndChangeThemeState(state: Boolean) {
        (app as App).switchTheme(state)
        themeStateInteractor.saveThemeState(state)
    }

    fun getLinkToCourse(): String {
        return stringStorageInteractor.getString(R.string.course_android_developer)
    }

    fun getEmailMessage(): String {
        return stringStorageInteractor.getString(R.string.mail_message)
    }

    fun getEmailSubject(): String {
        return stringStorageInteractor.getString(R.string.mail_subject)
    }

    fun getPracticumOffer(): String {
        return stringStorageInteractor.getString(R.string.practicum_offer)
    }

    fun getArrayOfEmailAddresses(): Array<String> {
        return arrayOf("vladalexeco@yandex.ru")
    }
}