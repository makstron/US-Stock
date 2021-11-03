package com.klim.symbol_details.di

import androidx.lifecycle.ViewModel
import com.klim.dep_in.view_model.ViewModelKey
import com.klim.symbol_details.presentation.SymbolDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelsBindModule {

    @Binds
    @IntoMap
    @ViewModelKey(SymbolDetailsViewModel::class)
    fun symbolDetailsViewModel(viewModel: SymbolDetailsViewModel): ViewModel

//    @Binds
//    @IntoMap
//    @ViewModelKey(SymbolDetailsViewModel::class)
//    fun symbolDetailsViewModel__as(viewModel: ViewModelFactory): ViewModelFactory_

//    @IntoMap
//    @Provides
//    @ViewModelKey(SymbolDetailsViewModel::class)
//    fun symbolDetailsViewModel(
//        application: Application,
//        symbolDetailsUseCase: SymbolDetailsUseCase,
//        geocoder: Geocoder,
//        analytics: AnalyticsI,
//        phoneNumberUtil: PhoneNumberUtil,
//    ): ViewModel {
//        println("!!!!!!!!!!!!!!!!!!!! symbolDetailsViewModel was called")
//        return SymbolDetailsViewModel(application, symbolDetailsUseCase, geocoder, analytics, phoneNumberUtil)
//    }

}