package com.klim.dep_in.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
open class ViewModelFactory
@Inject
constructor(
    var viewModels: MutableMap<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelFactoryTest() {

    public val mutableMap = HashMap<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>()

    init {
        println("!!!!!!!!!!!!!!!!!!!! ViewModelProvider.Factory was created")
        println(viewModels)
        mutableMap.putAll(viewModels)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModelProvider = mutableMap[modelClass]
            ?: throw IllegalArgumentException("model class $modelClass not found")
        return viewModelProvider.get() as T
    }

}

abstract class ViewModelFactoryTest : ViewModelProvider.Factory {


}