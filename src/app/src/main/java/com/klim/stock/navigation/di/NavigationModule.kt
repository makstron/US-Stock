package com.klim.stock.navigation.di

import com.klim.stock.navigation.Navigation
import com.klim.stock.navigation.NavigationImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CreateNavigationModule {

    @Provides
    @Singleton
    fun provideNavigation(): Navigation {
        return NavigationImpl()
    }

}