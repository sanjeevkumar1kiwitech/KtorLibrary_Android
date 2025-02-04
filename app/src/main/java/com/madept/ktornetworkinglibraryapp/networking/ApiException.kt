package com.madept.ktornetworkinglibraryapp.networking

sealed class ApiException(message: String) : Exception(message) {
    class NetworkError(message: String) : ApiException(message)
    class TimeoutError(message: String) : ApiException(message)
    class ServerError(message: String) : ApiException(message)
    class UnknownError(message: String) : ApiException(message)
}