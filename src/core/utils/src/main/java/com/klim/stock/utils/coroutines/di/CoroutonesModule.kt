package com.klim.stock.utils.coroutines.di

import com.klim.stock.dicore.Dependency
import com.klim.stock.dicore.DependencyKey
import com.klim.stock.utils.coroutines.CoroutineDispatchers
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Singleton

@Module(includes = [CreateCoroutineModule::class])
interface CoroutineModule {

    @Binds
    fun bindProviderImplementationToInterface(implementation: CoroutineProviderImpl): CoroutineProvider

    @Binds
    @IntoMap
    @DependencyKey(CoroutineProvider::class)
    fun bindProvider(impl: CoroutineProvider): Dependency
}

interface CoroutineProvider : Dependency {

    fun provide(): CoroutineDispatchers

}

@Module
class CreateCoroutineModule {

    @Provides
    @Singleton
    fun provideCoroutine(): CoroutineDispatchers {
        return CoroutineDispatchers()
    }

}

class CoroutineProviderImpl
@Inject
constructor(
    private val coroutineDispatchers: CoroutineDispatchers
) : CoroutineProvider {

    override fun provide(): CoroutineDispatchers = coroutineDispatchers

}