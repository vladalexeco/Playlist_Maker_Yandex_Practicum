package ru.vladalexeco.playlistmaker.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.vladalexeco.playlistmaker.new_playlist.data.repository.PlaylistDatabaseRepositoryImpl
import ru.vladalexeco.playlistmaker.new_playlist.domain.db.PlaylistDatabaseInteractor
import ru.vladalexeco.playlistmaker.new_playlist.domain.db.PlaylistDatabaseRepository
import ru.vladalexeco.playlistmaker.new_playlist.domain.impl.PlaylistDatabaseInteractorImpl
import ru.vladalexeco.playlistmaker.new_playlist.presentation.NewPlaylistViewModel

val newPlayListModule = module {

    viewModel {
        NewPlaylistViewModel(playlistDatabaseInteractor = get())
    }

    single<PlaylistDatabaseRepository> {
        PlaylistDatabaseRepositoryImpl(playlistDatabase = get())
    }

    single<PlaylistDatabaseInteractor> {
        PlaylistDatabaseInteractorImpl(playlistDatabaseRepository = get())
    }

}

