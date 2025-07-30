package screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import app_b_0305_24.composeapp.generated.resources.Res
import app_b_0305_24.composeapp.generated.resources.arrow_next
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import theme.LayerTwo
import theme.TextSecondary
import theme.TextStyleLocal
import theme.TextWhite

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ButtonSimpleSettings(
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 16.dp)
            .fillMaxWidth().height(50.dp).clip(RoundedCornerShape(50.dp))
            .background(LayerTwo)
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            modifier = Modifier.fillMaxWidth()
                .wrapContentHeight()
                .padding(start = 20.dp)
                .weight(6f),
            color = if (text.contains("Clear")) {
                TextWhite
            } else {
                TextWhite
            },
            style = TextStyleLocal.regular16,
        )
        Icon(
            painter = painterResource(Res.drawable.arrow_next),
            contentDescription = "",
            modifier = Modifier.height(15.dp).width(22.dp),
            tint = TextSecondary
        )
        Spacer(modifier = Modifier.width(17.dp))
    }
}