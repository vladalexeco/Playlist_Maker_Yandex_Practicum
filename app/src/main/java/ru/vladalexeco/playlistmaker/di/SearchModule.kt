package ru.vladalexeco.playlistmaker.di

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.vladalexeco.playlistmaker.search.data.network.ITunesApi
import ru.vladalexeco.playlistmaker.search.data.network.NetworkClient
import ru.vladalexeco.playlistmaker.search.data.network.RetrofitNetworkClient
import ru.vladalexeco.playlistmaker.search.data.repository.HistoryTrackRepositorySHImpl
import ru.vladalexeco.playlistmaker.search.data.repository.TracksSearchSearchRepositoryImpl
import ru.vladalexeco.playlistmaker.search.data.storage.TrackSearchHistoryStorage
import ru.vladalexeco.playlistmaker.search.data.storage.TrackSearchHistoryStorageSharedPrefs
import ru.vladalexeco.playlistmaker.search.domain.interactors.TrackHistoryInteractorImpl
import ru.vladalexeco.playlistmaker.search.domain.interactors.TracksSearchSearchInteractorImpl
import ru.vladalexeco.playlistmaker.search.domain.interfaces.HistoryTrackRepositorySH
import ru.vladalexeco.playlistmaker.search.domain.interfaces.TrackHistoryInteractor
import ru.vladalexeco.playlistmaker.search.domain.interfaces.TracksSearchInteractor
import ru.vladalexeco.playlistmaker.search.domain.interfaces.TracksSearchRepository
import ru.vladalexeco.playlistmaker.search.presentation.SearchingViewModel

const val SHARED_PREFERENCES = "SHARED_PREFERENCES"
const val BASE_URL = "http://itunes.apple.com"

val searchModule = module {

    single {
        androidContext().getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
    }

    single<TrackSearchHistoryStorage> {
        TrackSearchHistoryStorageSharedPrefs(sharedPreferences = get())
    }

    single<HistoryTrackRepositorySH> {
        HistoryTrackRepositorySHImpl(trackSearchHistoryStorage = get())
    }

    single<ITunesApi> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ITunesApi::class.java)
    }

    single<NetworkClient> {
        RetrofitNetworkClient(context = androidContext(), trackService = get())
    }

    single<TracksSearchRepository> {
        TracksSearchSearchRepositoryImpl(networkClient = get())
    }

    factory<TrackHistoryInteractor> {
        TrackHistoryInteractorImpl(historyTrackRepositorySH = get())
    }

    factory<TracksSearchInteractor> {
        TracksSearchSearchInteractorImpl(tracksSearchRepository = get())
    }

    viewModel {
        SearchingViewModel(tracksSearchInteractor = get(), trackHistoryInteractor = get())
    }

}