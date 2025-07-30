package theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import org.jetbrains.compose.resources.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import app_b_0305_24.composeapp.generated.resources.Res
import app_b_0305_24.composeapp.generated.resources.robotobold
import app_b_0305_24.composeapp.generated.resources.robotolight
import app_b_0305_24.composeapp.generated.resources.robotomedium


import org.jetbrains.compose.resources.ExperimentalResourceApi
@OptIn(ExperimentalResourceApi::class)
object TextStyleLocal {
    val headerLarge: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = FontFamily(Font(Res.font.robotomedium)),
            fontSize = 30.0.sp.nonScaledSp,
            letterSpacing = (0.0).sp,
            lineHeight = 30.0.sp.nonScaledSp
        )
    val headerMedium: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = FontFamily(Font(Res.font.robotobold)),
            fontSize = 26.0.sp.nonScaledSp,
            letterSpacing = (0.0).sp,
            lineHeight = 26.0.sp.nonScaledSp
        )
    val headerSmall: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = FontFamily(Font(Res.font.robotolight)),
            fontSize = 22.0.sp.nonScaledSp,
            letterSpacing = (0.0).sp,
            lineHeight = 22.0.sp.nonScaledSp
        )


    val regular12: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = FontFamily(Font(Res.font.robotolight)),
            fontSize = 12.0.sp.nonScaledSp,
            letterSpacing = (0.0).sp,
            lineHeight = 12.0.sp.nonScaledSp
        )

    val regular14: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = FontFamily(Font(Res.font.robotolight)),
            fontSize = 14.0.sp.nonScaledSp,
            letterSpacing = (0.0).sp,
            lineHeight = 14.0.sp.nonScaledSp
        )

    val regular16: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = FontFamily(Font(Res.font.robotolight)),
            fontSize = 16.0.sp.nonScaledSp,
            letterSpacing = (0.0).sp,
            lineHeight = 16.0.sp.nonScaledSp
        )

    val semibold14: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = FontFamily(Font(Res.font.robotomedium)),
            fontSize = 14.0.sp.nonScaledSp,
            letterSpacing = (0.0).sp,
            lineHeight = 14.0.sp.nonScaledSp
        )

    val semibold18: TextStyle
        @Composable
        get() = TextStyle(
            fontFamily = FontFamily(Font(Res.font.robotomedium)),
            fontSize = 18.0.sp.nonScaledSp,
            letterSpacing = (0.0).sp,
            lineHeight = 18.0.sp.nonScaledSp
        )
}