package com.klim.stock.info.ui.di

import androidx.lifecycle.ViewModel
import com.klim.stock.dependencyinjection.view_model.ViewModelFactory
import com.klim.stock.dependencyinjection.view_model.ViewModelFactoryTemp
import com.klim.stock.dependencyinjection.view_model.ViewModelKey
import com.klim.stock.info.ui.presentation.InfoViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Provider


@Module(includes = [ViewModelsFactoryModule::class])
interface ViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(InfoViewModel::class)
    fun bindViewModel(viewModel: InfoViewModel): ViewModel

}

@Module
class ViewModelsFactoryModule {

    @Provides
    fun provideViewModelFactory(
        factory: ViewModelFactory,
        viewModels: MutableMap<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
    ): ViewModelFactoryTemp {
        factory.mutableMap.putAll(viewModels)
        return factory
    }
}