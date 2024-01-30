package com.klim.stock.chart.usecase.api.di

import com.klim.stock.dicore.Dependency
import com.klim.stock.chart.usecase.api.ChartUseCase

interface ChartUseCaseProvider : Dependency {
    val useCase: ChartUseCase
}