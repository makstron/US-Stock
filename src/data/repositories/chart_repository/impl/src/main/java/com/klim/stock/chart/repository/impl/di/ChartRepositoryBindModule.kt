package com.klim.stock.chart.repository.impl.di

import com.klim.stock.chart.repository.impl.ChartRepositoryImpl
import com.klim.stock.chart.repository.api.ChartRepository
import dagger.Binds
import dagger.Module

@Module(includes = [ChartRepositoryBindModule::class, ChartDataSourcesModule::class])
class ChartRepositoryModule {

}

@Module
interface ChartRepositoryBindModule {

    @Binds
    fun bindChartRepository(repository: ChartRepositoryImpl): ChartRepository

}