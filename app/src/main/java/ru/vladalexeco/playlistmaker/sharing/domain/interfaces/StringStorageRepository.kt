package ru.vladalexeco.playlistmaker.sharing.domain.interfaces

interface StringStorageRepository {
    fun getStringById(id: Int): String
}