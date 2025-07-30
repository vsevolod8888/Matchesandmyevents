package screens.commonelements

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import app_b_0305_24.composeapp.generated.resources.Res
import app_b_0305_24.composeapp.generated.resources.arrow_back
import app_b_0305_24.composeapp.generated.resources.ic_but_plus
import app_b_0305_24.composeapp.generated.resources.ic_but_plus_pressed
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import theme.AccentPrimaryTwo
import theme.AccentSecondary
import theme.TextBody
import theme.TextStyleLocal.headerLarge
import theme.TextWhite

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ButtonPlus(onClick: () -> Unit = {}) {

    var isPressed by remember { mutableStateOf(false) }

    val imageResource = if (isPressed) {
        painterResource(Res.drawable.ic_but_plus_pressed)
    } else {
        painterResource(Res.drawable.ic_but_plus)
    }

    Image(
        modifier = Modifier
            .padding(18.dp)
            .width(80.dp)
            .height(52.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = { isPressed = true },
                    onTap = {
                        isPressed = false
                        onClick()
                    }
                )
            },
        painter = imageResource,
        contentDescription = "",
        contentScale = ContentScale.Crop
    )
}