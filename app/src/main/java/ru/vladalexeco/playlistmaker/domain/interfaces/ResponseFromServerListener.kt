package ru.vladalexeco.playlistmaker.domain.interfaces

import ru.vladalexeco.playlistmaker.domain.models.ITunesServerResponse

interface ResponseFromServerListener {
    fun onResponseFromServerOccurred(iTunesServerResponse: ITunesServerResponse)
}