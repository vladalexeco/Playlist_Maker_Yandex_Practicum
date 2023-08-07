package ru.vladalexeco.playlistmaker.search.data.network

import ru.vladalexeco.playlistmaker.search.data.dto.Response

interface NetworkClient {
    suspend fun doRequest(dto: Any): Response
}