package com.klim.stock.analytics.analytics

import androidx.annotation.Size

enum class AnalyticKeys(
    @Size(min = 1L, max = 40L)
    val key: String
) {
    ACTION_OPEN_SYMBOL_DETAILS("action_open_symbol_details"),
    ACTION_EXIT("action_app_close"),
    ACTION_OPEN("action_app_open"),
    ;
}