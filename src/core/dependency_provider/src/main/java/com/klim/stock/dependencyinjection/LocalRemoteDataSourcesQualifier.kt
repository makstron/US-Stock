package com.klim.stock.dependencyinjection

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class LocalDataSource

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RemoteDataSource