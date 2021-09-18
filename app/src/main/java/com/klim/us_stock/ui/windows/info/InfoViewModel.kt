package com.klim.us_stock.ui.windows.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class InfoViewModel
@Inject constructor(): ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Info"
    }
    val text: LiveData<String> = _text
}