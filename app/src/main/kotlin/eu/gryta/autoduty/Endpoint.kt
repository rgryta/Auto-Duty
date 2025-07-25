package eu.gryta.autoduty

import eu.gryta.ktor.utils.ApiInstance
import eu.gryta.ktor.utils.ResponseWrapper
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HeadersBuilder
import io.ktor.http.HttpHeaders
import io.ktor.http.parameters
import io.ktor.util.reflect.typeInfo
import kotlin.reflect.KClass

class Endpoint(
    val client: HttpClient,
    val url: String,
    private val apiInstanceClass: KClass<out ApiInstance> = ApiInstance::class,
) {
    suspend fun getInstance(): ApiInstance = ApiInstance.getInstance(apiInstanceClass)

    suspend inline fun <reified T : Any> get(
        crossinline block: HttpRequestBuilder.() -> Unit = { },
    ): ResponseWrapper<T> {
        val instance = getInstance()
        val response: HttpResponse = client.get(url) {
            headers {
                // Authorize if available
                this[HttpHeaders.Authorization] ?: instance.token?.let { token ->
                    append(HttpHeaders.Authorization, token.toString())
                }
            }
            block()
        }
        return ResponseWrapper(response, typeInfo<T>())
    }
    }