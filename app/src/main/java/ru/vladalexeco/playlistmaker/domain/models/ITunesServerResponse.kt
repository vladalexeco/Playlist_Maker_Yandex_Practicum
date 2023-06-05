package ru.vladalexeco.playlistmaker.domain.models

import ru.vladalexeco.playlistmaker.search.data.dto.ServerResponse
import ru.vladalexeco.playlistmaker.search.domain.models.Track

data class ITunesServerResponse(val tracks: ArrayList<Track>, val serverResponse: ServerResponse?, val serverResponseCode: Int?)
