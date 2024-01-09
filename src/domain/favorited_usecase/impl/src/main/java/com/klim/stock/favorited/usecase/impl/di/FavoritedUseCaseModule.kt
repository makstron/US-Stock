package com.klim.stock.favorited.usecase.impl.di

import com.klim.stock.dicore.Dependency
import com.klim.stock.dicore.DependencyKey
import com.klim.stock.favorited.usecase.api.FavoritedUseCase
import com.klim.stock.favorited.usecase.api.di.FavoritedUseCaseProvider
import com.klim.stock.favorited.usecase.impl.FavoritedUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject

@Module
interface FavoritedUseCaseModule {

    @Binds
    fun bindImplementationToInterface(impl: FavoritedUseCaseImpl): FavoritedUseCase

     @Binds
    fun bindProviderImplementationToInterface(impl: FavoritedUseCaseProviderImpl): FavoritedUseCaseProvider

    @Binds
    @IntoMap
    @DependencyKey(FavoritedUseCaseProvider::class)
    fun bindProvider(impl: FavoritedUseCaseProvider): Dependency

}

class FavoritedUseCaseProviderImpl
@Inject
constructor(
    override val useCase: FavoritedUseCase
) : FavoritedUseCaseProvider