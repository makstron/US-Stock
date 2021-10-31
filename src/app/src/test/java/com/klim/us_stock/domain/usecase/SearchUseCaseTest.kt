package com.klim.us_stock.domain.usecase


import com.google.common.truth.Truth.assertThat
import com.klim.smth.domain.entity.SearchResultEntity
import com.klim.smth.domain.repository.SymbolRepositoryI
import com.klim.smth.domain.usecase.SearchUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SearchUseCaseTest {

    private lateinit var repository: SymbolRepositoryI
    private lateinit var useCase: SearchUseCase
    private val request = "Tes"
    private val requestParams = SearchUseCase.Params(request)
    private val response = listOf(
        SearchResultEntity("TSLA", "Tesla Inc"),
    )

    @Before
    fun setUp() {
        repository = mockk<SymbolRepositoryI>{
            coEvery { search(request) } returns response
        }
        useCase = SearchUseCase(repository)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun search() {
        runBlockingTest {
            val searchResults = useCase.search(requestParams)
            assertThat(searchResults).containsExactlyElementsIn(response)
        }
    }

}