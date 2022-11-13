package com.jfmr.ac.test.data.remote.extensions

import arrow.core.Either
import com.jfmr.ac.test.domain.model.error.DomainError
import com.jfmr.ac.test.domain.model.error.RemoteError
import com.jfmr.ac.test.tests.data.Network.NETWORK_CODE_NOT_FOUND
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import kotlin.test.assertEquals

class ErrorExtensionsTest {

    private val response: Response<RemoteError> = Response.error(NETWORK_CODE_NOT_FOUND, "Error retrieving list".toResponseBody())
    private val httpError = HttpException(response)
    private val otherException = IllegalThreadStateException()
    private val unknown = RemoteError.Unknown()
    private val serverError = RemoteError.Server(NETWORK_CODE_NOT_FOUND)
    private val connectivity = RemoteError.Connectivity

    @Test
    fun toError() {
        assertEquals(connectivity, IOException().toError())
        assertEquals(serverError, httpError.toError())
        assertEquals(unknown, otherException.toError())
    }

    @Test
    fun tryCall() {

        val actual: Either<DomainError, IOException> = tryCall {
            IOException()
        }
        actual.fold(
            {
                assertEquals(connectivity, it)
            }, {

            }
        )

        val actualHttpError = tryCall {
            httpError
        }
        actualHttpError.fold(
            {
                assertEquals(RemoteError.Server(NETWORK_CODE_NOT_FOUND), it)
            }, {

            }
        )

        val actualUnknownError = tryCall {
            otherException
        }
        actualUnknownError.fold(
            {
                assertEquals(unknown, it)
            }, {

            }
        )
    }
}
