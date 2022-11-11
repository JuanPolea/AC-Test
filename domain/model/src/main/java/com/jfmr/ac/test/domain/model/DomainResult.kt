package com.jfmr.ac.test.domain.model

import arrow.core.Either
import com.jfmr.ac.test.domain.model.error.DomainError

typealias DomainResult <T> = Either<DomainError, T>
