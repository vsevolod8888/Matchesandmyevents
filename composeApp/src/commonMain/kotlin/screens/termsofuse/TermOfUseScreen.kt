package screens.termsofuse

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app_b_0305_24.composeapp.generated.resources.Res
import app_b_0305_24.composeapp.generated.resources.termofuse
import app_b_0305_24.composeapp.generated.resources.terms_of_use_text
import moe.tlaster.precompose.navigation.BackHandler
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import screens.commonelements.ToolBar
import theme.TextStyleLocal
import theme.TextWhite

@OptIn(ExperimentalResourceApi::class)
@Composable
fun TermOfUseScreen(onClickBackFromTermsOfUse:  () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        ToolBar(
            stringResource(Res.string.termofuse),
            onArrowBackClick = { onClickBackFromTermsOfUse() },
            goToDeleteDialog = {},
            goToSettings = {}
        )
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = stringResource(Res.string.terms_of_use_text),
                style = TextStyleLocal.regular14,
                modifier = Modifier,
                color = TextWhite
            )
        }
        BackHandler { onClickBackFromTermsOfUse() }
    }

}