package co.zsmb.picompose.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun PiPracticeComposeTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = lightColors(
            primary = Color(0xFF4CAF50),
            primaryVariant = Color(0xFF087F23),
            secondary = Color(0xFF4CAF50),
        ),
        content = content,
    )
}
