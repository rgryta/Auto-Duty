package eu.gryta.autoduty.pagerduty

import eu.gryta.autoduty.Endpoint
import eu.gryta.ktor.utils.ResponseWrapper
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.http.HeadersBuilder
import io.ktor.http.headers
import io.ktor.http.parameters
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


fun HttpClientConfig<*>.addLogging() {
    install(Logging) {
        level = LogLevel.ALL
    }
}

object PDClient {
    private const val URL = "https://api.pagerduty.com"
    private val client = HttpClient {
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
        val endpoint = Endpoint(client = client, url = URL)

        suspend fun get(userId: String): ResponseWrapper<eu.gryta.autoduty.pagerduty.Incidents> {
            return endpoint.get {
                url {
                        parameters.append("user_ids[]", userId)
                    }
            }
        }
    }

    object Users {
        private const val URL = "${PDClient.URL}/users"

        object Me {
            private const val URL = "${Users.URL}/me"

            private val endpoint = Endpoint(client = client, url = URL)

            suspend fun get(block: HttpRequestBuilder.() -> Unit = { }): ResponseWrapper<eu.gryta.autoduty.pagerduty.User> {
                return endpoint.get {
                    block()
                }
            }
        }
    }
}