package screens.neweventanddetail.items

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app_b_0305_24.composeapp.generated.resources.Res
import app_b_0305_24.composeapp.generated.resources.endtime
import app_b_0305_24.composeapp.generated.resources.starttime
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import theme.LayerOne
import theme.LayerThree
import theme.TextSecondary
import theme.TextStyleLocal


@OptIn(ExperimentalResourceApi::class)
@Composable
fun ItemChooseTime(
    hoursStart: MutableState<Int>,
    minutesStart: MutableState<Int>,
    hoursEnd: MutableState<Int>,
    minutesEnd: MutableState<Int>,
    onChooseTime: () -> Unit,
) {
    Row(
        modifier = Modifier.background(LayerOne).padding(horizontal = 16.dp)
            .fillMaxWidth().height(76.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.width(132.dp).height(76.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = stringResource(Res.string.starttime),
                style = TextStyleLocal.regular14,
                modifier = Modifier.padding(bottom = 6.dp),
                color = TextSecondary,
                textAlign = TextAlign.Start
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth().height(38.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.width(57.dp).height(38.dp).border(
                        1.dp, LayerThree, RoundedCornerShape(20.dp)
                    ).clickable {
                        hoursStart.value = (hoursStart.value + 1) % 24
                        onChooseTime()
                    },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = hoursStart.value.toString().padStart(2, '0'),// hours start game
                        style = TextStyleLocal.semibold18,
                        modifier = Modifier,
                        color = TextSecondary,
                        textAlign = TextAlign.Center
                    )
                }
                Column(
                    modifier = Modifier.width(57.dp).height(38.dp).border(
                        1.dp, LayerThree, RoundedCornerShape(20.dp)
                    ).clickable {
                        minutesStart.value = (minutesStart.value + 1) % 60
                        onChooseTime()
                    },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = minutesStart.value.toString().padStart(2, '0'),// minutes start game
                        style = TextStyleLocal.semibold18,
                        modifier = Modifier,
                        color = TextSecondary,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }


        Column(
            modifier = Modifier.width(132.dp).height(76.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = stringResource(Res.string.endtime),
                style = TextStyleLocal.regular14,
                modifier = Modifier.padding(bottom = 6.dp),
                color = TextSecondary,
                textAlign = TextAlign.Start
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth().height(38.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.width(57.dp).height(38.dp).border(
                        1.dp, LayerThree, RoundedCornerShape(20.dp)
                    ).clickable {
                        hoursEnd.value = (hoursEnd.value + 1) % 24
                        onChooseTime()
                    },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = hoursEnd.value.toString().padStart(2, '0'),// hours end game
                        style = TextStyleLocal.semibold18,
                        modifier = Modifier,
                        color = TextSecondary,
                        textAlign = TextAlign.Center
                    )
                }
                Column(
                    modifier = Modifier.width(57.dp).height(38.dp).border(
                        1.dp, LayerThree, RoundedCornerShape(20.dp)
                    ).clickable {
                        minutesEnd.value = (minutesEnd.value + 1) % 60
                        onChooseTime()
                    },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = minutesEnd.value.toString().padStart(2, '0'),// minutes end game
                        style = TextStyleLocal.semibold18,
                        modifier = Modifier,
                        color = TextSecondary,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
