package com.klim.us_stock.ui.windows.search

import androidx.lifecycle.viewModelScope
import com.klim.us_stock.TestCoroutineDispatchers
import com.klim.us_stock.domain.entity.SearchResultEntity
import com.klim.us_stock.domain.usecase.SearchUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.*


@ObsoleteCoroutinesApi
class SearchViTewTModelTest {

    private val delayForSearchRequest = 10L

//    private val singleThread = newSingleThreadContext("Main thread")
    private val singleThread = Dispatchers.Unconfined

    private lateinit var useCase: SearchUseCase
    private lateinit var viewModel: SearchViewModel

    @Before
    fun setUp() {
        useCase = mock<SearchUseCase> {
            onBlocking { search(any()) } doReturn ArrayList<SearchResultEntity>()
        }
        val dispatchers = TestCoroutineDispatchers()
        viewModel = SearchViewModel(useCase, dispatchers)

        //reduce time waiting
        viewModel.DELAY_BEFORE_SEND_SEARCH_REQUEST = delayForSearchRequest
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `delay when typing`() {
        val viewModelSpy = Mockito.spy(viewModel)
        doNothing().`when`(viewModelSpy).search()
        runBlocking {
            println("!!!@ preSearch ${this}")
            viewModelSpy.preSearch("t")
            delay(delayForSearchRequest / 2)
            viewModelSpy.preSearch("ts")
            delay(delayForSearchRequest / 2)
            viewModelSpy.preSearch("tsl")
            delay(delayForSearchRequest + 10)
        }
        Mockito.verify(viewModelSpy, times(1)).search()
    }

    @Test
    fun `TEST delay when typing work correctly`() {
        val viewModelSpy = Mockito.spy(viewModel)
        doNothing().`when`(viewModelSpy).search()

        runBlocking(singleThread) {
            viewModelSpy.scope = this

            println("!!!@ preSearch ${this}")
//            viewModelSpy.preSearch__test("t")

//            viewModelSpy.job?.join()

            println("!!!@ VERIFY")
            Mockito.verify(viewModelSpy, times(1)).search()
            println("!!!@ END BLOCKING")
        }
        println("!!!@ END TEST")
    }

    @Test
    fun search_() {
    }
}