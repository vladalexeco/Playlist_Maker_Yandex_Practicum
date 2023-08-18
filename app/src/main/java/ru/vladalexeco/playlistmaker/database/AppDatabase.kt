package ru.vladalexeco.playlistmaker.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.vladalexeco.playlistmaker.database.dao.TrackDao
import ru.vladalexeco.playlistmaker.database.entity.TrackEntity

@Database(version = 2, entities = [TrackEntity::class])
abstract class AppDatabase: RoomDatabase() {
    abstract fun trackDao(): TrackDao
}