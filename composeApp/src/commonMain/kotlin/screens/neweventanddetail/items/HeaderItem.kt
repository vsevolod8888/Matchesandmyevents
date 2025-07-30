package screens.neweventanddetail.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app_b_0305_24.composeapp.generated.resources.Res
import app_b_0305_24.composeapp.generated.resources.logo_league_default
import app_b_0305_24.composeapp.generated.resources.logoteam
import data.room.GameEntity
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import theme.AccentSecondaryTwo
import theme.TextSecondary
import theme.TextStyleLocal
import theme.TextWhite

@Composable
fun HeaderItem(text: String, spacer: Boolean = true, content: @Composable () -> Unit) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(26.dp)
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = text,
                style = TextStyleLocal.semibold18,
                modifier = Modifier.wrapContentSize(),
                color = AccentSecondaryTwo,
                textAlign = TextAlign.Start,
                softWrap = true,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                letterSpacing = 0.sp
            )
        }

        content()

       if (spacer) Spacer(modifier = Modifier.height(12.dp))
    }
}
@OptIn(ExperimentalResourceApi::class)
@Composable
fun InfoItemTeam(picTeam: String,nameTeam: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        KamelImage(
            resource = asyncPainterResource(picTeam),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            onFailure = {
                Image(
                    painter = painterResource(Res.drawable.logoteam),
                    null,
                    modifier = Modifier.size(30.dp)
                )
            },
            modifier = Modifier
                .size(30.dp)
        )

        Text(
            text = nameTeam,
            style = TextStyleLocal.semibold14,
            modifier = Modifier.padding(start = 4.dp).wrapContentSize(),
            color = TextWhite,
            textAlign = TextAlign.Start,
            softWrap = true,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            letterSpacing = 0.sp
        )
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun InfoItem(titleText: StringResource, descText: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(titleText),
            color = TextSecondary,
            style = TextStyleLocal.regular16
        )

        Text(
            text = descText,
            color = TextWhite,
            style = TextStyleLocal.semibold14,
            modifier = Modifier
                .padding(start = 6.dp)
                .weight(1f),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            textAlign = TextAlign.End
        )
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun InfoItemLeague(titleText: StringResource, descText: String,gameEntity: GameEntity) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(titleText),
            color = TextSecondary,
            style = TextStyleLocal.regular16
        )

        Text(
            text = descText,
            color = TextWhite,
            style = TextStyleLocal.semibold14,
            modifier = Modifier
                .padding(start = 6.dp)
                .weight(1f),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            textAlign = TextAlign.End
        )
        gameEntity.picLeague?.let { asyncPainterResource(it) }?.let {
            KamelImage(
                resource = it,
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                onFailure = {
                    Image(
                        painter = painterResource(Res.drawable.logo_league_default),
                        null,
                        modifier = Modifier.size(30.dp)
                    )
                },
                modifier = Modifier
                    .height(25.dp).width(30.dp).padding(start = 8.dp)
            )
        }
    }
}