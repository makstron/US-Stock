package com.klim.stock.presentation.main

import androidx.lifecycle.ViewModel
import com.klim.stock.presentation.main.ui.Actions
import com.klim.stock.presentation.main.ui.ScreenState
import kotlinx.coroutines.flow.Flow

abstract class MainActivityViewModel : ViewModel() {

    abstract val screenState: Flow<ScreenState>

    abstract fun setAction(action: Actions)

}