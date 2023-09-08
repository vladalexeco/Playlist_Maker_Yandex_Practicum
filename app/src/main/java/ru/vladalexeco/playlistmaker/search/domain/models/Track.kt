package ru.vladalexeco.playlistmaker.search.domain.models

import com.google.gson.annotations.SerializedName
import ru.vladalexeco.playlistmaker.database.entity.PlaylistTrackEntity
import ru.vladalexeco.playlistmaker.player.domain.models.PlayerTrack
import java.io.Serializable

data class Track(
    val trackId: Int,
    val trackName: String?,
    val artistName: String?,
    @SerializedName("trackTimeMillis") val trackTime: String?,
    @SerializedName("artworkUrl100") val artworkUrl: String?,
    val collectionName: String?,
    val releaseDate: String?,
    val primaryGenreName: String?,
    val country: String?,
    val previewUrl: String?,
    val insertTimeStamp: Long? = null
): Serializable

fun Track.mapToPlaylistTrackEntity(): PlaylistTrackEntity {
    return PlaylistTrackEntity(
        trackId,
        trackName,
        artistName,
        trackTime,
        artworkUrl,
        collectionName,
        releaseDate,
        primaryGenreName,
        country,
        previewUrl,
        System.currentTimeMillis()
    )
}

fun Track.mapToPlayerTrack(): PlayerTrack {
    return PlayerTrack(trackId, trackName, artistName, trackTime, artworkUrl, collectionName, releaseDate, primaryGenreName, country, previewUrl, null)
}

fun PlaylistTrackEntity.mapToTrack(): Track {
    return Track(
        trackId,
        trackName,
        artistName,
        trackTime,
        artworkUrl,
        collectionName,
        releaseDate,
        primaryGenreName,
        country,
        previewUrl,
        insertTimeStamp
    )
}