package com.klim.stock.info.ui.presentation

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
fun InfoScreen() {
    println("@@@ InfoScreen recomposition")
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxHeight()

    ) {
        Spacer(modifier = Modifier.height(300.dp))
        Text(
            text = "Info Screen",
            fontSize = 20.sp,
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
        )
    }

}

@Preview
@Composable
fun SettingsScreenPreview() {
    InfoScreen()
}