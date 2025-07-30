package theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp



val TextUnit.nonScaledSp
    @Composable
    get() = (this.value / LocalDensity.current.fontScale).sp

private val AppColorScheme = lightColorScheme(
    primary = AccentSecondary,
    background = BackgroundMain,
)

@Composable
fun AppTheme(content: @Composable () -> Unit) {

    MaterialTheme(
        colorScheme = AppColorScheme,
        typography = MaterialTheme.typography.copy(
            bodyLarge = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
            labelLarge = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.W500,
                fontSize = 14.sp.nonScaledSp,
            )
        ),
        content = content
    )
}