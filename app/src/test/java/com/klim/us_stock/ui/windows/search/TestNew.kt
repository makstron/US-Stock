package com.klim.us_stock.ui.windows.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.klim.us_stock.domain.entity.SearchResultEntity
import com.klim.us_stock.domain.usecase.SearchUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.*

class TestNew {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private lateinit var viewModel: TestViewModel
    private lateinit var viewModelSpy: TestViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)

        viewModel = TestViewModel()
        viewModelSpy = Mockito.spy(viewModel)
        doNothing().`when`(viewModelSpy).search()

        viewModelSpy.dispatcherMain = Dispatchers.Unconfined
        viewModelSpy.dispatcherIO = Dispatchers.Unconfined
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

//    @Test
//    fun testSomeUI() = runBlocking {
//        launch(Dispatchers.Main) {  // Will be launched in the mainThreadSurrogate dispatcher
//            // ...
//        }
//    }

    @Test
//    @ExperimentalCoroutinesApi
    fun `test launch or async`() = runBlockingTest {
        viewModelSpy.preSearch("")
        println("!!!@ testEnd")
    }

    @Test
    fun addition_isCorrect() {
        Assert.assertEquals(4, 2 + 2)
    }
}


class TestViewModel constructor() : ViewModel() {

    var dispatcherMain: CoroutineDispatcher = Dispatchers.Main
    var dispatcherIO: CoroutineDispatcher = Dispatchers.IO

    fun preSearch(request: String) {
        println("!!!@ preSearch()")
        viewModelScope.launch(dispatcherIO) {
            println("!!!@ launch()")
//            delay(1000)
            search()
            println("!!!@ result")
        }
    }

    fun search() {
        println("!!!@ search()")
    }
}

