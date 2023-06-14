package ru.vladalexeco.playlistmaker.settings.data.repository

import ru.vladalexeco.playlistmaker.settings.data.storage.ThemeStateStorage
import ru.vladalexeco.playlistmaker.settings.domain.interfaces.ThemeStateRepository

class ThemeStateRepositoryImpl(val themeStateStorage: ThemeStateStorage): ThemeStateRepository {
    override fun getThemeStateData(): Boolean {
        return themeStateStorage.getThemeStateStorage()
    }

    override fun saveThemeStateData(state: Boolean) {
        themeStateStorage.saveThemeStateStorage(state)
    }
}