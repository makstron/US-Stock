package com.klim.us_stock.ui.windows.search

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.klim.us_stock.data.repository.symbol.SymbolRepository
import com.klim.us_stock.data.repository.symbol.data_source.remote.SymbolRemoteDataSource
import com.klim.us_stock.data.retrofit.RetrofitProvider
import com.klim.us_stock.domain.entity.SearchResultEntity
import com.klim.us_stock.domain.repository.SymbolRepositoryI
import kotlinx.coroutines.*
import javax.inject.Inject


class SearchViewModel
@Inject constructor(val repository: SymbolRepositoryI) : ViewModel() {

    private val DELAY_BEFORE_SEND_SEARCH_REQUEST = 700L

//    val repository: SymbolRepositoryI = SymbolRepository(SymbolRemoteDataSource(RetrofitProvider.get().searchStockSymbolApi))

    private val _searchResults = MutableLiveData<List<SearchResultView>>()
    val searchResults: LiveData<List<SearchResultView>> = _searchResults

    val isSearching = ObservableBoolean(false)
    val isExistsResult = ObservableBoolean(true)

    var searchRequest = ""
    var job: Job? = null

    fun preSearch(request: String) {
        searchRequest = request
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.Main) {
            delay(DELAY_BEFORE_SEND_SEARCH_REQUEST)
            job = null
            search()
        }
    }

    private fun search() {
        isSearching.set(true)
        viewModelScope.launch(Dispatchers.Main) {
            val results: List<SearchResultEntity>
            withContext(Dispatchers.IO) {
                results = repository.search(searchRequest)
            }

            prepareSearchResults(results)
        }
    }

    private fun prepareSearchResults(results: List<SearchResultEntity>) {
        val regex = Regex(searchRequest, RegexOption.IGNORE_CASE)
        val resultsView = results.map { sre ->
            SearchResultView(
                ticker = sre.ticker,
                tickerStyled = setStyleForText(sre.ticker, regex),
                companyStyled = setStyleForText(sre.name, regex)
            )
        }
        isExistsResult.set(resultsView.isNotEmpty())
        _searchResults.postValue(resultsView)
        isSearching.set(false)
    }

    private fun setStyleForText(text: String, regex: Regex): SpannableStringBuilder {
        val styledText = SpannableStringBuilder(text)
        val matches = regex.findAll(text)
        matches.forEach {
            styledText.setSpan(StyleSpan(Typeface.BOLD), it.range.first, it.range.last + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        return styledText
    }
}