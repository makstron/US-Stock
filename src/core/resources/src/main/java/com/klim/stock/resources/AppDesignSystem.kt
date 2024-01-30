package com.klim.stock.resources

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class AppColorScheme(
    val oneColor: Color,
)

data class AppTypograpphy(
    val displayLarge: TextStyle,
    val displayMedium: TextStyle,
    val displaySmall: TextStyle,
    val headLineLarge: TextStyle,
    val headLineMedium: TextStyle,
    val headLineSmall: TextStyle,
    val titleLarge: TextStyle,
    val titleMedium: TextStyle,
    val titleSmall: TextStyle,
    val bodyLarge: TextStyle,
    val bodyMedium: TextStyle,
    val bodySmall: TextStyle,
    val labelLarge: TextStyle,
    val labelMedium: TextStyle,
    val labelSmall: TextStyle,
)

data class AppShape(
    val rectangle: Shape
)

data class AppSize(
    val appBarHeight: Dp,
    val someSize: Dp,
)

val LocalAppColorShame = staticCompositionLocalOf {
    AppColorScheme(
        oneColor = Color.Unspecified
    )
}

val LocalAppTypography = staticCompositionLocalOf {
    AppTypograpphy(
        displayLarge = TextStyle.Default,
        displayMedium = TextStyle.Default,
        displaySmall = TextStyle.Default,
        headLineLarge = TextStyle.Default,
        headLineMedium = TextStyle.Default,
        headLineSmall = TextStyle.Default,
        titleLarge = TextStyle.Default,
        titleMedium = TextStyle.Default,
        titleSmall = TextStyle.Default,
        bodyLarge = TextStyle.Default,
        bodyMedium = TextStyle.Default,
        bodySmall = TextStyle.Default,
        labelLarge = TextStyle.Default,
        labelMedium = TextStyle.Default,
        labelSmall = TextStyle.Default,
    )
}

val LocalAppShape = staticCompositionLocalOf {
    AppShape(
        rectangle = RectangleShape
    )
}

val LocalAppSize = staticCompositionLocalOf {
    AppSize(
        appBarHeight = Dp.Unspecified,
        someSize = Dp.Unspecified
    )
}