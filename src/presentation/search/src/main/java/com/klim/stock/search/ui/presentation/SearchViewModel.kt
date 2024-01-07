package com.klim.stock.search.ui.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.klim.stock.searchusecase.api.SearchUseCase
import com.klim.stock.searchusecase.api.entity.SearchResultEntity
import com.klim.stock.utils.coroutines.CoroutineDispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


class SearchViewModel
@Inject constructor(
    private val searchUseCase: SearchUseCase,
    private val dispatchers: CoroutineDispatchers,
    private val searchResultFormatter: SearchResultFormatter,
) : ViewModel() {

    private val _searchResults = MutableLiveData<List<SearchResultView>>()
    val searchResults: LiveData<List<SearchResultView>> = _searchResults

    private val _isSearching = MutableLiveData<Boolean>(false)
    val isSearching: LiveData<Boolean> = _isSearching

    private val _isExistsResult = MutableLiveData<Boolean>(true)
    val isExistsResult: LiveData<Boolean> = _isExistsResult

    var searchRequest = ""

    fun search(request: String) {
        searchRequest = request
        _isSearching.postValue(true)
        viewModelScope.launch(dispatchers.Main) {
            val results = searchUseCase.search(SearchUseCase.RequestParams(searchRequest))
            val resultsViews: List<SearchResultView>
            withContext(dispatchers.IO) {
                resultsViews = prepareSearchResults(results)
            }
            _isExistsResult.postValue(resultsViews.isNotEmpty())
            _searchResults.postValue(resultsViews)
            _isSearching.postValue(false)
        }
    }

    private fun prepareSearchResults(results: List<SearchResultEntity>): List<SearchResultView> {
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