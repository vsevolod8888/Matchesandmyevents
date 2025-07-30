package screens.main.item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app_b_0305_24.composeapp.generated.resources.Res
import app_b_0305_24.composeapp.generated.resources.pinnedevents
import data.room.BigItemDataClass
import data.room.EventEntity
import data.room.GameEntity
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import theme.AccentSecondary
import theme.TextStyleLocal

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ItemForPinnedEvents(bigItemEntity: BigItemDataClass, onClick: (Event) -> Unit = {}) {
    val isOnlyEventEntities = bigItemEntity.listItems.all { it is EventEntity }
    val rowHeight = if (isOnlyEventEntities) 50.dp else 96.dp
    val listState = rememberLazyListState()
    var startPadding by remember { mutableStateOf(16.dp) }

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex to listState.firstVisibleItemScrollOffset }
            .collect { (index, scrollOffset) ->
                startPadding = if (index == 0 && scrollOffset == 0) 16.dp else 0.dp
            }
    }

    Column(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
        Text(
            text = stringResource(Res.string.pinnedevents),
            modifier = Modifier.padding(start = 16.dp, top = 12.dp, bottom = 16.dp)
                .fillMaxWidth().wrapContentHeight(),
            color = AccentSecondary,
            style = TextStyleLocal.semibold18,
            textAlign = TextAlign.Start
        )

        LazyRow(
            state = listState,
            modifier = Modifier.padding(start = startPadding).height(rowHeight)
                .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            items(items = bigItemEntity.listItems) { event ->
                when (event) {

                    is GameEntity -> {
                        ItemGame(event as GameEntity, onClick = onClick)
                    }

                    else -> {
                        ItemEvent(event as EventEntity, onClick = onClick)
                    }
                }
                Spacer(modifier = Modifier.width(6.dp))
            }
        }
    }
}