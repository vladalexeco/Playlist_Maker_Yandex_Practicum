package ru.vladalexeco.playlistmaker.settings.domain.interactors

import ru.vladalexeco.playlistmaker.settings.domain.interfaces.ThemeStateInteractor
import ru.vladalexeco.playlistmaker.settings.domain.interfaces.ThemeStateRepository

class ThemeStateInteractorImpl(val themeStateRepository: ThemeStateRepository): ThemeStateInteractor {

    override fun getThemeState(): Boolean {
        return themeStateRepository.getThemeStateData()
    }

    override fun saveThemeState(state: Boolean) {
        themeStateRepository.saveThemeStateData(state)
    }
}