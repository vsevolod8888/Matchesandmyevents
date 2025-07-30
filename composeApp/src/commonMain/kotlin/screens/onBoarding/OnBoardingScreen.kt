package screens.onBoarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app_b_0305_24.composeapp.generated.resources.Res
import app_b_0305_24.composeapp.generated.resources.img_1
import app_b_0305_24.composeapp.generated.resources.next
import app_b_0305_24.composeapp.generated.resources.onboardingtext1
import app_b_0305_24.composeapp.generated.resources.onboardingtext2
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.BackHandler
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import screens.commonelements.RedButton
import theme.LayerOne
import theme.TextStyleLocal
import theme.TextWhite

@OptIn(ExperimentalResourceApi::class)
@Composable
fun OnBoardingScreen(onClickNext: () -> Unit) {
    val viewModel: OnBoardingViewModel = koinViewModel(OnBoardingViewModel::class)
    LaunchedEffect(Unit) {
        if (viewModel.getFirstLaunch() == 2) {
            onClickNext()
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LayerOne)
            .padding(horizontal = 16.dp), // добавил отступы по краям
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(Res.drawable.img_1),
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.padding(20.dp)
                .fillMaxWidth()
                .aspectRatio(16f / 14f)
        )
        Text(
            modifier = Modifier
                .padding(bottom = 16.dp, top = 30.dp),
            text = stringResource(Res.string.onboardingtext1).toUpperCase(),
            color = TextWhite,
            style = TextStyleLocal.headerMedium,
            textAlign = TextAlign.Center,
        )
        Text(
            modifier = Modifier
                .padding(bottom = 22.dp),
            text = stringResource(Res.string.onboardingtext2),
            color = TextWhite,
            style = TextStyleLocal.regular16,
            lineHeight = if (viewModel.canShow()) 0.sp else 20.sp,
            textAlign = TextAlign.Center,
        )
        RedButton(
            stringResource(Res.string.next),
            onClick = {
                onClickNext()
            },
        )
        Spacer(modifier = Modifier.height(10.dp))
    }
    BackHandler {
        viewModel.exitApp()
    }
}

