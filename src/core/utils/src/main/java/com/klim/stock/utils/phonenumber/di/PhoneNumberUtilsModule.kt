package com.klim.stock.utils.phonenumber.di

import com.klim.stock.dicore.Dependency
import com.klim.stock.dicore.DependencyKey
import com.klim.stock.utils.phonenumber.PhoneNumberUtilsImpl
import com.klim.stock.utils.phonenumber.api.PhoneNumberUtils
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Singleton

@Module(includes = [CreatePhoneNumberUtilsModule::class])
interface PhoneNumberUtilsModule {

    @Binds
    fun bindImplementationToInterface(implementation: PhoneNumberUtilsImpl): PhoneNumberUtils

    @Binds
    fun bindProviderImplementationToInterface(implementation: PhoneNumberUtilsProviderImpl): PhoneNumberUtilsProvider

    @Binds
    @IntoMap
    @DependencyKey(PhoneNumberUtilsProvider::class)
    fun bindProvider(impl: PhoneNumberUtilsProvider): Dependency
}

interface PhoneNumberUtilsProvider : Dependency {

    fun provide(): PhoneNumberUtils

}

@Module
class CreatePhoneNumberUtilsModule {

    @Provides
    @Singleton
    fun providePhoneNumberUtils(): PhoneNumberUtilsImpl {
        return PhoneNumberUtilsImpl()
    }

}

class PhoneNumberUtilsProviderImpl
@Inject
constructor(
    private val phoneNumberUtils: PhoneNumberUtils
) : PhoneNumberUtilsProvider {

    override fun provide(): PhoneNumberUtils = phoneNumberUtils

}