package ru.vladalexeco.playlistmaker.player.data.repository

import ru.vladalexeco.playlistmaker.database.PlaylistTrackDatabase
import ru.vladalexeco.playlistmaker.player.domain.interfaces.PlaylistTrackDatabaseRepository
import ru.vladalexeco.playlistmaker.search.domain.models.Track
import ru.vladalexeco.playlistmaker.search.domain.models.mapToPlaylistTrackEntity

class PlaylistTrackDatabaseRepositoryImpl(
    private val playlistTrackDatabase: PlaylistTrackDatabase
) : PlaylistTrackDatabaseRepository {

    override suspend fun insertTrackToPlaylistTrackDatabase(track: Track) {
        playlistTrackDatabase.playlistTrackDao().insertTrack(track.mapToPlaylistTrackEntity())
    }

    override suspend fun deletePlaylistTrackFromDatabase(track: Track) {
        playlistTrackDatabase.playlistTrackDao().deletePlaylistTrack(track.mapToPlaylistTrackEntity(newTimeStamp = false))
    }
}