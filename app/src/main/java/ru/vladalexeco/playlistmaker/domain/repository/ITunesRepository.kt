package ru.vladalexeco.playlistmaker.domain.repository

import ru.vladalexeco.playlistmaker.data.models.ITunesServerResponse
import ru.vladalexeco.playlistmaker.data.retrofit.ServerResponse
import ru.vladalexeco.playlistmaker.domain.models.Track

interface ITunesRepository {
    fun getTracks(textRequest: String)
}