package com.jfmr.ac.test.tests.data

import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

object Network {
    const val NETWORK_CODE_BAD_REQUEST = 400
    const val NETWORK_CODE_UNAUTHORIZED = 401
    const val NETWORK_CODE_NOT_FOUND = 404
    const val NETWORK_CODE_SERVER_ERROR = 500

    fun <T> getResponseError(code: Int = NETWORK_CODE_UNAUTHORIZED): Response<T> =
        Response.error(code, "Episodes error".toResponseBody())
}
