package ru.vladalexeco.playlistmaker.domain.models

import ru.vladalexeco.playlistmaker.data.retrofit.ServerResponse
import ru.vladalexeco.playlistmaker.domain.models.Track

data class ITunesServerResponse(val tracks: ArrayList<Track>, val serverResponse: ServerResponse?, val serverResponseCode: Int?)
