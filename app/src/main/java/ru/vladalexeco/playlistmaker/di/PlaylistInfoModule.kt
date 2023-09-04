package ru.vladalexeco.playlistmaker.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.vladalexeco.playlistmaker.playlist_info.presentation.PlaylistInfoViewModel

val playlistInfoModule = module {

    viewModel {
        PlaylistInfoViewModel()
    }

}