package ru.vladalexeco.playlistmaker.util

import android.content.Context
import ru.vladalexeco.playlistmaker.settings.data.repository.ThemeStateRepositoryImpl
import ru.vladalexeco.playlistmaker.settings.data.storage.ThemeStateStorageSharedPrefs
import ru.vladalexeco.playlistmaker.settings.domain.interactors.ThemeStateInteractorImpl
import ru.vladalexeco.playlistmaker.settings.domain.interfaces.ThemeStateInteractor
import ru.vladalexeco.playlistmaker.settings.domain.interfaces.ThemeStateRepository
import ru.vladalexeco.playlistmaker.sharing.data.repository.StringStorageRepositoryImpl
import ru.vladalexeco.playlistmaker.sharing.data.storage.StringStorageFromSystemResources
import ru.vladalexeco.playlistmaker.sharing.domain.interactors.StringStorageInteractorImpl
import ru.vladalexeco.playlistmaker.sharing.domain.interfaces.StringStorageInteractor
import ru.vladalexeco.playlistmaker.sharing.domain.interfaces.StringStorageRepository

object Creator {

    private fun getThemeStateRepository(context: Context): ThemeStateRepository {
        return ThemeStateRepositoryImpl(ThemeStateStorageSharedPrefs(context))
    }

    fun provideThemeStateInteractor(context: Context): ThemeStateInteractor {
        return ThemeStateInteractorImpl(getThemeStateRepository(context))
    }

    private fun getStringStorageRepository(context: Context): StringStorageRepository {
        return StringStorageRepositoryImpl(StringStorageFromSystemResources(context))
    }

    fun provideStringStorageInteractor(context: Context): StringStorageInteractor {
        return StringStorageInteractorImpl(getStringStorageRepository(context))
    }

}