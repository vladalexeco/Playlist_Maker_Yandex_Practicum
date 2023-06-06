package ru.vladalexeco.playlistmaker.util

import android.content.Context
import ru.vladalexeco.playlistmaker.player.data.repository.AudioPlayerRepositoryImpl
import ru.vladalexeco.playlistmaker.player.domain.interactors.AudioPlayerInteractorImpl
import ru.vladalexeco.playlistmaker.player.domain.interfaces.AudioPlayerInteractor
import ru.vladalexeco.playlistmaker.player.domain.interfaces.AudioPlayerRepository
import ru.vladalexeco.playlistmaker.player.domain.models.TrackUrl
import ru.vladalexeco.playlistmaker.search.data.network.RetrofitNetworkClient
import ru.vladalexeco.playlistmaker.search.data.repository.HistoryTrackRepositorySHImpl
import ru.vladalexeco.playlistmaker.search.data.repository.TracksSearchSearchRepositoryImpl
import ru.vladalexeco.playlistmaker.search.data.storage.TrackSearchHistoryStorageSharedPrefs
import ru.vladalexeco.playlistmaker.search.domain.interactors.TrackHistoryInteractorImpl
import ru.vladalexeco.playlistmaker.search.domain.interactors.TracksSearchSearchInteractorImpl
import ru.vladalexeco.playlistmaker.search.domain.interfaces.HistoryTrackRepositorySH
import ru.vladalexeco.playlistmaker.search.domain.interfaces.TrackHistoryInteractor
import ru.vladalexeco.playlistmaker.search.domain.interfaces.TracksSearchInteractor
import ru.vladalexeco.playlistmaker.search.domain.interfaces.TracksSearchRepository
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

    //    settings/sharing

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

    //    *settings/sharing


    //    search

    private fun getTracksSearchRepository(context: Context): TracksSearchRepository {
        return TracksSearchSearchRepositoryImpl(RetrofitNetworkClient(context))
    }

    fun provideTracksSearchInteractor(context: Context): TracksSearchInteractor {
        return TracksSearchSearchInteractorImpl(getTracksSearchRepository(context))
    }

    private fun getHistoryTrackRepositorySH(context: Context): HistoryTrackRepositorySH {
        return HistoryTrackRepositorySHImpl(TrackSearchHistoryStorageSharedPrefs(context))
    }

    fun provideTrackHistoryInteractor(context: Context): TrackHistoryInteractor {
        return TrackHistoryInteractorImpl(getHistoryTrackRepositorySH(context))
    }

    //    *search


    //    player

    private fun getAudioPlayerRepository(): AudioPlayerRepository {
        return AudioPlayerRepositoryImpl()
    }

    fun provideAudioPlayerInteractor(trackUrl: TrackUrl): AudioPlayerInteractor {
        return AudioPlayerInteractorImpl(trackUrl, getAudioPlayerRepository())
    }

    //    *player
}
