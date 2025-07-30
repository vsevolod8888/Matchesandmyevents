package screens.commonelements

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import theme.AccentPrimaryTwo
import theme.TextBody
import theme.TextStyleLocal
import theme.TextWhite

@Composable
fun MaximumLimitn(text: String) {
    TextButton(
        modifier = Modifier.padding(bottom = 18.dp).padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(68.dp)
            .clip(RoundedCornerShape(10.dp)),
        onClick = {},
        colors = ButtonDefaults.buttonColors(
            containerColor = AccentPrimaryTwo,
            contentColor = TextBody
        ),
        shape = RectangleShape
    ) {
        Text(
            text = text,
            color = TextWhite,
            style = TextStyleLocal.semibold14,
        )
    }
}