package ru.vladalexeco.playlistmaker.domain.repository

interface ITunesRepository {
    fun getTracks(textRequest: String)
}