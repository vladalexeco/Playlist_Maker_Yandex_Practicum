package ru.vladalexeco.playlistmaker.data.retrofit

import com.google.gson.annotations.SerializedName
import ru.vladalexeco.playlistmaker.domain.models.Track

class TrackResponse(@SerializedName("results") val tracks: ArrayList<Track>) {
}