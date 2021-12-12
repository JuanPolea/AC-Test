package com.jfmr.ac.test.domain.model

import arrow.core.Either

typealias DomainResult <T> = Either<DomainError, T>