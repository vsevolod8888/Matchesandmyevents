package screens.main.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app_b_0305_24.composeapp.generated.resources.Res
import app_b_0305_24.composeapp.generated.resources.arrow_back
import app_b_0305_24.composeapp.generated.resources.ic_stadium
import app_b_0305_24.composeapp.generated.resources.logo
import data.room.GameEntity
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import theme.AccentPrimary
import theme.AccentPrimaryTwo
import theme.AccentSecondary
import theme.AccentSecondaryTwo
import theme.LayerTwo
import theme.TextBody
import theme.TextSecondary
import theme.TextStyleLocal
import theme.TextWhite

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ItemGame(gameEntity: GameEntity, onClick: (GameEntity) -> Unit = {}) {
    Column(modifier = Modifier.padding(bottom = 5.dp).width(268.dp).height(96.dp)
        .clip(RoundedCornerShape(15.dp)).background(AccentPrimary)
        .clickable { onClick(gameEntity) }) {
        Row(
            modifier = Modifier.fillMaxWidth().height(28.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .padding(start = 15.dp)
                    .wrapContentHeight().fillMaxWidth().weight(1f),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                KamelImage(
                    resource = asyncPainterResource(gameEntity.picLeague),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    onFailure = { Image(painterResource(Res.drawable.logo), null) },
                    modifier = Modifier
                        .size(16.dp)
                )

                Text(
                    text = gameEntity.league,
                    style = TextStyleLocal.regular12,
                    modifier = Modifier.padding(start = 5.dp).wrapContentSize(),
                    color = TextBody,
                    textAlign = TextAlign.Start,
                    softWrap = true,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    letterSpacing = 0.sp
                )
            }


            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .height(20.dp).weight(0.6f)
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (gameEntity.stadium.isEmpty()) "Wembley" else gameEntity.stadium,
                    modifier = Modifier.wrapContentSize().weight(3f),
                    color = TextBody,
                    style = TextStyleLocal.regular12,
                    softWrap = true,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    letterSpacing = 0.sp,
                )
                Icon(
                    painter = painterResource(Res.drawable.ic_stadium),
                    contentDescription = "",
                    modifier = Modifier.weight(1f)
                        .padding(horizontal = 5.dp)
                        .size(13.dp),
                    tint = TextBody
                )
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth()
                .height(104.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(setColourByIndex(gameEntity.colour)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().height(0.dp).weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                KamelImage(
                    resource = asyncPainterResource(gameEntity.pic1),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    onFailure = { Image(painterResource(Res.drawable.logo), null) },
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(24.dp)
                )
                Text(
                    text = gameEntity.name1,
                    modifier = Modifier.weight(1f).padding(start = 8.dp),
                    color = TextWhite,
                    style = TextStyleLocal.semibold14,
                    softWrap = true,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    letterSpacing = 0.sp
                )

            }
            Row(
                modifier = Modifier.fillMaxWidth().height(0.dp).weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {

                KamelImage(
                    resource = asyncPainterResource(gameEntity.pic2),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    onFailure = { Image(painterResource(Res.drawable.logo), null) },
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(24.dp)
                )
                Text(
                    text = gameEntity.name2,
                    modifier = Modifier.weight(1f).padding(start = 8.dp),
                    color = TextWhite,
                    style = TextStyleLocal.semibold14,
                    softWrap = true,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    letterSpacing = 0.sp
                )

            }
        }

    }
}
