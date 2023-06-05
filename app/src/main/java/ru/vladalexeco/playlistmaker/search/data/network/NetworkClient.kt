package ru.vladalexeco.playlistmaker.search.data.network

import ru.vladalexeco.playlistmaker.search.data.dto.Response

interface NetworkClient {
    fun doRequest(dto: Any): Response
}