package screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import app_b_0305_24.composeapp.generated.resources.Res
import app_b_0305_24.composeapp.generated.resources.city
import app_b_0305_24.composeapp.generated.resources.country
import app_b_0305_24.composeapp.generated.resources.detailinfo
import app_b_0305_24.composeapp.generated.resources.league
import app_b_0305_24.composeapp.generated.resources.match
import app_b_0305_24.composeapp.generated.resources.name
import app_b_0305_24.composeapp.generated.resources.pinanevent
import app_b_0305_24.composeapp.generated.resources.referee
import app_b_0305_24.composeapp.generated.resources.round
import app_b_0305_24.composeapp.generated.resources.save
import app_b_0305_24.composeapp.generated.resources.season
import app_b_0305_24.composeapp.generated.resources.startmatch
import app_b_0305_24.composeapp.generated.resources.teams
import app_b_0305_24.composeapp.generated.resources.theeventhasbeensuccesfullypinned
import app_b_0305_24.composeapp.generated.resources.theeventhasbeensuccesfullyremoved
import app_b_0305_24.composeapp.generated.resources.unpintheevent
import app_b_0305_24.composeapp.generated.resources.venue
import data.dto.convertTimestampToLocalTime
import data.room.GameEntity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import moe.tlaster.precompose.koin.koinViewModel
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import screens.commonelements.RedButton
import screens.commonelements.ToolBarMatchDetail
import screens.commonelements.PopUpPin
import screens.neweventanddetail.items.HeaderItem
import screens.neweventanddetail.items.InfoItem
import screens.neweventanddetail.items.InfoItemLeague
import screens.neweventanddetail.items.InfoItemTeam
import screens.neweventanddetail.items.ItemChooseColour
import screens.neweventanddetail.items.ItemDetailTopRed
import screens.neweventanddetail.items.LimitedTextField
import theme.LayerOne
import theme.LayerThree

@OptIn(ExperimentalResourceApi::class, DelicateCoroutinesApi::class)
@Composable
fun MatchesDetailScreen(onClickBackFromDetail: () -> Unit) {
    val viewModel: MatchDetailViewModel = koinViewModel(MatchDetailViewModel::class)
    var idGame by remember { mutableStateOf(viewModel.getGameId()) }
    var selectedGameToShow by remember { mutableStateOf<GameEntity?>(null) }
    var indexOfColour = remember { mutableStateOf(1) }
    var isPinned = remember { mutableStateOf(false) }
    var toShowPopUpPinned = remember { mutableStateOf(false) }
    var areFieldsFilled by remember {
        mutableStateOf(false)
    }
    var scr = rememberScrollState()
    val keyboardController = LocalSoftwareKeyboardController.current
    var scope = rememberCoroutineScope()
    var textDescription by remember { mutableStateOf("") }


    LaunchedEffect(Unit) {
        selectedGameToShow = viewModel.getGameById(idGame)
    }
    LaunchedEffect(selectedGameToShow) {
        isPinned.value = selectedGameToShow?.isGameWasPinned ?: false
    }
    LaunchedEffect(selectedGameToShow) {
        indexOfColour.value = selectedGameToShow?.colour ?: 0
    }
    LaunchedEffect(selectedGameToShow) {
        textDescription = selectedGameToShow?.textEventMatch ?: ""
    }


    fun checkFieldsFilled() {
        areFieldsFilled = textDescription.isNotEmpty() || indexOfColour != null
    }
    Column(modifier = Modifier.fillMaxSize().clickable { keyboardController?.hide() }) {
        ToolBarMatchDetail(
            stringResource(Res.string.detailinfo),
            onArrowBackClick = { onClickBackFromDetail() },
            goToDeleteDialog = { },
            goToSettings = { },
        )
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            Column(modifier = Modifier.fillMaxSize().verticalScroll(scr)) {
                selectedGameToShow?.let {
                    ItemDetailTopRed(
                        gameEntity = it,
                        indexOfColour
                    )
                    HeaderItem(text = stringResource(Res.string.teams)) {
                        Spacer(modifier = Modifier.height(6.dp))
                        selectedGameToShow?.let {
                            InfoItemTeam(selectedGameToShow!!.pic1, selectedGameToShow!!.name1)
                            Spacer(modifier = Modifier.height(6.dp))
                            InfoItemTeam(selectedGameToShow!!.pic2, selectedGameToShow!!.name2)
                        }
                    }
                    Spacer(
                        modifier = Modifier.padding(horizontal = 16.dp)
                            .padding(top = 4.dp, bottom = 16.dp).fillMaxWidth().height(1.dp)
                            .background(
                                LayerThree
                            )
                    )
                    HeaderItem(text = stringResource(Res.string.match)) {
                        Spacer(modifier = Modifier.height(6.dp))
                        selectedGameToShow?.let {
                            InfoItem(
                                Res.string.startmatch,
                                selectedGameToShow!!.date + " | " + convertTimestampToLocalTime(
                                    selectedGameToShow!!.startTimeTimeStamp
                                )
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            InfoItem(Res.string.referee, selectedGameToShow!!.referee)
                            Spacer(modifier = Modifier.height(6.dp))
                            InfoItem(Res.string.venue, selectedGameToShow!!.stadium)
                            Spacer(modifier = Modifier.height(6.dp))
                            InfoItem(Res.string.city, selectedGameToShow!!.city)
                        }
                    }
                    Spacer(
                        modifier = Modifier.padding(horizontal = 16.dp)
                            .padding(top = 4.dp, bottom = 16.dp).fillMaxWidth().height(1.dp)
                            .background(
                                LayerThree
                            )
                    )
                    HeaderItem(text = stringResource(Res.string.league)) {
                        Spacer(modifier = Modifier.height(6.dp))
                        selectedGameToShow?.let {
                            InfoItemLeague(Res.string.name, selectedGameToShow!!.league,selectedGameToShow!!)
                            Spacer(modifier = Modifier.height(6.dp))
                            InfoItem(Res.string.country, selectedGameToShow!!.country)
                            Spacer(modifier = Modifier.height(6.dp))
                            InfoItem(Res.string.season, "2024")
                            Spacer(modifier = Modifier.height(6.dp))
                            InfoItem(Res.string.round, selectedGameToShow!!.round)
                        }
                    }
                    ItemChooseColour(
                        colourIndex = indexOfColour,
                        onChooseColour = {
                            indexOfColour.value = it
                            checkFieldsFilled()
                        })
                    Row(
                        modifier = Modifier.background(LayerOne).padding(horizontal = 16.dp)
                            .padding(bottom = 16.dp)
                            .fillMaxWidth()
                            .height(140.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .border(
                                1.dp, LayerThree, RoundedCornerShape(20.dp)
                            ),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        LimitedTextField(
                            textDescription = textDescription,
                            onTextChange = {
                                textDescription = it
                                checkFieldsFilled()


                            },
                            maxNumbersEditTextText = 140 // Example max characters
                        )
                    }
                    Spacer(modifier = Modifier.height(149.dp))
                }
            }
            Column(
                modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(19.dp))
                RedButton(
                    if (isPinned.value)
                        stringResource(Res.string.unpintheevent)
                    else
                        stringResource(Res.string.pinanevent),
                    onClick = {
                        if (isPinned.value) {
                            scope.launch(Dispatchers.IO) {
                                toShowPopUpPinned.value = true
                                selectedGameToShow?.let {
                                    viewModel.updateIsMatchWasPinned(it.id, false)
                                    viewModel.addIdToListChangedMatches(it.id)
                                }
                                isPinned.value = false
                                delay(500)
                                toShowPopUpPinned.value = false
                            }

                        } else {
                            scope.launch(Dispatchers.IO) {
                                toShowPopUpPinned.value = true
                                selectedGameToShow?.let {
                                    viewModel.updateIsMatchWasPinned(it.id, true)
                                    viewModel.addIdToListChangedMatches(it.id)
                                }
                                isPinned.value = true
                                delay(500)
                                toShowPopUpPinned.value = false
                            }
                        }
                    },
                    isAllFieldsAllowed = true
                )
                RedButton(
                    stringResource(Res.string.save),
                    onClick = {
                        if (areFieldsFilled) {
                            scope.launch(Dispatchers.IO) {
                                onClickBackFromDetail()
                                selectedGameToShow?.let {
                                    viewModel.updateColourIndexMatch(
                                        it.id,
                                        indexOfColour.value
                                    )
                                    viewModel.updateTextEventMatch(
                                        it.id, textDescription
                                    )
                                    viewModel.addIdToListChangedMatches(it.id)
                                }
                            }

//                            onClickBackFromDetail()
                        }
                    },
                    isAllFieldsAllowed = areFieldsFilled
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
            if (toShowPopUpPinned.value) {
                PopUpPin(
                    if (isPinned.value) stringResource(Res.string.theeventhasbeensuccesfullypinned) else stringResource(
                        Res.string.theeventhasbeensuccesfullyremoved
                    )
                )

            }
        }
    }
}