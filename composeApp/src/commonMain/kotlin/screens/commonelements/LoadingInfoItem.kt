package screens.commonelements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app_b_0305_24.composeapp.generated.resources.Res
import app_b_0305_24.composeapp.generated.resources.therearenomatches
import app_b_0305_24.composeapp.generated.resources.tocreateanotepress
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import theme.AccentPrimary
import theme.AccentPrimaryTwo
import theme.LayerThree
import theme.TextSecondary
import theme.TextStyleLocal


@OptIn(ExperimentalResourceApi::class)
@Composable
fun LoadingInfoItem(textLoading: String,modifier: Modifier = Modifier.fillMaxSize()) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(66.dp),
            strokeWidth = 6.dp,
            strokeCap = StrokeCap.Round,
            color = AccentPrimary,
            trackColor = AccentPrimaryTwo
        )
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = textLoading,
            color = TextSecondary,
            style = TextStyleLocal.regular14
        )
    }
}


@OptIn(ExperimentalResourceApi::class)
@Composable
fun NoMatchesItem(modifier: Modifier = Modifier.fillMaxSize()) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(Res.string.therearenomatches),
            modifier = Modifier.padding(top = 10.dp, bottom = 8.dp)
                .fillMaxWidth().wrapContentHeight(),
            color = AccentPrimaryTwo,
            style = TextStyleLocal.regular14,
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(Res.string.tocreateanotepress),
            modifier = Modifier
                .fillMaxWidth().wrapContentHeight(),
            color =
            TextSecondary,
            style = TextStyleLocal.regular14,
            textAlign = TextAlign.Center
        )
    }
}