package screens.main.item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import app_b_0305_24.composeapp.generated.resources.Res
import app_b_0305_24.composeapp.generated.resources.but_next
import app_b_0305_24.composeapp.generated.resources.but_prew
import io.wojciechosak.calendar.utils.today
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import theme.AccentPrimary
import theme.AccentPrimaryTwo
import theme.LayerOne
import theme.TextStyleLocal
import theme.TextWhite

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ChoosenWeekItem(
    onClickPrevious: () -> Unit,
    onClickNext: () -> Unit,
    name:String,
    isCurrentWeek:Boolean =true
){
    var dateToday = LocalDate.today()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp).background(LayerOne),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {


        Icon(
            painter = painterResource(Res.drawable.but_prew),
            contentDescription = "",
            modifier = Modifier
                .padding(start = 18.dp)
                .width(8.dp)
                .height(14.dp)
                .clickable { onClickPrevious() },
            tint = if (!isCurrentWeek) AccentPrimary else AccentPrimaryTwo
        )
        Text(
            text = name,
            modifier = Modifier.padding(horizontal = 10.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .weight(1f),
            color = TextWhite,
            style = TextStyleLocal.semibold18,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Icon(
            painter = painterResource(Res.drawable.but_next),
            contentDescription = "",
            modifier = Modifier
                .padding(end = 18.dp)
                .width(8.dp)
                .height(14.dp)
                .clickable { onClickNext() },
            tint = if (isCurrentWeek) AccentPrimary else AccentPrimaryTwo
        )
    }
}