package com.klim.us_stock.ui.windows.search

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.klim.us_stock.CoroutineDispatchers
import com.klim.symbol_details_usecase_api.entity.SearchResultEntity
import com.klim.search_usecase.SearchUseCase
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


class SearchViewModel
@Inject constructor(
    private val searchUseCase: com.klim.search_usecase.SearchUseCase,
    private val dispatchers: CoroutineDispatchers,
    private val searchResultFormatter: SearchResultFormatter,
) : ViewModel() {

    private val _searchResults = MutableLiveData<List<SearchResultView>>()
    val searchResults: LiveData<List<SearchResultView>> = _searchResults

    val isSearching = ObservableBoolean(false)
    val isExistsResult = ObservableBoolean(true)

    var searchRequest = ""

    fun search(request: String) {
        searchRequest = request
        isSearching.set(true)
        viewModelScope.launch(dispatchers.Main) {
            val results = searchUseCase.search(com.klim.search_usecase.SearchUseCase.Params(searchRequest))
            val resultsViews: List<SearchResultView>
            withContext(dispatchers.IO) {
                resultsViews = prepareSearchResults(results)
            }
            isExistsResult.set(resultsViews.isNotEmpty())
            _searchResults.postValue(resultsViews)
            isSearching.set(false)
        }
    }

    private fun prepareSearchResults(results: List<com.klim.symbol_details_usecase_api.entity.SearchResultEntity>): List<SearchResultView> {
        val regex = Regex(searchRequest, RegexOption.IGNORE_CASE)
        return results.map { sre ->
            SearchResultView(
                ticker = sre.ticker,
                tickerStyled = searchResultFormatter.format(sre.ticker, regex),
                companyStyled = searchResultFormatter.format(sre.name, regex)
            )
        }
    }

}