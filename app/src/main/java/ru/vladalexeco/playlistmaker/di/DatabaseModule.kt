package ru.vladalexeco.playlistmaker.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.vladalexeco.playlistmaker.database.AppDatabase
import ru.vladalexeco.playlistmaker.database.PlaylistDatabase
import ru.vladalexeco.playlistmaker.database.PlaylistTrackDatabase

val databaseModule = module {

    single<AppDatabase> {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    single<PlaylistDatabase> {
        Room.databaseBuilder(androidContext(), PlaylistDatabase::class.java, "playlist_database.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    single<PlaylistTrackDatabase> {
        Room.databaseBuilder(androidContext(), PlaylistTrackDatabase::class.java, "playlist_track_databases.db")
            .fallbackToDestructiveMigration()
            .build()
    }

}