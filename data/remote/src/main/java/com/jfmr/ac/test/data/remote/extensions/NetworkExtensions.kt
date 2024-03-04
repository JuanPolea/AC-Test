package com.jfmr.ac.test.data.remote.extensions

import arrow.core.left
import arrow.core.right
import com.jfmr.ac.test.domain.model.DomainResult
import com.jfmr.ac.test.domain.model.error.RemoteError
import java.io.IOException

fun Throwable.toError(): RemoteError =
    when (this) {
        is IOException -> RemoteError.Connectivity
        else -> RemoteError.Unknown(message = message.orEmpty())
    }

inline fun <T> tryCall(action: () -> T): DomainResult<T> =
    try {
        action.invoke().right()
    } catch (e: Exception) {
        e.toError().left()
    }
