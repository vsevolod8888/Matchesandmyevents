package screens.loading

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import app_b_0305_24.composeapp.generated.resources.Res
import app_b_0305_24.composeapp.generated.resources.bg_load
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.BackHandler
import screens.onBoarding.OnBoardingViewModel
import theme.AccentPrimary
import theme.AccentPrimaryTwo
import theme.LayerOne
import utils.ApiKeyDecoder
//import utils.ApiKeyDecoder.decodeKey

@OptIn(ExperimentalResourceApi::class)
@Composable
fun LoadingScreen(goToOnboarding: () -> Unit = {}) {
    val viewModel: OnBoardingViewModel = koinViewModel(OnBoardingViewModel::class)

    LaunchedEffect(Unit) {
        delay(1000)
        goToOnboarding()
    }

    Column (
        modifier = Modifier.fillMaxSize().background(LayerOne),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Box(
            modifier = Modifier.width(284.dp).height(296.dp),
            contentAlignment = Alignment.BottomCenter
        ){
            Image(
                painter = painterResource(Res.drawable.bg_load),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(bottom = 105.dp)
                    .size(68.dp),
                strokeWidth = 10.dp,
                strokeCap = StrokeCap.Round,
                color = AccentPrimary,
                trackColor = AccentPrimaryTwo
            )
        }

    }
    BackHandler {
        viewModel.exitApp()
    }
}


