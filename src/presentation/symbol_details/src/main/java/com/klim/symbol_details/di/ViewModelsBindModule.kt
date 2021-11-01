package com.klim.symbol_details.di

import android.app.Application
import android.location.Geocoder
import androidx.lifecycle.ViewModel
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.klim.analytics.AnalyticsI
import com.klim.dep_in.view_model.ViewModelKey
import com.klim.smth.domain.usecase.SymbolDetailsUseCase
import com.klim.symbol_details.presentation.SymbolDetailsViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class ViewModelsBindModule {

//    @Binds
//    @IntoMap
//    @ViewModelKey(SymbolDetailsViewModel::class)
//    fun symbolDetailsViewModel(viewModel: SymbolDetailsViewModel): ViewModel

    @IntoMap
    @Provides
    @ViewModelKey(SymbolDetailsViewModel::class)
    fun symbolDetailsViewModel(
        application: Application,
        symbolDetailsUseCase: SymbolDetailsUseCase,
        geocoder: Geocoder,
        analytics: AnalyticsI,
        phoneNumberUtil: PhoneNumberUtil,
    ): ViewModel {
        println("!!!!!!!!!!!!!!!!!!!! symbolDetailsViewModel was called")
        return SymbolDetailsViewModel(application, symbolDetailsUseCase, geocoder, analytics, phoneNumberUtil)
    }

}