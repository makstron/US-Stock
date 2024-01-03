package com.klim.stock.ui.windows.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class InfoViewModel
@Inject constructor(): ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is a sample project.\n" +
                "If you see some design differences or have other questions, you can ask me."
    }
    val text: LiveData<String> = _text
}