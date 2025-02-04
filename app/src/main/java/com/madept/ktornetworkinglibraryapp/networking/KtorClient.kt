package com.madept.ktornetworkinglibraryapp.networking

import com.madept.ktornetworkinglibraryapp.BuildConfig
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object KtorClient {
    val baseUrl = BuildConfig.BASE_URL

    val client = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }

        // Global Exception Handling
        HttpResponseValidator {
            validateResponse { response ->
                when (response.status) {
                    HttpStatusCode.Unauthorized -> throw ApiException.ServerError("Unauthorized Access")
                    HttpStatusCode.Forbidden -> throw ApiException.ServerError("Forbidden Request")
                    HttpStatusCode.NotFound -> throw ApiException.ServerError("Resource Not Found")
                    HttpStatusCode.InternalServerError -> throw ApiException.ServerError("Server Error")
                }
            }

            handleResponseExceptionWithRequest { cause, _ ->
                throw when (cause) {
                    is ServerResponseException -> ApiException.ServerError("Server Error: ${cause.message}")
                    is ClientRequestException -> ApiException.ServerError("Client Error: ${cause.message}")
                    is RedirectResponseException -> ApiException.ServerError("Redirection Error: ${cause.message}")
                    is java.net.SocketTimeoutException -> ApiException.TimeoutError("Request Timed Out")
                    is java.net.UnknownHostException -> ApiException.NetworkError("No Internet Connection")
                    else -> ApiException.UnknownError("Unknown Error: ${cause.message}")
                }
            }
        }
    }
}
