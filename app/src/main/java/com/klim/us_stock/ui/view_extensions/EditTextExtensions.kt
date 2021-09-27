package com.klim.us_stock.ui.view_extensions

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import kotlinx.coroutines.*

/**
 * Will call listener after time when a user stopped write text
 */

fun EditText.addOnTextChangeEndListener(delay: Long = 700L, action: (text: CharSequence?) -> Unit) {
    this.addTextChangedListener(object : TextEndWatcher(delay) {
        override fun endTextChanged(text: CharSequence?) {
            action.invoke(text)
        }
    })
}

fun EditText.addOnTextChangeEndListener(textEndWatcher: TextEndWatcher) {
    this.addTextChangedListener(textEndWatcher)
}

abstract class TextEndWatcher(private val delay: Long) : TextWatcher {

    private var job: Job? = null
    private var string: CharSequence? = null

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
        string = s
        checkIfEnded()
    }

    private fun checkIfEnded() {
        job?.cancel()
        job = GlobalScope.launch(Dispatchers.Main) {
            delay(delay)
            job = null
            endTextChanged(string)
        }
    }

    abstract fun endTextChanged(text: CharSequence?)
}