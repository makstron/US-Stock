package com.klim.stock.storage.impl

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.klim.stock.storage.api.StorageKeys
import com.klim.stock.storage.api.StorageKeys.Companion.Key as Keys


class StorageKeysImpl(
    val context: Context,
) : StorageKeys {

    private val STORAGE_NAME = "keyValueSharedPrefsStorage"

    private var masterKey: MasterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private var sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        STORAGE_NAME,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    private var inMemoryStorage = HashMap<Keys, Any?>()

    //region values

    override fun getCookies(): String? {
        return getValueString(Keys.COOKIES)
    }

    override fun setCookies(value: String?) {
        setValueString(Keys.COOKIES, value)
    }

    override fun getCrumb(): String? {
        return getValueString(Keys.CRUMB)
    }

    override fun setCrumb(value: String?) {
        setValueString(Keys.CRUMB, value)
    }

    //endregion

    private fun setValueString(key: Keys, value: String?) {
        inMemoryStorage[key] = value
        sharedPreferences.edit(commit = true) {
            putString(key.key, value)
        }
    }

    private fun getValueString(key: Keys): String? {
        var value = inMemoryStorage[key]
        Log.d("StorageKeys: ", "${key.key} from " + (if (value == null) "disk" else "memory")) //TODO: now replace logs
        if (value == null) {
            value = getString(key)
            inMemoryStorage[key] = value
        }
        return value as String?
    }

    private fun getString(key: Keys): String? {
        return sharedPreferences.getString(key.key, key.defValue)
    }
}