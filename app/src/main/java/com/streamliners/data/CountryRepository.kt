package com.streamliners.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.delay

class CountryRepository(
    private val client: HttpClient
) {
    class Response(
        val data: Map<String, Country>
    ) {
        class Country(
            val country: String,
            val region: String
        )
    }

    class Country(
        val code: String,
        val name: String,
        val region: String
    )

    suspend fun getCountries(): List<Country> {
        delay(2000)
        val response: Response = client.get("https://api.first.org/data/v1/countries").body()
        return response.data.map { (code, country) ->
            Country(
                code, country.country, country.region
            )
        }.sortedBy { it.name }
    }

}