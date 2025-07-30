package screens.leagues

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app_b_0305_24.composeapp.generated.resources.Res
import app_b_0305_24.composeapp.generated.resources.favouriteleagues
import app_b_0305_24.composeapp.generated.resources.leaguesscreentext1
import app_b_0305_24.composeapp.generated.resources.leaguesscreentext2
import app_b_0305_24.composeapp.generated.resources.leaguesscreentext3
import app_b_0305_24.composeapp.generated.resources.loadingevents
import app_b_0305_24.composeapp.generated.resources.loadingleagues
import app_b_0305_24.composeapp.generated.resources.maximumlimitofleagues
import app_b_0305_24.composeapp.generated.resources.savefavouriteleagues
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.BackHandler
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import screens.commonelements.LoadingInfoItem
import screens.commonelements.MaximumLimitn
import screens.commonelements.RedButton
import screens.commonelements.SnackBarNoInternetLeagues
import screens.commonelements.ToolBar
import screens.leagues.item.itemLeague.ItemLeague
import theme.Tertiary1
import theme.TextSecondary
import theme.TextStyleLocal

@OptIn(ExperimentalResourceApi::class)
@Composable
fun LeaguesScreen(
    onClickBackFromSelectLeagues: () -> Unit,
    onClickNextToMatchesScreen: () -> Unit
) {
    val viewModel: LeaguesViewModel = koinViewModel(LeaguesViewModel::class)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val selectedLeagues = remember { mutableStateListOf<Int>() }
    var toShowPopUpALotOfLeagues = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var job: Job? = null
    var isTransitioning by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getIntIdList()
        viewModel.getIntIdList().forEach {
            selectedLeagues.add(it)
        }
    }
    LaunchedEffect(selectedLeagues.size) {
        if (selectedLeagues.size > 5 && !toShowPopUpALotOfLeagues.value && job == null) {
            toShowPopUpALotOfLeagues.value = true
            job = scope.launch {
                delay(10000)
                toShowPopUpALotOfLeagues.value = false
            }
        } else if (selectedLeagues.size <= 5) {
            job?.cancel()
            toShowPopUpALotOfLeagues.value = false
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        ToolBar(
            stringResource(Res.string.favouriteleagues),
            onArrowBackClick = {

                    onClickBackFromSelectLeagues()

            },
            goToDeleteDialog = { },
            goToSettings = {}
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 12.dp)
                .fillMaxSize()
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = TextSecondary)) {
                        append(stringResource(Res.string.leaguesscreentext1))
                    }
                    withStyle(style = SpanStyle(color = Tertiary1)) {
                        append(stringResource(Res.string.leaguesscreentext2))
                    }
                    withStyle(style = SpanStyle(color = TextSecondary)) {
                        append(stringResource(Res.string.leaguesscreentext3))
                    }
                },
                style = TextStyleLocal.semibold14,
                lineHeight = 18.sp
            )
            when (uiState.loadingState) {
                LoadingState.DONE -> {
                    val leagues by uiState.listOfLeaguees.collectAsState(initial = emptyList())

                    if (leagues.isEmpty()) {
                        LoadingInfoItem(textLoading = stringResource(Res.string.loadingleagues))
                    } else {
                        Box(contentAlignment = Alignment.BottomCenter) {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(0.dp),
                                contentPadding = PaddingValues(horizontal = 0.dp, vertical = 0.dp)
                            ) {
                                items(items = leagues) { league ->
                                    val isChecked = selectedLeagues.contains(league.leagueId)

                                    ItemLeague(
                                        league = league,
                                        isChecked = isChecked,
                                        onCheckedChange = { isChecked, leagueId ->
                                            if (isChecked) {
                                                league.leagueId?.let { selectedLeagues.add(it) }
                                            } else {
                                                selectedLeagues.remove(league.leagueId)
                                            }
                                        }
                                    )
                                }
                                item {
                                    Spacer(modifier = Modifier.height(70.dp))
                                }
                            }
                            Column(
                                modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                RedButton(
                                    stringResource(Res.string.savefavouriteleagues) + " (${selectedLeagues.size}/5)",
                                    onClick = {
                                        if (selectedLeagues.size in 1..5) {
                                            scope.launch(Dispatchers.IO) {
                                                onClickNextToMatchesScreen()
                                                viewModel.setFirstLaunch(2)
                                                viewModel.saveIntIdList(selectedLeagues)
                                                this.cancel()
                                            }
                                        }
                                    },
                                    isAllFieldsAllowed = selectedLeagues.size in 1..5
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                            }

                            if (toShowPopUpALotOfLeagues.value) {
                                MaximumLimitn(stringResource(Res.string.maximumlimitofleagues))
                                Spacer(modifier = Modifier.height(18.dp))
                            }
                        }
                    }
                }

                LoadingState.LOADING -> {
                    LoadingInfoItem(textLoading = stringResource(Res.string.loadingleagues))
                }

                LoadingState.ERROR -> {
                    SnackBarNoInternetLeagues(
                        onRefreshClick = {
                            viewModel.getListOfLeagues()
                        },
                    )
                }
            }
        }
        BackHandler {
                onClickBackFromSelectLeagues()
        }
    }
}
