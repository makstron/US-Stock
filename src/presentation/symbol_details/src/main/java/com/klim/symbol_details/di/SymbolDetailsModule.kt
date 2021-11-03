package com.klim.symbol_details.di

import androidx.lifecycle.ViewModel
import com.klim.dep_in.view_model.ViewModelFactory
import com.klim.dep_in.view_model.ViewModelFactoryTest
import dagger.Module
import dagger.Provides
import javax.inject.Provider

@Module
class SymbolDetailsModule {

    @Provides
    fun provideViewModelFactory(
        factory: ViewModelFactory,
        viewModels: MutableMap<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
    ): ViewModelFactoryTest {
        factory.mutableMap.putAll(viewModels)
        return factory
    }
}