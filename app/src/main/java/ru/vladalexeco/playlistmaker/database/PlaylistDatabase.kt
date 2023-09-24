package ru.vladalexeco.playlistmaker.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.vladalexeco.playlistmaker.database.dao.PlaylistDao
import ru.vladalexeco.playlistmaker.database.entity.PlaylistEntity

@Database(version = 4, entities = [PlaylistEntity::class])
abstract class PlaylistDatabase : RoomDatabase() {

    abstract fun playlistDao(): PlaylistDao

}