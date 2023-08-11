package com.zara.rickandmorty.di

import com.zara.rickandmorty.data.remote.RickAndMortyApi
import com.zara.rickandmorty.data.remote.repository.CharacterRepository
import com.zara.rickandmorty.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCharacterRepository(
        api: RickAndMortyApi
    ) = CharacterRepository(api)

    @Singleton
    @Provides
    fun provideRickAndMortyApi(): RickAndMortyApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(RickAndMortyApi::class.java)
    }

}