package ru.vladalexeco.playlistmaker.player.domain.interactors

import kotlinx.coroutines.flow.Flow
import ru.vladalexeco.playlistmaker.player.domain.converters.PlayerTrackDataConverter
import ru.vladalexeco.playlistmaker.player.domain.interfaces.AudioPlayerDatabaseInteractor
import ru.vladalexeco.playlistmaker.player.domain.interfaces.AudioPlayerDatabaseRepository
import ru.vladalexeco.playlistmaker.player.domain.models.PlayerTrack

class AudioPlayerDatabaseInteractorImpl(
    private val audioPlayerDatabaseRepository: AudioPlayerDatabaseRepository,
    private val playerTrackDataConverter: PlayerTrackDataConverter
) : AudioPlayerDatabaseInteractor {

    override suspend fun addPlayerTrackToDatabase(playerTrack: PlayerTrack) {
        audioPlayerDatabaseRepository.addPlayerTrackToDatabase(playerTrackDataConverter.map(playerTrack))
    }

    override suspend fun deletePlayerTrackFromDatabase(playerTrack: PlayerTrack) {
        audioPlayerDatabaseRepository.deletePlayerTrackFromDatabase(playerTrackDataConverter.map(playerTrack))
    }

    override suspend fun getTracksIdFromDatabase(): Flow<List<Int>> {
        return audioPlayerDatabaseRepository.getTracksIdFromDatabase()
    }
}