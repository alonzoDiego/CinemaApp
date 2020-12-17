package pe.edu.upc.rickagendaapp.data.repository

import pe.edu.upc.rickagendaapp.data.local.CharacterDao
import pe.edu.upc.rickagendaapp.data.remote.CharacterRemoteDS
import pe.edu.upc.rickagendaapp.utils.performGetOperation
import javax.inject.Inject

class CharacterRepository @Inject constructor(
     private val remoteDS: CharacterRemoteDS,
     private val localDS: CharacterDao
) {

    fun getCharacter(id: Int) = performGetOperation(
            databaseQuery = { localDS.getCharacter(id) },
            networkCall = { remoteDS.getCharacter(id) },
            saveCallResult = { localDS.insert(it) }
    )

    fun getCharacters() = performGetOperation(
            databaseQuery = { localDS.getAllCharacters() },
            networkCall = { remoteDS.getCharacters() },
            saveCallResult = {localDS.insertAll(it.results) }
    )
}