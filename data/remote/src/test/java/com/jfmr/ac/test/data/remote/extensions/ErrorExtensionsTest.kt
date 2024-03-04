package com.jfmr.ac.test.data.remote.extensions

import arrow.core.Either
import com.jfmr.ac.test.domain.model.error.DomainError
import com.jfmr.ac.test.domain.model.error.RemoteError
import org.junit.Test
import java.io.IOException
import kotlin.test.assertEquals

class ErrorExtensionsTest {

    private val otherException = IllegalThreadStateException()
    private val unknown = RemoteError.Unknown()
    private val connectivity = RemoteError.Connectivity

    @Test
    fun toError() {
        assertEquals(connectivity, IOException().toError())
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
