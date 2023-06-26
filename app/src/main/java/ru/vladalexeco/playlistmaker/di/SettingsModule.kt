package ru.vladalexeco.playlistmaker.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.vladalexeco.playlistmaker.settings.data.repository.ThemeStateRepositoryImpl
import ru.vladalexeco.playlistmaker.settings.data.storage.ThemeStateStorage
import ru.vladalexeco.playlistmaker.settings.data.storage.ThemeStateStorageSharedPrefs
import ru.vladalexeco.playlistmaker.settings.domain.interactors.ThemeStateInteractorImpl
import ru.vladalexeco.playlistmaker.settings.domain.interfaces.ThemeStateInteractor
import ru.vladalexeco.playlistmaker.settings.domain.interfaces.ThemeStateRepository
import ru.vladalexeco.playlistmaker.settings.presentation.SettingsViewModel

val settingsModule = module {

    single<ThemeStateStorage> {
        ThemeStateStorageSharedPrefs(sharedPreferences = get())
    }

    single<ThemeStateRepository> {
        ThemeStateRepositoryImpl(themeStateStorage = get())
    }

    factory<ThemeStateInteractor> {
        ThemeStateInteractorImpl(themeStateRepository = get())
    }

    single {
        androidContext().applicationContext as Application
    }

    viewModel {
        SettingsViewModel(app = get(), themeStateInteractor = get(), stringStorageInteractor = get())
    }

}