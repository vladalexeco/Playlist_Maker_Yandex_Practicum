package ru.vladalexeco.playlistmaker.sharing.data.repository

import ru.vladalexeco.playlistmaker.sharing.data.storage.StringStorage
import ru.vladalexeco.playlistmaker.sharing.domain.interfaces.StringStorageRepository

class StringStorageRepositoryImpl(val stringStorage: StringStorage): StringStorageRepository {
    override fun getStringById(id: Int): String {
        return stringStorage.getStringFromStorage(id)
    }
}