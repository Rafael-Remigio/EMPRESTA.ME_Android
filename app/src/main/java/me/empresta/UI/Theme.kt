package me.empresta

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private  val DarkColorPalette = darkColors(
        primary = White,
        background = Black,
        onBackground = White,
    )

@Composable
fun CleanArchitecture(darkTheme: Boolean = true,content: @Composable() () -> Unit) {
    MaterialTheme(
        colors = DarkColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
