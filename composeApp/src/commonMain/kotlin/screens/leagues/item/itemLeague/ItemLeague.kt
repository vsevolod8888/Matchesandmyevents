package screens.leagues.item.itemLeague

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import app_b_0305_24.composeapp.generated.resources.Res
import app_b_0305_24.composeapp.generated.resources.logo_league_default
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import model.Leaguee
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import theme.AccentPrimaryTwo
import theme.TextBody
import theme.TextStyleLocal
import theme.TextWhite


@OptIn(ExperimentalResourceApi::class)
@Composable
fun ItemLeague(
    league: Leaguee, isChecked: Boolean, onCheckedChange: (Boolean, Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            league.logo?.let { asyncPainterResource(it) }?.let {
                KamelImage(
                    resource = it,
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    onFailure = {
                        Image(
                            painter = painterResource(Res.drawable.logo_league_default),
                            null,
                            modifier = Modifier.size(40.dp)
                        )
                    },
                    modifier = Modifier
                        .height(30.dp).width(40.dp).padding(end = 8.dp)
                )
            }
            league.nameOfLeague?.let {
                Text(
                    text = it,
                    color = TextWhite,
                    style = TextStyleLocal.regular16,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }

        Checkbox(
            checked = isChecked,

            colors = CheckboxDefaults.colors(
                checkedColor = AccentPrimaryTwo,
                uncheckedColor = AccentPrimaryTwo,
                checkmarkColor = TextBody

            ),

            onCheckedChange = { newValue ->
                onCheckedChange(newValue, league.id ?: -1)
            }
        )
    }


}