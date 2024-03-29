package ru.vladalexeco.playlistmaker.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.vladalexeco.playlistmaker.database.dao.PlaylistTrackDao
import ru.vladalexeco.playlistmaker.database.entity.PlaylistTrackEntity

@Database(version = 1, entities = [PlaylistTrackEntity::class])
abstract class PlaylistTrackDatabase : RoomDatabase() {

    abstract fun playlistTrackDao(): PlaylistTrackDao

}

