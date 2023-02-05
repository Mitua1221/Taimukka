package com.arjental.taimukka.presentaion.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.arjental.taimukka.R

val fontFamily = FontFamily(
    Font(R.font.roboto_black900, FontWeight.W900),
    //Font(R.font.montserrat_extrabold800, FontWeight.W800),
    Font(R.font.roboto_bold700, FontWeight.W700),
    //Font(R.font.montserrat_semibold600, FontWeight.W600),
    Font(R.font.roboto_medium500, FontWeight.W500),
    Font(R.font.roboto_regular400, FontWeight.W400),
    Font(R.font.roboto_light300, FontWeight.W300),
    //Font(R.font.montserrat_extralight200, FontWeight.W200),
    Font(R.font.roboto_thin100, FontWeight.W100),
)

const val globalFontScale = 1.14
const val baselineShiftScale = 0.23f


val typography = Typography(
    displayLarge = TextStyle(
        fontWeight = FontWeight.W400,
        fontFamily = fontFamily,
        fontSize = (57 * globalFontScale).sp,
        lineHeight = (64 * 0.96).sp,
        letterSpacing = 0.sp
    ),
    displayMedium = TextStyle(
        fontWeight = FontWeight.W400,
        fontFamily = fontFamily,
        fontSize = (45 * globalFontScale).sp,
        lineHeight = (52 * 0.99).sp,
        letterSpacing = 0.sp
    ),
    displaySmall = TextStyle(
        fontWeight = FontWeight.W400,
        fontFamily = fontFamily,
        fontSize = (36 * globalFontScale).sp,
        lineHeight = (44 * 1.04).sp,
        letterSpacing = 0.sp
    ),
    headlineLarge = TextStyle(
        fontWeight = FontWeight.W400,
        fontFamily = fontFamily,
        fontSize = (32 * globalFontScale).sp,
        lineHeight = (40 * 1.07).sp,
        letterSpacing = 0.sp
    ),
    headlineMedium = TextStyle(
        fontWeight = FontWeight.W400,
        fontFamily = fontFamily,
        fontSize = (28 * globalFontScale).sp,
        lineHeight = (36 * 1.10).sp,
        letterSpacing = 0.sp
    ),
    headlineSmall = TextStyle(
        fontWeight = FontWeight.W400,
        fontFamily = fontFamily,
        fontSize = (24 * globalFontScale).sp,
        lineHeight = (32 * 1.14).sp,
        letterSpacing = 0.sp
    ),
    titleLarge = TextStyle(
        fontWeight = FontWeight.W400,
        fontFamily = fontFamily,
        fontSize = (22 * globalFontScale).sp,
        lineHeight = (28 * 1.08).sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.W500,
        fontFamily = fontFamily,
        fontSize = (16 * globalFontScale).sp,
        lineHeight = (24 * 1.28).sp,
    ),
    titleSmall = TextStyle(
        fontWeight = FontWeight.W500,
        fontFamily = fontFamily,
        fontSize = (14 * globalFontScale).sp,
        lineHeight = (20 * 1.22).sp,
    ),
    labelLarge = TextStyle(
        fontWeight = FontWeight.W500,
        fontFamily = fontFamily,
        fontSize = (14 * globalFontScale).sp,
        lineHeight = (20 * 1.22).sp,
    ),
    labelMedium = TextStyle(
        fontWeight = FontWeight.W500,
        fontFamily = fontFamily,
        fontSize = (12 * globalFontScale).sp,
        lineHeight = (16 * 1.14).sp,
    ),
    labelSmall = TextStyle(
        fontWeight = FontWeight.W500,
        fontFamily = fontFamily,
        fontSize = (11 * globalFontScale).sp,
        lineHeight = (16 * 1.24).sp,
    ),
    bodyLarge = TextStyle(
        fontWeight = FontWeight.W400,
        fontFamily = fontFamily,
        fontSize = (16 * globalFontScale).sp,
        lineHeight = (24 * 1.28).sp,
        baselineShift = BaselineShift(baselineShiftScale),
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Bottom,
            trim = LineHeightStyle.Trim.None
        )
    ),
    bodyMedium = TextStyle(
        fontWeight = FontWeight.W400,
        fontFamily = fontFamily,
        fontSize = (14.sp * globalFontScale),
        lineHeight = (20 * 1.22).sp,
        baselineShift = BaselineShift(baselineShiftScale),
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Bottom,
            trim = LineHeightStyle.Trim.None
        )
    ),
    bodySmall = TextStyle(
        fontWeight = FontWeight.W400,
        fontFamily = fontFamily,
        fontSize = (12 * globalFontScale).sp,
        lineHeight = (16 * 1.14).sp,
        baselineShift = BaselineShift(baselineShiftScale),
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Bottom,
            trim = LineHeightStyle.Trim.None
        )
    ),
)

fun TextStyle.toOnBoardingSign() = this.copy(
    fontWeight = FontWeight.W400,
    fontFamily = fontFamily,
    fontSize = 25.8.sp,
    lineHeight = 44.5.sp,
    letterSpacing = 15.sp,
)

