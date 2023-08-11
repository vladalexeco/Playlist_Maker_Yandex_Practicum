package ru.vladalexeco.playlistmaker.di

import android.media.MediaPlayer
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import ru.vladalexeco.playlistmaker.player.data.converters.PlayerTrackDbConverter
import ru.vladalexeco.playlistmaker.player.data.repository.AudioPlayerDatabaseRepositoryImpl
import ru.vladalexeco.playlistmaker.player.data.repository.AudioPlayerRepositoryImpl
import ru.vladalexeco.playlistmaker.player.domain.converters.PlayerTrackDataConverter
import ru.vladalexeco.playlistmaker.player.domain.interactors.AudioPlayerDatabaseInteractorImpl
import ru.vladalexeco.playlistmaker.player.domain.interactors.AudioPlayerInteractorImpl
import ru.vladalexeco.playlistmaker.player.domain.interfaces.AudioPlayerDatabaseInteractor
import ru.vladalexeco.playlistmaker.player.domain.interfaces.AudioPlayerDatabaseRepository
import ru.vladalexeco.playlistmaker.player.domain.interfaces.AudioPlayerInteractor
import ru.vladalexeco.playlistmaker.player.domain.interfaces.AudioPlayerRepository
import ru.vladalexeco.playlistmaker.player.domain.models.PlayerTrack
import ru.vladalexeco.playlistmaker.player.presentation.PlayerViewModel

val playerModule = module {

    factory { MediaPlayer() }

    factory <AudioPlayerRepository> {
        AudioPlayerRepositoryImpl(mediaPlayer = get())
    }

    factory<AudioPlayerInteractor> { (playerTrack: PlayerTrack) ->
        AudioPlayerInteractorImpl(playerTrack = playerTrack, audioPlayerRepository = get())
    }

    viewModel { (playerTrack: PlayerTrack) ->
        PlayerViewModel(
            playerTrack = playerTrack,
            audioPlayerInteractor = get { parametersOf(playerTrack) },
            audioPlayerDatabaseInteractor = get()
        )
    }

    factory<PlayerTrackDbConverter> { PlayerTrackDbConverter() }

    single<AudioPlayerDatabaseRepository> {
        AudioPlayerDatabaseRepositoryImpl(appDatabase = get(), playerTrackDbConverter = get())
    }

    factory<PlayerTrackDataConverter> { PlayerTrackDataConverter() }

    single<AudioPlayerDatabaseInteractor> {
        AudioPlayerDatabaseInteractorImpl(audioPlayerDatabaseRepository = get(), playerTrackDataConverter = get())
    }
}

