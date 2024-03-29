package ru.vladalexeco.playlistmaker.new_playlist.domain.models

import ru.vladalexeco.playlistmaker.database.entity.PlaylistEntity
import java.io.Serializable

data class Playlist(
    val id: Long = 0,
    val name: String,
    val description: String,
    val filePath: String,
    val listOfTracksId: String = "",
    val amountOfTracks: Int,
    val insertTimeStamp: Long? = 0L
) : Serializable

fun Playlist.mapToPlaylistEntity(): PlaylistEntity = PlaylistEntity(
    id = id,
    name = name,
    description = description,
    filePath = filePath,
    listOfTracksId = listOfTracksId,
    amountOfTracks = amountOfTracks,
    insertTimeStamp = insertTimeStamp
)

fun PlaylistEntity.mapToPlaylist(): Playlist = Playlist(
    id = id,
    name = name,
    description = description,
    filePath = filePath,
    listOfTracksId = listOfTracksId,
    amountOfTracks = amountOfTracks,
    insertTimeStamp = insertTimeStamp
)

