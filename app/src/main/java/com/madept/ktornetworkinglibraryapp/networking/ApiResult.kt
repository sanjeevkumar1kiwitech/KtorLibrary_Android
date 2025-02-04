package com.madept.ktornetworkinglibraryapp.networking

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val message: String) : ApiResult<Nothing>()
}


suspend fun <T> safeApiCall(apiCall: suspend () -> T): ApiResult<T> {
    return try {
        ApiResult.Success(apiCall()) // If successful, return the result
    } catch (e: ApiException) {
        ApiResult.Error(e.message ?: "Unknown error") // Handle known API exceptions
    } catch (e: Exception) {
        ApiResult.Error("Unexpected error: ${e.message}") // Handle unknown exceptions
    }
}
