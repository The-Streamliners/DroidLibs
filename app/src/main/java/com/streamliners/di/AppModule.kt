package com.streamliners.di

import com.streamliners.data.CountryRepository
import com.streamliners.data.FactRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.gson.gson
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(CIO) {
            expectSuccess = true
            install(ContentNegotiation) { gson() }
        }
    }

    @Provides
    @Singleton
    fun provideFactRepo(
        client: HttpClient
    ): FactRepository {
        return FactRepository(client)
    }

    @Provides
    @Singleton
    fun provideCountryRepo(
        client: HttpClient
    ): CountryRepository {
        return CountryRepository(client)
    }

}