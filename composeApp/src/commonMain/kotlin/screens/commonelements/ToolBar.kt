package screens.commonelements

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.ExperimentalResourceApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app_b_0305_24.composeapp.generated.resources.Res
import app_b_0305_24.composeapp.generated.resources.arrow_back
import app_b_0305_24.composeapp.generated.resources.ic_delete
import app_b_0305_24.composeapp.generated.resources.ic_settings
import org.jetbrains.compose.resources.painterResource
import theme.AccentSecondary
import theme.LayerOne
import theme.TextStyleLocal
import theme.TextWhite

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ToolBar(
    textToolbar: String,
    onArrowBackClick: () -> Unit,
    goToDeleteDialog: () -> Unit,
    goToSettings: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(LayerOne)
            .statusBarsPadding()
            .height(64.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        if (!textToolbar.equals("Work schedule")) {
            Icon(
                painter = painterResource(Res.drawable.arrow_back),
                contentDescription = "",
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(32.dp)
                    .clickable { onArrowBackClick() },
                tint = AccentSecondary
            )
        } else {
            Spacer(
                modifier = Modifier.padding(start = 10.dp).size(2.dp)
            )
        }
        Text(
            text = textToolbar,
            style = TextStyleLocal.headerSmall,
            modifier = Modifier.padding(start = 16.dp).weight(1f),
            color = TextWhite,
            textAlign = TextAlign.Start
        )

        if (textToolbar.equals("Settings")) {
            Image(
                painter = painterResource(Res.drawable.ic_delete),
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.padding(end = 20.dp).height(24.dp).width(18.dp).clickable {
                    goToDeleteDialog()
                }
            )
        } else if (textToolbar.equals("Work schedule")) {
            Image(
                painter = painterResource(Res.drawable.ic_settings),
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.padding(end = 20.dp).height(22.dp).width(20.dp).clickable {
                    goToSettings()
                }
            )
        } else if (textToolbar.equals("Detail info")) {
            Image(
                painter = painterResource(Res.drawable.ic_delete),
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.padding(end = 20.dp).height(22.dp).width(20.dp).clickable {
                        goToDeleteDialog()
                }
            )
        }else {
            Spacer(
                modifier = Modifier.padding(end = 20.dp).width(18.dp)
            )
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ToolBarMatchDetail(
    textToolbar: String,
    onArrowBackClick: () -> Unit,
    goToDeleteDialog: () -> Unit,
    goToSettings: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(LayerOne)
            .statusBarsPadding()
            .height(64.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        if (!textToolbar.equals("Work schedule")) {
            Icon(
                painter = painterResource(Res.drawable.arrow_back),
                contentDescription = "",
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(32.dp)
                    .clickable { onArrowBackClick() },
                tint = AccentSecondary
            )
        } else {
            Spacer(
                modifier = Modifier.padding(start = 10.dp).size(32.dp)
            )
        }
        Text(
            text = textToolbar,
            style = TextStyleLocal.headerSmall,
            modifier = Modifier.padding(start = 16.dp).weight(1f),
            color = TextWhite,
            textAlign = TextAlign.Start
        )

        if (textToolbar.equals("Settings")) {
            Image(
                painter = painterResource(Res.drawable.ic_delete),
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.padding(end = 20.dp).height(24.dp).width(18.dp).clickable {
                    goToDeleteDialog()
                }
            )
        } else if (textToolbar.equals("Work schedule")) {
            Image(
                painter = painterResource(Res.drawable.ic_settings),
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.padding(end = 20.dp).height(22.dp).width(20.dp).clickable {
                    goToSettings()
                }
            )
        } else if (textToolbar.equals("Detail info")) {
            Spacer(
                modifier = Modifier.padding(end = 20.dp).width(18.dp)

            )
        }else {
            Spacer(
                modifier = Modifier.padding(end = 20.dp).width(18.dp)
            )
        }
    }
}