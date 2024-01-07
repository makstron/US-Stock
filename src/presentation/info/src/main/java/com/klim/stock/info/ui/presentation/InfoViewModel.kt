package com.klim.stock.info.ui.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class InfoViewModel
@Inject
constructor() : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is a sample project.\n" +
                "If you have any questions, you can write me somewhere =)"
    }
    val text: LiveData<String> = _text
}