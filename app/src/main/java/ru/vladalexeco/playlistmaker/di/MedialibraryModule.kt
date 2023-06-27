package ru.vladalexeco.playlistmaker.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.vladalexeco.playlistmaker.medialibrary.presentation.MedialibraryFavouritesViewModel
import ru.vladalexeco.playlistmaker.medialibrary.presentation.MedialibraryPlaylistsViewModel

val medialibraryModule = module {

    viewModel {
        MedialibraryFavouritesViewModel()
    }

    viewModel {
        MedialibraryPlaylistsViewModel()
    }
}