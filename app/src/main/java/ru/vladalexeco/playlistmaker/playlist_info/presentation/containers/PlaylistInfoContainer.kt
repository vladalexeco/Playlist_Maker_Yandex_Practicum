package ru.vladalexeco.playlistmaker.playlist_info.presentation.containers

import ru.vladalexeco.playlistmaker.search.domain.models.Track

data class PlaylistInfoContainer(
    val totalTime: String,
    val playlistTracks: List<Track>
)