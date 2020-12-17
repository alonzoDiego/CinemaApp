package pe.edu.upc.rickagendaapp.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import pe.edu.upc.rickagendaapp.data.local.AppDatabase
import pe.edu.upc.rickagendaapp.data.local.CharacterDao
import pe.edu.upc.rickagendaapp.data.remote.CharacterRemoteDS
import pe.edu.upc.rickagendaapp.data.remote.CharacterService
import pe.edu.upc.rickagendaapp.data.repository.CharacterRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideCharacterService(retrofit: Retrofit): CharacterService = retrofit.create(CharacterService::class.java)

    @Singleton
    @Provides
    fun provideCharacterRemoteDS(characterService: CharacterService) = CharacterRemoteDS(characterService)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun providerCharacterDao(db: AppDatabase) = db.characterDo()

    @Singleton
    @Provides
    fun providerRepository(remoteDS: CharacterRemoteDS, localDS: CharacterDao) = CharacterRepository(remoteDS, localDS)

}