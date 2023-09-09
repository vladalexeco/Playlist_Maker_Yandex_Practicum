package ru.vladalexeco.playlistmaker.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.vladalexeco.playlistmaker.playlist_info.data.db.CurrentPlaylistTracksDatabaseRepositoryImpl
import ru.vladalexeco.playlistmaker.playlist_info.domain.db.CurrentPlaylistTracksDatabaseInteractor
import ru.vladalexeco.playlistmaker.playlist_info.domain.db.CurrentPlaylistTracksDatabaseRepository
import ru.vladalexeco.playlistmaker.playlist_info.domain.impl.CurrentPlaylistTracksDatabaseInteractorImpl
import ru.vladalexeco.playlistmaker.playlist_info.presentation.PlaylistInfoViewModel

val playlistInfoModule = module {

    single<CurrentPlaylistTracksDatabaseRepository> {
        CurrentPlaylistTracksDatabaseRepositoryImpl(playlistTrackDatabase = get())
    }

    single<CurrentPlaylistTracksDatabaseInteractor> {
        CurrentPlaylistTracksDatabaseInteractorImpl(currentPlaylistTracksDatabaseRepository = get())
    }

    viewModel {
        PlaylistInfoViewModel(
            currentPlaylistTracksDatabaseInteractor = get(),
            playlistDatabaseInteractor = get(),
            playlistTrackDatabaseInteractor = get(),
            playlistMediaDatabaseInteractor = get()
        )
    }

}