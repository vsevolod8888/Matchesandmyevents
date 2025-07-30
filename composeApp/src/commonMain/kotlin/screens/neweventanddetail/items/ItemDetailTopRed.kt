package screens.neweventanddetail.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app_b_0305_24.composeapp.generated.resources.Res
import app_b_0305_24.composeapp.generated.resources.logoteam
import data.dto.convertTimestampToLocalTime
import data.room.GameEntity
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import screens.main.item.setColourByIndex
import theme.TextStyleLocal
import theme.TextWhite

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ItemDetailTopRed(gameEntity: GameEntity, indexOfColour: MutableState<Int>) {
    Column(
        modifier = Modifier.padding(16.dp).fillMaxWidth().height(154.dp)
            .clip(RoundedCornerShape(20.dp)).background(setColourByIndex(indexOfColour.value)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = gameEntity.league,
            style = TextStyleLocal.regular16,
            modifier = Modifier.padding(bottom = 5.dp).wrapContentSize(),
            color = TextWhite,
            textAlign = TextAlign.Center,
            softWrap = true,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            letterSpacing = 0.sp
        )
        Row(
            modifier = Modifier.fillMaxWidth().height(77.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            Column(
                modifier = Modifier.fillMaxHeight().width(0.dp).weight(2f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                KamelImage(
                    resource = asyncPainterResource(gameEntity.pic1),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    onFailure = {
                        Image(
                            painter = painterResource(Res.drawable.logoteam),
                            null,
                            modifier = Modifier.size(52.dp)
                        )
                    },
                    modifier = Modifier
                        .size(52.dp)
                )

                Text(
                    text = gameEntity.name1,
                    style = TextStyleLocal.semibold14,
                    modifier = Modifier.padding(horizontal = 5.dp),
                    color = TextWhite,
                    textAlign = TextAlign.Center,
                    softWrap = true,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    letterSpacing = 0.sp
                )
            }
            Column(
                modifier = Modifier.padding(vertical = 8.dp)
                    .fillMaxHeight()
                    .width(0.dp)
                    .weight(1.2f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = gameEntity.date,
                    modifier = Modifier.padding(top = 4.dp),
                    color = TextWhite,
                    style = TextStyleLocal.semibold14,
                )
                Text(
                    text = convertTimestampToLocalTime(gameEntity.startTimeTimeStamp),
                    modifier = Modifier.padding(bottom = 4.dp),
                    color = TextWhite,
                    style = TextStyleLocal.headerSmall,
                )
            }
            Column(
                modifier = Modifier.fillMaxHeight().width(0.dp).weight(2f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween

            ) {
                KamelImage(
                    resource = asyncPainterResource(gameEntity.pic2),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    onFailure = {
                        Image(
                            painter = painterResource(Res.drawable.logoteam),
                            null,
                            modifier = Modifier.size(52.dp)
                        )
                    },
                    modifier = Modifier.size(52.dp).padding(bottom = 6.dp)
                )
                Text(
                    text = gameEntity.name2,
                    style = TextStyleLocal.semibold14,
                    modifier = Modifier.padding(horizontal = 5.dp),
                    color = TextWhite,
                    textAlign = TextAlign.Center,
                    softWrap = true,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    letterSpacing = 0.sp
                )
            }
        }
    }
}