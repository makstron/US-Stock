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
import com.klim.us_stock.domain.entity.SearchResultEntity
import com.klim.us_stock.domain.usecase.SearchUseCase
import kotlinx.coroutines.*
import javax.inject.Inject


class SearchViewModel
@Inject constructor(private val searchUseCase: SearchUseCase) : ViewModel() {

    //delay while user write
    private val DELAY_BEFORE_SEND_SEARCH_REQUEST = 700L

    //need for tests in the future
    private val dispatcherMain = Dispatchers.Main
    private val dispatcherIO = Dispatchers.IO

    private val _searchResults = MutableLiveData<List<SearchResultView>>()
    val searchResults: LiveData<List<SearchResultView>> = _searchResults

    val isSearching = ObservableBoolean(false)
    val isExistsResult = ObservableBoolean(true)

    var searchRequest = ""

    fun search(request: String) {
        searchRequest = request
        isSearching.set(true)
        viewModelScope.launch(dispatcherMain) {
            val results = searchUseCase.search(SearchUseCase.Params(searchRequest))
            val resultsViews: List<SearchResultView>
            withContext(dispatcherIO) {
                resultsViews = prepareSearchResults(results)
            }
            isExistsResult.set(resultsViews.isNotEmpty())
            _searchResults.postValue(resultsViews)
            isSearching.set(false)
        }
    }

    private fun prepareSearchResults(results: List<SearchResultEntity>): List<SearchResultView> {
        val regex = Regex(searchRequest, RegexOption.IGNORE_CASE)
        return results.map { sre ->
            SearchResultView(
                ticker = sre.ticker,
                tickerStyled = setStyleForText(sre.ticker, regex),
                companyStyled = setStyleForText(sre.name, regex)
            )
        }
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