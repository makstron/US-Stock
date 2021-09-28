package com.klim.us_stock.ui.windows.search

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import javax.inject.Inject

class SearchResultFormatter
@Inject
constructor() {

    fun format(text: String, regex: Regex): CharSequence {
        val styledText = SpannableStringBuilder(text)
        val matches = regex.findAll(text)
        matches.forEach {
            styledText.setSpan(StyleSpan(Typeface.BOLD), it.range.first, it.range.last + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        return styledText
    }

}