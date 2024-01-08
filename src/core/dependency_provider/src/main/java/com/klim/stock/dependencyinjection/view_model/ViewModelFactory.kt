package com.klim.stock.dependencyinjection.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

open class ViewModelFactory
@Inject
constructor(
    var viewModels: MutableMap<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {

    val mutableMap = HashMap<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>()

    init {
        mutableMap.putAll(viewModels)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModelProvider = mutableMap[modelClass]
            ?: throw IllegalArgumentException("model class $modelClass not found")
        return viewModelProvider.get() as T
    }

}