package ru.vladalexeco.playlistmaker.new_playlist.domain.db

import ru.vladalexeco.playlistmaker.new_playlist.domain.models.Playlist

interface PlaylistDatabaseRepository {
    suspend fun insertPlaylistToDatabase(playlist: Playlist)

}