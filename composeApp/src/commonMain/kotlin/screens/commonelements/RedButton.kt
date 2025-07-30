package screens.commonelements

import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import theme.AccentPrimaryTwo
import theme.TertiaryRedNotActive2
import theme.TextBody
import theme.TextStyleLocal.semibold18
import theme.TextWhite

@Composable
fun RedButton(text: String, onClick: () -> Unit = {}, isAllFieldsAllowed:Boolean =true) {
    TextButton(
        modifier = Modifier.padding(bottom = 8.dp)
            .width(304.dp)
            .height(48.dp)
            .clip(RoundedCornerShape(10.dp)).alpha(if (!isAllFieldsAllowed) 1f else 1f),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if(!isAllFieldsAllowed) TertiaryRedNotActive2 else AccentPrimaryTwo,
            contentColor = TextBody
        ),
        shape = RectangleShape
    ) {
        Text(
            text = text,
            color = TextWhite,
            style = semibold18,
        )
    }
}