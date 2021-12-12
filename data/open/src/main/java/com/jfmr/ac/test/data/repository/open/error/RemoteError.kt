package com.jfmr.ac.test.data.repository.open.error

import arrow.core.left
import arrow.core.right
import com.jfmr.ac.test.domain.model.DomainError
import com.jfmr.ac.test.domain.model.DomainResult
import retrofit2.HttpException
import java.io.IOException

sealed class RemoteError : DomainError {
    object Connectivity : RemoteError()
    data class Server(val message: Int) : RemoteError()
    data class Unknown(val message: String) : RemoteError()
}

fun Exception.toError(): RemoteError =
    when (this) {
        is IOException -> RemoteError.Connectivity
        is HttpException -> RemoteError.Server(code())
        else -> RemoteError.Unknown(message ?: "")
    }

inline fun <T> tryCall(action: () -> T): DomainResult<T> =
    try {
        action.invoke().right()
    } catch (e: Exception) {
        e.toError().left()
    }