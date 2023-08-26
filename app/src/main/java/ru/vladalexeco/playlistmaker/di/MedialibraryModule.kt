package ru.vladalexeco.playlistmaker.di


import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.vladalexeco.playlistmaker.medialibrary.data.converters.TrackDbConverter
import ru.vladalexeco.playlistmaker.medialibrary.data.repository.LibraryDatabaseRepositoryImpl
import ru.vladalexeco.playlistmaker.medialibrary.data.repository.PlaylistMediaDatabaseRepositoryImpl
import ru.vladalexeco.playlistmaker.medialibrary.domain.converters.LibraryTrackDataConverter
import ru.vladalexeco.playlistmaker.medialibrary.domain.converters.LibraryTrackToTrackConverter
import ru.vladalexeco.playlistmaker.medialibrary.domain.db.LibraryDatabaseInteractor
import ru.vladalexeco.playlistmaker.medialibrary.domain.db.LibraryDatabaseRepository
import ru.vladalexeco.playlistmaker.medialibrary.domain.db.PlaylistMediaDatabaseInteractor
import ru.vladalexeco.playlistmaker.medialibrary.domain.db.PlaylistMediaDatabaseRepository
import ru.vladalexeco.playlistmaker.medialibrary.domain.impl.LibraryDatabaseInteractorImpl
import ru.vladalexeco.playlistmaker.medialibrary.domain.impl.PlaylistMediaDatabaseInteractorImpl
import ru.vladalexeco.playlistmaker.medialibrary.presentation.MedialibraryFavouritesViewModel
import ru.vladalexeco.playlistmaker.medialibrary.presentation.MedialibraryPlaylistsViewModel

val medialibraryModule = module {

    viewModel {
        MedialibraryFavouritesViewModel(libraryDatabaseInteractor = get(), libraryTrackToTrackConverter = get())
    }

    viewModel {
        MedialibraryPlaylistsViewModel(playlistMediaDatabaseInteractor = get())
    }

    factory<TrackDbConverter> { TrackDbConverter() }

    factory<LibraryTrackToTrackConverter> { LibraryTrackToTrackConverter() }

    single<LibraryDatabaseRepository> {
        LibraryDatabaseRepositoryImpl(appDatabase = get(), trackDbConverter = get())
    }

    factory<LibraryTrackDataConverter> { LibraryTrackDataConverter() }

    single<LibraryDatabaseInteractor> {
        LibraryDatabaseInteractorImpl(libraryDatabaseRepository = get(), libraryTrackDataConverter = get())
    }

    single<PlaylistMediaDatabaseRepository> {
        PlaylistMediaDatabaseRepositoryImpl(playlistDatabase = get())
    }

    single<PlaylistMediaDatabaseInteractor> {
        PlaylistMediaDatabaseInteractorImpl(playlistMediaDatabaseRepository = get())
    }
}

