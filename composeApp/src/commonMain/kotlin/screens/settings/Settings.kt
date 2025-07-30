package screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app_b_0305_24.composeapp.generated.resources.Res
import app_b_0305_24.composeapp.generated.resources.favouriteleagues
import app_b_0305_24.composeapp.generated.resources.notifications
import app_b_0305_24.composeapp.generated.resources.privacy_policy
import app_b_0305_24.composeapp.generated.resources.rateapp
import app_b_0305_24.composeapp.generated.resources.selectleaguestoseeupcoming
import app_b_0305_24.composeapp.generated.resources.settings
import app_b_0305_24.composeapp.generated.resources.shareapp
import app_b_0305_24.composeapp.generated.resources.termofuse
import kotlinx.coroutines.launch
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.BackHandler
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import screens.commonelements.ToolBar
import theme.TextStyleLocal
import theme.TextWhite


@OptIn(ExperimentalResourceApi::class)
@Composable
fun SettingsScreen(
    goToPolicy: () -> Unit,
    goToTermOfUse: () -> Unit,
    onClickBackFromSettings: () -> Unit,
    goToFavouriteLeagues: () -> Unit,
) {
    val viewModel: SettingsViewModel = koinViewModel(SettingsViewModel::class)
    var showDeleteDataDialog by remember { mutableStateOf(false) }
    var scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        ToolBar(
            stringResource(Res.string.settings),
            onArrowBackClick = { onClickBackFromSettings() },
            goToDeleteDialog = { showDeleteDataDialog = true },
            goToSettings = {}
        )
        Spacer(modifier = Modifier.height(12.dp))
        ButtonSimpleSettings(
            stringResource(Res.string.favouriteleagues),
            onClick = {
                goToFavouriteLeagues()
            },
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(Res.string.selectleaguestoseeupcoming),
            modifier = Modifier.fillMaxWidth()
                .wrapContentHeight(),
            color = TextWhite,
            style = TextStyleLocal.regular12,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))
        ButtonSimpleSettings(
            stringResource(Res.string.notifications),
            onClick = {
                viewModel.goToSettings()
            },
        )
        Spacer(modifier = Modifier.height(12.dp))
        ButtonSimpleSettings(
            stringResource(Res.string.privacy_policy),
            onClick = {
                goToPolicy()
            },
        )
        if (viewModel.canShowTermsOfUse()) {
            ButtonSimpleSettings(
                stringResource(Res.string.termofuse),
                onClick = {
                    goToTermOfUse()
                },
            )
        }

        ButtonSimpleSettings(
            stringResource(Res.string.shareapp),
            onClick = {
                viewModel.shareApp()
            },
        )
        ButtonSimpleSettings(
            stringResource(Res.string.rateapp),
            onClick = {
                viewModel.rateApp()
            },
        )
    }
    if (showDeleteDataDialog) {
        DeleteDataDialog(
            onConfirm = {
                scope.launch {
                    viewModel.deleteAllEvents()
                    viewModel.deleteAllChangesInMatches()

                }
                showDeleteDataDialog = false
            },
            onDismiss = {
                showDeleteDataDialog = false
            }
        )
    }
    BackHandler {onClickBackFromSettings() }
}