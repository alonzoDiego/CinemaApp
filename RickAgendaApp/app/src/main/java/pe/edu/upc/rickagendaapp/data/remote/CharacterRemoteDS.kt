package pe.edu.upc.rickagendaapp.data.remote

import javax.inject.Inject

class CharacterRemoteDS @Inject constructor(
    private val characterService: CharacterService
): BaseDataSource() {

    suspend fun getCharacters() = getResult { characterService.getAllCharacters() }
    suspend fun getCharacter(id: Int) = getResult {characterService.getCharacter(id)}
}