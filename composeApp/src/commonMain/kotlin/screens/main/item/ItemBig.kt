package screens.main.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import data.dto.convertTimestampToLocalTime
import data.room.BigItemDataClass
import data.room.EventEntity
import data.room.GameEntity
import theme.TextStyleLocal
import theme.TextWhite

@Composable
fun ItemBig(bigItemEntity: BigItemDataClass, onClick: (Event) -> Unit = {}) {
    val isOnlyEventEntities = bigItemEntity.listItems.all { it is EventEntity }
    val rowHeight = if (isOnlyEventEntities) 50.dp else 96.dp
    Row(modifier = Modifier.height(rowHeight).fillMaxWidth()) {
        Column(
            modifier = Modifier.fillMaxHeight().width(54.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = convertTimestampToLocalTime(bigItemEntity.startTimeTimeStamp),
                style = TextStyleLocal.regular12,
                modifier = Modifier.padding(top = 6.dp),
                color = TextWhite,
                textAlign = TextAlign.Start
            )
            Text(
                text = convertTimestampToLocalTime(bigItemEntity.endTimeTimeStamp),
                style = TextStyleLocal.regular12,
                modifier = Modifier.padding(bottom = 6.dp),
                color = TextWhite,
                textAlign = TextAlign.Start
            )
        }

        LazyRow(modifier = Modifier.fillMaxHeight().wrapContentWidth(), verticalAlignment = Alignment.CenterVertically) {
            items(items = bigItemEntity.listItems) { event ->
                when (event) {

                    is GameEntity
                    -> {
                            ItemGame(event as GameEntity, onClick  = onClick)
                    }

                    else -> {
                        ItemEvent(event as EventEntity, onClick  = onClick)
                    }
                }
                Spacer(modifier = Modifier.width(6.dp))
            }
        }
    }
}
