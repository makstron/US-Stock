package com.klim.us_stock.ui.view_extensions

import android.text.Editable
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TextEndWatcherTest {

    private val delay = 10L
    private lateinit var text: Editable
    private lateinit var finalText: Editable

    private var calledTimes = 0
    private var searchedValue: CharSequence? = ""

    private var textWatcher = object : TextEndWatcher(delay) {
        override fun endTextChanged(text: CharSequence?) {
            calledTimes++
            searchedValue = text
        }
    }

    @Before
    fun setUp() {
        calledTimes = 0
        searchedValue = ""

        text = Editable.Factory.getInstance().newEditable("")
        finalText = Editable.Factory.getInstance().newEditable("Tes")
    }

    @After
    fun tearDown() {
    }

    @Test
    fun only_one_request_after_many_tap() {
        runBlocking {
            for (i in 0..5) {
                tapOnKeyboard(text, delay / 2)
            }
            delay(delay)
        }
        assertThat(calledTimes).isEqualTo(1)
    }

    @Test
    fun send_last_value_after_taps() {
        runBlocking {
            for (i in 0..5) {
                tapOnKeyboard(text, delay / 2)
            }
            tapOnKeyboard(finalText, delay + 5)
        }
        assertThat(searchedValue).isEqualTo(finalText)
    }

    private suspend fun tapOnKeyboard(text: Editable?, delay: Long) {
        textWatcher.afterTextChanged(text)
        delay(delay)
    }

}