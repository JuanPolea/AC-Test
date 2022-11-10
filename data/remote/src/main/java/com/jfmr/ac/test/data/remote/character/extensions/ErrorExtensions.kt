package com.jfmr.ac.test.data.remote.character.extensions

import arrow.core.left
import arrow.core.right
import com.jfmr.ac.test.domain.model.character.DomainResult
import com.jfmr.ac.test.domain.model.error.RemoteError
import retrofit2.HttpException
import java.io.IOException

fun Exception.toError(): RemoteError =
    when (this) {
        is IOException -> RemoteError.Connectivity
        is HttpException -> RemoteError.Server(code())
        else -> RemoteError.Unknown(message.orEmpty())
    }

inline fun <T> tryCall(action: () -> T): DomainResult<T> =
    try {
        action.invoke().right()
    } catch (e: Exception) {
        e.toError().left()
    }
