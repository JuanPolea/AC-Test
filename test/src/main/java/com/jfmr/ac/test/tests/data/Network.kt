package com.jfmr.ac.test.tests.data

object Network {
    const val NETWORK_CODE_UNAUTHORIZED = 401
    const val NETWORK_CODE_NOT_FOUND = 404
    const val NETWORK_CODE_SERVER_ERROR = 500

    fun <T> getResponseError(code: Int = NETWORK_CODE_UNAUTHORIZED): Result<T> =
        Result.failure(Throwable(code.toString()))

    fun <T> getRemoteError(code: Int = NETWORK_CODE_UNAUTHORIZED): Result<T> =
        Result.failure(Throwable(code.toString()))
}
