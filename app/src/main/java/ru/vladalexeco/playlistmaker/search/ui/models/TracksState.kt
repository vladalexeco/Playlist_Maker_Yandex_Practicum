package ru.vladalexeco.playlistmaker.search.ui.models

import ru.vladalexeco.playlistmaker.search.domain.models.Track

data class TracksState(
    val tracks: List<Track>,
    val isLoading: Boolean,
    val isFailed: Boolean?
)