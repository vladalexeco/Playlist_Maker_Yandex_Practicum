package ru.vladalexeco.playlistmaker.sharing.domain.interactors

import ru.vladalexeco.playlistmaker.sharing.domain.interfaces.StringStorageInteractor
import ru.vladalexeco.playlistmaker.sharing.domain.interfaces.StringStorageRepository

class StringStorageInteractorImpl(private val stringStorageRepository: StringStorageRepository): StringStorageInteractor {
    override fun getString(id: Int): String {
        return stringStorageRepository.getStringById(id)
    }
}