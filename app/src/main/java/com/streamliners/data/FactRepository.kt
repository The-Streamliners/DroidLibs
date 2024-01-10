package com.streamliners.data

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

class FactRepository(
    private val client: HttpClient
) {

    suspend fun getFact(num: Int): String {
        return client.get("http://numbersapi.com/$num").bodyAsText()
    }

}