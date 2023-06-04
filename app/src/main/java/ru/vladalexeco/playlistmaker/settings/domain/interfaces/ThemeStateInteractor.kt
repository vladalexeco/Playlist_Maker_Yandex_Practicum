package ru.vladalexeco.playlistmaker.settings.domain.interfaces

interface ThemeStateInteractor {

    fun getThemeState(): Boolean

    fun saveThemeState(state: Boolean)

}