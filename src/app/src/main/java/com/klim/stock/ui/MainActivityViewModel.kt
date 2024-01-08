package com.klim.stock.ui

import androidx.lifecycle.ViewModel
import com.klim.stock.analytics.analytics.Analytics
import javax.inject.Inject

class MainActivityViewModel
@Inject
constructor(
    private val analytics: Analytics
) : ViewModel() {

    init {
        analytics.logEventOpenApp()
    }

    override fun onCleared() {
        super.onCleared()
        analytics.logEventCloseApp()
    }
}