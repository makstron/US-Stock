package com.klim.stock.dicore

import dagger.MapKey
import kotlin.reflect.KClass

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class DependencyKey(val value: KClass<out Dependency>)

interface Dependency

typealias DependenciesMap = @JvmSuppressWildcards MutableMap<Class<out Dependency>, Dependency>

interface DependencyContainer {
    val dependenciesMap: DependenciesMap
}