package com.jfmr.ac.test.data.remote.qualifier

import javax.inject.Qualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DispatcherDefault

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DispatcherIO

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class QCharactersDataSource

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class QEpisodesDataSource

