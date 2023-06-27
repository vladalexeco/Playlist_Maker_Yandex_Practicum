package ru.vladalexeco.playlistmaker.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.vladalexeco.playlistmaker.medialibrary.presentation.MedialibraryViewModel

val medialibraryModule = module {

    viewModel {
        MedialibraryViewModel()
    }
}