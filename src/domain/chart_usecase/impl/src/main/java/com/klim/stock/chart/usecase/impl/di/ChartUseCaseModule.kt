package com.klim.stock.chart.usecase.impl.di

import com.klim.stock.dicore.Dependency
import com.klim.stock.dicore.DependencyKey
import com.klim.stock.chart.usecase.api.ChartUseCase
import com.klim.stock.chart.usecase.api.di.ChartUseCaseProvider
import com.klim.stock.chart.usecase.impl.ChartUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject

@Module
interface ChartUseCaseModule {

    @Binds
    fun bindImplementationToInterface(impl: ChartUseCaseImpl): ChartUseCase

     @Binds
    fun bindProviderImplementationToInterface(impl: ChartUseCaseProviderImpl): ChartUseCaseProvider

    @Binds
    @IntoMap
    @DependencyKey(ChartUseCaseProvider::class)
    fun bindProvider(impl: ChartUseCaseProvider): Dependency

}

class ChartUseCaseProviderImpl
@Inject
constructor(
    override val useCase: ChartUseCase
) : ChartUseCaseProvider