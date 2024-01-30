package com.klim.stock.favorited.ui.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.klim.stock.favorited.ui.Intents
import com.klim.stock.favorited.ui.ScreenState

abstract class SymbolFavoritedViewModel : ViewModel() {

    abstract val screenState: LiveData<ScreenState>
    open val navigation: LiveData<NavigationTarget>? = null
    abstract fun load()
    abstract fun sendIntent(intent: Intents)

}