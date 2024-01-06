package com.klim.stock.storage.impl.di

import android.content.Context
import com.klim.stock.dicore.Dependency
import com.klim.stock.dicore.DependencyKey
import com.klim.stock.storage.api.StorageKeys
import com.klim.stock.storage.impl.StorageKeysImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module(includes = [CreateStorageKeysModule::class])
interface StorageKeysModule {

    @Binds
    fun bindImplementationToInterface(implementation: StorageKeysImpl): StorageKeys

    @Binds
    fun bindProviderImplementationToInterface(implementation: StorageKeysProviderImpl): StorageKeysProvider

    @Binds
    @IntoMap
    @DependencyKey(StorageKeysProvider::class)
    fun bindProvider(impl: StorageKeysProvider): Dependency
}

@Module
class CreateStorageKeysModule {

    @Provides
    @Singleton
    fun provide(context: Context): StorageKeysImpl {
        return StorageKeysImpl(context)
    }

}



