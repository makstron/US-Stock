package com.klim.stock.presentation.main

import com.klim.stock.analytics.analytics.Analytics
import com.klim.stock.presentation.main.ui.Actions
import com.klim.stock.presentation.main.ui.ScreenState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class MainActivityViewModelImpl
@Inject
constructor(
    private val analytics: Analytics
) : MainActivityViewModel() {

    private val _screenState = MutableStateFlow<ScreenState>(ScreenState())
    override val screenState: Flow<ScreenState> = _screenState


    init {
        analytics.logEventOpenApp()
    }

    override fun setAction(action: Actions) {
        when (action) {
            is Actions.MenuButton -> {
                val currentState = _screenState.value.menuState
                _screenState.tryEmit(
                    _screenState.value.copy(menuState = !currentState)
                )
            }

            is Actions.SearchButton -> {
                println("Search")
            }

            is Actions.Navigation -> {
                _screenState.tryEmit(
                    _screenState.value.copy(
                        internalContainerContent = action.destination,
                        menuState = false,
                    )
                )
            }

            is Actions.ExitButton -> {
                println("Exit")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        analytics.logEventCloseApp()
    }
}