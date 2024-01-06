package com.klim.stock.storage.impl.di

import com.klim.stock.dependencyinjection.ApplicationContextProvider
import com.klim.stock.storage.api.StorageKeyProvider
import com.klim.stock.storage.api.StorageKeys
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [StorageKeysModule::class],
    dependencies = [ApplicationContextProvider::class]
)
public interface StorageKeysComponent : StorageKeyProvider {

    override fun getStorageKeys(): StorageKeys

}