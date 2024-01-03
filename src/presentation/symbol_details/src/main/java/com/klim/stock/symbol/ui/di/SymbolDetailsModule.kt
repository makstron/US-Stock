package com.klim.stock.symbol.ui.di

import androidx.lifecycle.ViewModel
import com.klim.stock.dependencyinjection.view_model.ViewModelFactory
import com.klim.stock.dependencyinjection.view_model.ViewModelFactoryTest
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