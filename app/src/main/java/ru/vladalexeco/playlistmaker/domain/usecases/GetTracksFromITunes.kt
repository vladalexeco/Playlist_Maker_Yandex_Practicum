package ru.vladalexeco.playlistmaker.domain.usecases

import ru.vladalexeco.playlistmaker.domain.repository.ITunesRepository

class GetTracksFromITunes(private val iTunesRepository: ITunesRepository) {
    fun execute(textRequest: String) {
        iTunesRepository.getTracks(textRequest)
    }
}

