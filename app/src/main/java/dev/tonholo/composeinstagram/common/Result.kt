package dev.tonholo.composeinstagram.common

sealed interface Result<T> {
    data class Success<T>(val data: T) : Result<T>
    data class Loading<T>(val data: T? = null) : Result<T>
    data class Error<T>(
        val message: String,
        val throwable: Throwable? = null,
        val data: T? = null,
    ) : Result<T>
}
