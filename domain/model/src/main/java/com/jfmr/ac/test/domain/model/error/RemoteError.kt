package com.jfmr.ac.test.domain.model.error

sealed class RemoteError : DomainError {
    data object Connectivity : RemoteError()
    data class Server(val message: Int) : RemoteError()
    data class Unknown(val message: String = "") : RemoteError()
}
