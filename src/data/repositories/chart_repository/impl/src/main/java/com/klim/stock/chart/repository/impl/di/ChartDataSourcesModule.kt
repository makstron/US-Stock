package com.klim.stock.chart.repository.impl.di

import com.klim.stock.chart.repository.impl.data_source.ChartDataSource
import com.klim.stock.chart.repository.impl.data_source.remote.ChartRemoteDataSource
import com.klim.stock.network.api.ChartApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ChartDataSourcesModule {

    @Provides
    @Singleton
    fun provideChartRemoteDataSource(api: ChartApi): ChartDataSource {
        return ChartRemoteDataSource(api)
    }
}