package com.klim.stock.favorited.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.klim.stock.favorited.usecase.api.FavoritedUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SymbolFavoritedViewModel
@Inject constructor(
    val favoritedUseCase: FavoritedUseCase,
) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Symbols"
    }
    var text: LiveData<String> = _text

    init {

    }

    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            _text.postValue(favoritedUseCase.getFavorited()?.map { it.symbol }.toString())
        }
    }
}