package com.klim.stock.navigation.di

import com.klim.stock.dicore.Dependency
import com.klim.stock.dicore.DependencyKey
import com.klim.stock.navigation.Navigation
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject

@Module
interface NavigationModule {

    @Binds
    fun bindProviderImplementationToInterface(implementation: NavigationProviderImpl): NavigationProvider

    @Binds
    @IntoMap
    @DependencyKey(NavigationProvider::class)
    fun bindProvider(impl: NavigationProvider): Dependency
}

interface NavigationProvider : Dependency {

    fun provide(): Navigation

}

class NavigationProviderImpl
@Inject
constructor(
    private val geocoder: Navigation
) : NavigationProvider {

    override fun provide(): Navigation = geocoder

}