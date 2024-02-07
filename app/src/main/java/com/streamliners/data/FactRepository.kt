package com.streamliners.data

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.delay

class FactRepository(
    private val client: HttpClient
) {

    suspend fun getFact(num: Int): String {
        delay(2000)
        return client.get("http://numbersapi.com/$num").bodyAsText()
    }

    suspend fun getFact(num: String): String {
        delay(2000)
        return client.get("http://numbersapi.com/$num").bodyAsText()
    }

}