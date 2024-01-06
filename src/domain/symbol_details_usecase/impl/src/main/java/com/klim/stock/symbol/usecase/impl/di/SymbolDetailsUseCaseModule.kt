package com.klim.stock.symbol.usecase.impl.di

import com.klim.stock.dicore.Dependency
import com.klim.stock.dicore.DependencyKey
import com.klim.stock.symbol.api.SymbolDetailsUseCase
import com.klim.stock.symbol.api.di.SymbolDetailsUseCaseProvider
import com.klim.stock.symbol.usecase.impl.SymbolDetailsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject

@Module
interface SymbolDetailsUseCaseModule {

    @Binds
    fun bindImplementationToInterface(impl: SymbolDetailsUseCaseImpl): SymbolDetailsUseCase

     @Binds
    fun bindProviderImplementationToInterface(impl: SymbolDetailsUseCaseProviderImpl): SymbolDetailsUseCaseProvider

    @Binds
    @IntoMap
    @DependencyKey(SymbolDetailsUseCaseProvider::class)
    fun bindProvider(impl: SymbolDetailsUseCaseProvider): Dependency

}

class SymbolDetailsUseCaseProviderImpl
@Inject
constructor(
    override val useCase: SymbolDetailsUseCase
) : SymbolDetailsUseCaseProvider