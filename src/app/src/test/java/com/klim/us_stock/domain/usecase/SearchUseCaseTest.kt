package com.klim.us_stock.domain.usecase


import com.google.common.truth.Truth.assertThat
import com.klim.symbol_details_usecase_api.entity.SearchResultEntity
import com.klim.symbol_repository.SymbolRepositoryI
import com.klim.search_usecase.SearchUseCase
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SearchUseCaseTest {

    private lateinit var repository: com.klim.symbol_repository.SymbolRepositoryI
    private lateinit var useCase: com.klim.search_usecase.SearchUseCase
    private val request = "Tes"
    private val requestParams = com.klim.search_usecase.SearchUseCase.Params(request)
    private val response = listOf(
        com.klim.symbol_details_usecase_api.entity.SearchResultEntity("TSLA", "Tesla Inc"),
    )

    @Before
    fun setUp() {
        repository = mockk<com.klim.symbol_repository.SymbolRepositoryI>{
            coEvery { search(request) } returns response
        }
        useCase = com.klim.search_usecase.SearchUseCase(repository)
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