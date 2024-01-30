package com.klim.stock.resources

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

class UiText(
    @StringRes val stringId: Int
) {

    @Composable
    fun asString(): String {
        return stringResource(id = stringId)
    }

}