package com.klim.stock.settings.ui.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsScreen() {
    println("@@@ SettingsScreen recomposition")
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxHeight()

    ) {
        Spacer(modifier = Modifier.height(300.dp))
        Text(
            text = "Settings Screen",
            fontSize = 20.sp,
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
        )
    }

}

@Preview
@Composable
fun SettingsScreenPreview() {
    SettingsScreen()
}