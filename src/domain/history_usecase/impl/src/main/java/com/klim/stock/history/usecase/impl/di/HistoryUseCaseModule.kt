package com.klim.stock.history.usecase.impl.di

import com.klim.stock.dicore.Dependency
import com.klim.stock.dicore.DependencyKey
import com.klim.stock.history.usecase.api.HistoryUseCase
import com.klim.stock.history.usecase.api.di.HistoryUseCaseProvider
import com.klim.stock.history.usecase.impl.HistoryUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject

@Module
interface HistoryUseCaseModule {

    @Binds
    fun bindImplementationToInterface(impl: HistoryUseCaseImpl): HistoryUseCase

     @Binds
    fun bindProviderImplementationToInterface(impl: HistoryUseCaseProviderImpl): HistoryUseCaseProvider

    @Binds
    @IntoMap
    @DependencyKey(HistoryUseCaseProvider::class)
    fun bindProvider(impl: HistoryUseCaseProvider): Dependency

}

class HistoryUseCaseProviderImpl
@Inject
constructor(
    override val useCase: HistoryUseCase
) : HistoryUseCaseProvider