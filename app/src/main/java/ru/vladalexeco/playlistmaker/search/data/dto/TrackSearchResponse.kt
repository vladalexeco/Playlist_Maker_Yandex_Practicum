package ru.vladalexeco.playlistmaker.search.data.dto

import com.google.gson.annotations.SerializedName
import ru.vladalexeco.playlistmaker.search.domain.models.Track

class TrackSearchResponse(@SerializedName("results") val tracks: ArrayList<TrackDto>): Response() {
}