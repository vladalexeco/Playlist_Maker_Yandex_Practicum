package ru.vladalexeco.playlistmaker.util

sealed class Resource<T>(val data: T? = null, val response: ServerResponse? = null) {
    class Success<T>(data: T): Resource<T>(data)
    class Error<T>(response: ServerResponse, data: T? = null): Resource<T>(data, response)
}
