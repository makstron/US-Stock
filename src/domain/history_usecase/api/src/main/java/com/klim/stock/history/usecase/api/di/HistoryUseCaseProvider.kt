package com.klim.stock.history.usecase.api.di

import com.klim.stock.dicore.Dependency
import com.klim.stock.history.usecase.api.HistoryUseCase

interface HistoryUseCaseProvider : Dependency {
    val useCase: HistoryUseCase
}