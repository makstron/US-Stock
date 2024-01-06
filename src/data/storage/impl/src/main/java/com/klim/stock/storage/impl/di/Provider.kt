package com.klim.stock.storage.impl.di

import com.klim.stock.dicore.Dependency
import com.klim.stock.storage.api.StorageKeys
import javax.inject.Inject

interface StorageKeysProvider : Dependency {
    fun provide(): StorageKeys
}

class StorageKeysProviderImpl
@Inject
constructor(
    private val storageKeys: StorageKeys
) : StorageKeysProvider {

    override fun provide(): StorageKeys = storageKeys

}