package pe.edu.upc.rickagendaapp.data.remote

import pe.edu.upc.rickagendaapp.data.entities.Character
import pe.edu.upc.rickagendaapp.data.entities.CharacterList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CharacterService {

    @GET("character")
    suspend fun getAllCharacters():Response<CharacterList>

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id")  id: Int): Response<Character>
}