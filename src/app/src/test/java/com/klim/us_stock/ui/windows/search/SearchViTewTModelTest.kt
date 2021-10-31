package com.klim.us_stock.ui.windows.search

import com.google.common.truth.Truth.assertThat
import com.klim.smth.domain.entity.SearchResultEntity
import com.klim.smth.domain.usecase.SearchUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.klim.us_stock.TestCoroutineDispatchers
import io.mockk.every
import kotlinx.coroutines.test.*

import org.junit.Rule
import kotlin.collections.ArrayList

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class SearchViTewTModelTest {

    private val testDispatcher = TestCoroutineDispatcher()
    private val dispatchers = TestCoroutineDispatchers(testDispatcher)

    private lateinit var useCase: SearchUseCase
    private lateinit var searchResultFormatter: SearchResultFormatter
    private lateinit var viewModel: SearchViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        useCase = mockk<SearchUseCase> {}
        searchResultFormatter = mockk<SearchResultFormatter> {
            every { format(any(), any()) } returns ""
        }

        viewModel = SearchViewModel(useCase, dispatchers, searchResultFormatter)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `test searching state`() {
        //setup
        coEvery { useCase.search(any()) } returns ArrayList<SearchResultEntity>()

        //test
        testDispatcher.runBlockingTest {
            pauseDispatcher()
            viewModel.search("")
            assertThat(viewModel.isSearching.get()).isTrue()
            runCurrent()
            assertThat(viewModel.isSearching.get()).isFalse()
        }
    }

    @Test
    fun `test search results exist`() {
        //setup
        coEvery { useCase.search(any()) } returns listOf(SearchResultEntity("TESS", "Tessco Technologies Inc"))

        //test
        runBlockingTest {
            viewModel.search("Tes")
            assertThat(viewModel.isExistsResult.get()).isTrue()
        }
    }

    @Test
    fun `test search results not exist`() {
        //setup
        coEvery { useCase.search(any()) } returns ArrayList<SearchResultEntity>()

        //test
        runBlockingTest {
            viewModel.search("Tes")
            assertThat(viewModel.isExistsResult.get()).isFalse()
        }
    }

}