package ru.vladalexeco.playlistmaker.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.vladalexeco.playlistmaker.sharing.data.repository.StringStorageRepositoryImpl
import ru.vladalexeco.playlistmaker.sharing.data.storage.StringStorage
import ru.vladalexeco.playlistmaker.sharing.data.storage.StringStorageFromSystemResources
import ru.vladalexeco.playlistmaker.sharing.domain.interactors.StringStorageInteractorImpl
import ru.vladalexeco.playlistmaker.sharing.domain.interfaces.StringStorageInteractor
import ru.vladalexeco.playlistmaker.sharing.domain.interfaces.StringStorageRepository

val sharingModule = module {

    single<StringStorage> {
        StringStorageFromSystemResources(context = androidContext())
    }

    single<StringStorageRepository> {
        StringStorageRepositoryImpl(stringStorage = get())
    }

    factory<StringStorageInteractor> {
        StringStorageInteractorImpl(stringStorageRepository = get())
    }

}