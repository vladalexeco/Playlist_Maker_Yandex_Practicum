package ru.vladalexeco.playlistmaker.data.interfaces

import ru.vladalexeco.playlistmaker.data.models.ITunesServerResponse

interface ResponseFromServerListener {
    fun onResponseFromServerOccurred(iTunesServerResponse: ITunesServerResponse)
}