package screens.main.item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.room.EventEntity
import org.jetbrains.compose.resources.ExperimentalResourceApi
import theme.AccentPrimaryTwo
import theme.Tertiary1
import theme.Tertiary2
import theme.Tertiary3
import theme.Tertiary4
import theme.Tertiary5
import theme.Tertiary6
import theme.Tertiary7
import theme.Tertiary8
import theme.Tertiary9
import theme.TextStyleLocal
import theme.TextWhite

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ItemEvent(eventEntity: EventEntity, onClick: (EventEntity) -> Unit = {}) {
    Row(
        modifier = Modifier
            .height(50.dp).width(268.dp)
            .clip(RoundedCornerShape(20.dp)).background(setColourByIndex(eventEntity.colour))
            .clickable { onClick(eventEntity) },
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = eventEntity.tittle,
            modifier = Modifier.weight(1f).padding(start = 16.dp),
            color = TextWhite,
            style = TextStyleLocal.semibold14,
            softWrap = true,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            letterSpacing = 0.sp
        )
    }
}

fun setColourByIndex(index: Int): Color {
    return when (index) {
        0 -> AccentPrimaryTwo
        1 -> Tertiary1
        2 -> Tertiary2
        3 -> Tertiary3
        4 -> Tertiary4
        5 -> Tertiary5
        6 -> Tertiary6
        7 -> Tertiary7
        8 -> Tertiary8
        else -> Tertiary9
    }

}
