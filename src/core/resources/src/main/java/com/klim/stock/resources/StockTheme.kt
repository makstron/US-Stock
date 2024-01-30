package com.klim.stock.resources

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Spacer


import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val darkColorScheme = AppColorScheme(
    oneColor = Color.White
)

private val lightColorScheme = AppColorScheme(
    oneColor = Color.White
)

private val typography = AppTypograpphy(
    displayLarge = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = 0.sp,
    ),
    displayMedium = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp,
    ),
    displaySmall = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp,
    ),

    headLineLarge = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp,
    ),
    headLineMedium = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp,
    ),
    headLineSmall = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp,
    ),

    titleLarge = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = .15.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = .1.sp,
    ),

    bodyLarge = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp,
    ),

    labelLarge = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
    ),
)

private val shape = AppShape(
    rectangle = AbsoluteCutCornerShape(20.dp)
)

private val size = AppSize(
    appBarHeight = 50.dp,
    someSize = 16.dp,
)

@Composable
fun AppTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (isDarkTheme) darkColorScheme else lightColorScheme
    val rippleIndication = rememberRipple()
    CompositionLocalProvider(
        LocalAppColorShame provides colorScheme,
        LocalAppTypography provides typography,
        LocalAppShape provides shape,
        LocalAppSize provides size,
        LocalIndication provides rippleIndication,
    ) {
        content()
    }
}

object AppTheme {
    val styles: AppTypograpphy
        @Composable
        @ReadOnlyComposable
        get() = LocalAppTypography.current

}


@Preview
@Composable
fun DesignSystem() {
    AppTheme {
        Column {
            StockText(
                text = "Lorem ipsum.",
                style = AppTheme.styles.displayLarge
            )

            StockText(
                text = "Lorem ipsum.",
                style = AppTheme.styles.displayMedium
            )

            StockText(
                text = "Lorem ipsum.",
                style = AppTheme.styles.displaySmall
            )

            Spacer(modifier = Modifier.height(8.dp))


            StockText(
                text = "Lorem ipsum.",
                style = AppTheme.styles.headLineLarge
            )

            StockText(
                text = "Lorem ipsum.",
                style = AppTheme.styles.headLineMedium
            )

            StockText(
                text = "Lorem ipsum.",
                style = AppTheme.styles.headLineSmall
            )

            Spacer(modifier = Modifier.height(8.dp))


            StockText(
                text = "Lorem ipsum.",
                style = AppTheme.styles.titleLarge
            )

            StockText(
                text = "Lorem ipsum.",
                style = AppTheme.styles.titleMedium
            )

            StockText(
                text = "Lorem ipsum.",
                style = AppTheme.styles.titleSmall
            )

            Spacer(modifier = Modifier.height(8.dp))


            StockText(
                text = "Lorem ipsum.",
                style = AppTheme.styles.bodyLarge
            )

            StockText(
                text = "Lorem ipsum.",
                style = AppTheme.styles.bodyMedium
            )

            StockText(
                text = "Lorem ipsum.",
                style = AppTheme.styles.bodySmall
            )

            Spacer(modifier = Modifier.height(8.dp))


            StockText(
                text = "Lorem ipsum.",
                style = AppTheme.styles.labelLarge
            )

            StockText(
                text = "Lorem ipsum.",
                style = AppTheme.styles.labelMedium
            )

            StockText(
                text = "Lorem ipsum.",
                style = AppTheme.styles.labelSmall
            )
        }
    }
}


@Composable
fun StockText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    style: TextStyle = LocalTextStyle.current
) {
    Text(
        text = text,
        modifier = modifier,
        style = style,
    )
}


//@Composable
//fun StockTheme(
//    colors: Colors = MaterialTheme.colors,
//    typography: Typography = MaterialTheme.typography,
//    shapes: Shapes = MaterialTheme.shapes,
//    content: @Composable () -> Unit
//) {
//    val rememberedColors = remember {
//        // Explicitly creating a new object here so we don't mutate the initial [colors]
//        // provided, and overwrite the values set in it.
//        colors.copy()
//    }
//    //    .apply { updateColorsFrom(colors) }
//    val rippleIndication = rememberRipple()
////    val selectionColors = rememberTextSelectionColors(rememberedColors)
//    CompositionLocalProvider(
//        LocalApp
//        LocalColors provides rememberedColors,
//        LocalContentAlpha provides ContentAlpha.high,
//        LocalIndication provides rippleIndication,
//        LocalRippleTheme provides MaterialRippleTheme,
//        LocalShapes provides shapes,
//        LocalTextSelectionColors provides selectionColors,
//        LocalTypography provides typography
//    )
//    {
//        ProvideTextStyle(value = typography.body1) {
//            PlatformMaterialTheme(content)
//        }
//    }
//}