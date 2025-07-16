package eu.gryta.autoduty.pagerduty

import eu.gryta.ktor.utils.Endpoint
import eu.gryta.ktor.utils.ResponseWrapper
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.HeadersBuilder
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


fun HttpClientConfig<*>.addLogging() {
    install(Logging) {
        level = LogLevel.ALL
    }
}

object PDClient {
    private const val URL = "https://api.pagerduty.com"
    val client = HttpClient {
        addLogging()
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }
    }

    object Incidents {
        private const val URL = "${PDClient.URL}/incidents"

        private val endpoint = Endpoint(client = client, url = URL)

        suspend fun get(headers: HeadersBuilder.() -> Unit = { }): ResponseWrapper<eu.gryta.autoduty.pagerduty.Incidents> {
            return endpoint.get { headers() }
        }
    }

    object Users {
        private const val URL = "${PDClient.URL}/users"

        object Me {
            private const val URL = "${Users.URL}/me"

            private val endpoint = Endpoint(client = client, url = URL)

            suspend fun get(headers: HeadersBuilder.() -> Unit = { }): ResponseWrapper<eu.gryta.autoduty.pagerduty.Me> {
                return endpoint.get { headers() }
            }
        }
    }
}