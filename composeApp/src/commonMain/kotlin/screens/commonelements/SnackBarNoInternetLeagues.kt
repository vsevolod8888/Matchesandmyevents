package screens.commonelements

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import app_b_0305_24.composeapp.generated.resources.Res
import app_b_0305_24.composeapp.generated.resources.loadingleagues
import app_b_0305_24.composeapp.generated.resources.nointernet
import app_b_0305_24.composeapp.generated.resources.retry
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import theme.LayerTwo
import theme.TextSecondary
import theme.TextStyleLocal
import theme.TextWhite

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SnackBarNoInternetLeagues(
    onRefreshClick: () -> Unit = {},
    textError: String = stringResource(Res.string.loadingleagues)
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoadingInfoItem(
            textLoading = textError,
            modifier = Modifier.fillMaxSize().weight(1f)
        )
        Row(
            modifier = Modifier.padding(bottom = 18.dp)
                .fillMaxWidth().height(68.dp).clip(RoundedCornerShape(20.dp)).background(LayerTwo),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier.fillMaxHeight().width(0.dp).weight(2f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    modifier = Modifier.padding(start = 20.dp),
                    text = stringResource(Res.string.nointernet),
                    color = TextSecondary,
                    style = TextStyleLocal.regular14
                )

            }
            Column(
                modifier = Modifier.fillMaxHeight().width(0.dp).weight(1f).clickable {
                        onRefreshClick()
                },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    modifier = Modifier.padding(end = 20.dp),
                    text = stringResource(Res.string.retry),
                    color = TextWhite,
                    style = TextStyleLocal.semibold18
                )
            }
        }
    }
}