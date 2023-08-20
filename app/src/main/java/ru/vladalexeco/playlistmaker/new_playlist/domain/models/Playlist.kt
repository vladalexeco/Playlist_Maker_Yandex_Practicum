package ru.vladalexeco.playlistmaker.new_playlist.domain.models

import ru.vladalexeco.playlistmaker.database.entity.PlaylistEntity

data class Playlist(
    val name: String,
    val description: String,
    val filePath: String,
    val listOfTracksId: String = "",
    val amountOfTracks: Int
)

fun Playlist.mapToPlaylistEntity(): PlaylistEntity = PlaylistEntity(
    name = name,
    description = description,
    filePath = filePath,
    listOfTracksId = listOfTracksId,
    amountOfTracks = amountOfTracks
)

