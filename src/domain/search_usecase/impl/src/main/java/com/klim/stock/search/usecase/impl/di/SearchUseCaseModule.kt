package com.klim.stock.search.usecase.impl.di

import com.klim.stock.dicore.Dependency
import com.klim.stock.dicore.DependencyKey
import com.klim.stock.searchusecase.api.SearchUseCase
import com.klim.stock.searchusecase.api.di.SearchUseCaseProvider
import com.klim.stock.search.usecase.impl.SearchUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject

@Module
interface SearchUseCaseModule {

    @Binds
    fun bindImplementationToInterface(impl: SearchUseCaseImpl): SearchUseCase

    @Binds
    fun bindProviderImplementationToInterface(impl: SearchUseCaseProviderImpl): SearchUseCaseProvider

    @Binds
    @IntoMap
    @DependencyKey(SearchUseCaseProvider::class)
    fun bindProvider(impl: SearchUseCaseProvider): Dependency

}

class SearchUseCaseProviderImpl
@Inject
constructor(
    override val symbolDetailsUseCase: SearchUseCase
) : SearchUseCaseProvider