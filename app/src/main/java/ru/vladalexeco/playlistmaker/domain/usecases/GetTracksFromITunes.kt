package ru.vladalexeco.playlistmaker.domain.usecases

import ru.vladalexeco.playlistmaker.data.models.ITunesServerResponse
import ru.vladalexeco.playlistmaker.domain.models.Track
import ru.vladalexeco.playlistmaker.domain.repository.ITunesRepository

class GetTracksFromITunes(private val iTunesRepository: ITunesRepository) {
    fun execute(textRequest: String) {
        iTunesRepository.getTracks(textRequest)
    }
}

