package screens.neweventanddetail.items

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import theme.AccentPrimaryTwo
import theme.LayerOne
import theme.LayerThree
import theme.Tertiary1
import theme.Tertiary2
import theme.Tertiary3
import theme.Tertiary4
import theme.Tertiary5
import theme.Tertiary6
import theme.Tertiary7
import theme.Tertiary8
import theme.Tertiary9
import theme.TextWhite

@Composable
fun ItemChooseColour(colourIndex: MutableState<Int>, onChooseColour: (Int) -> Unit, ) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(162.dp)
            .background(LayerOne),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .height(120.dp)
                .clip(RoundedCornerShape(20.dp))
                .border(
                    1.dp, LayerThree, RoundedCornerShape(20.dp)
                )
                .background(LayerOne),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 28.dp, vertical = 0.dp)
                    .fillMaxWidth().height(42.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Box(
                    modifier = Modifier.fillMaxHeight().weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Spacer(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(AccentPrimaryTwo)
                            .clickable { colourIndex.value = 0
                                onChooseColour(0)}
                    )

                    Spacer(
                        modifier = Modifier
                            .size(22.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .border(BorderStroke(2.dp, if (colourIndex.value == 0)TextWhite else AccentPrimaryTwo ),RoundedCornerShape(8.dp))
                            .background(AccentPrimaryTwo)

                    )
                }

                Box(
                    modifier = Modifier.fillMaxHeight().weight(1f),
                    contentAlignment = Alignment.Center
                ) {

                    Spacer(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Tertiary1)
                            .clickable  { colourIndex.value = 1
                                onChooseColour(1)}
                    )

                    Spacer(
                        modifier = Modifier
                            .size(22.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .border(BorderStroke(2.dp, if (colourIndex.value == 1) TextWhite else Tertiary1 ),RoundedCornerShape(8.dp))
                            .background(Tertiary1)
                    )
                }
                Box(
                    modifier = Modifier.fillMaxHeight().weight(1f),
                    contentAlignment = Alignment.Center
                ) {

                    Spacer(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Tertiary2)
                            .clickable { colourIndex.value = 2
                                onChooseColour(2)}
                    )

                    Spacer(
                        modifier = Modifier
                            .size(22.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .border(BorderStroke(2.dp, if (colourIndex.value == 2) TextWhite else Tertiary2 ),RoundedCornerShape(8.dp))
                            .background(Tertiary2)
                    )
                }
                Box(
                    modifier = Modifier.fillMaxHeight().weight(1f),
                    contentAlignment = Alignment.Center
                ) {

                    Spacer(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Tertiary3)
                            .clickable  { colourIndex.value = 3
                                onChooseColour(3)}
                    )

                    Spacer(
                        modifier = Modifier
                            .size(22.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .border(BorderStroke(2.dp, if (colourIndex.value == 3)TextWhite else Tertiary3 ),RoundedCornerShape(8.dp))
                            .background(Tertiary3)
                    )
                }
                Box(
                    modifier = Modifier.fillMaxHeight().weight(1f),
                    contentAlignment = Alignment.Center
                ) {

                    Spacer(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Tertiary4)
                            .clickable { colourIndex.value = 4
                                onChooseColour(4)}
                    )

                    Spacer(
                        modifier = Modifier
                            .size(22.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .border(BorderStroke(2.dp, if (colourIndex.value == 4)TextWhite else Tertiary4 ),RoundedCornerShape(8.dp))
                            .background(Tertiary4)
                    )
                }

            }
            Row(
                modifier = Modifier.padding(horizontal = 28.dp, vertical = 6.dp)
                    .fillMaxWidth().height(42.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Box(
                    modifier = Modifier.fillMaxHeight().weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Spacer(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Tertiary5)
                            .clickable { colourIndex.value = 5
                                onChooseColour(5)}
                    )

                    Spacer(
                        modifier = Modifier
                            .size(22.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .border(BorderStroke(2.dp, if (colourIndex.value == 5)TextWhite else Tertiary5 ),RoundedCornerShape(8.dp))
                            .background(Tertiary5)
                    )
                }

                Box(
                    modifier = Modifier.fillMaxHeight().weight(1f),
                    contentAlignment = Alignment.Center
                ) {

                    Spacer(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Tertiary6)
                            .clickable { colourIndex.value = 6
                                onChooseColour(6)}
                    )

                    Spacer(
                        modifier = Modifier
                            .size(22.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .border(BorderStroke(2.dp, if (colourIndex.value == 6)TextWhite else Tertiary6 ),RoundedCornerShape(8.dp))
                            .background(Tertiary6)
                    )
                }
                Box(
                    modifier = Modifier.fillMaxHeight().weight(1f),
                    contentAlignment = Alignment.Center
                ) {

                    Spacer(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Tertiary7)
                            .clickable { colourIndex.value = 7
                                onChooseColour(7)}
                    )

                    Spacer(
                        modifier = Modifier
                            .size(22.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .border(BorderStroke(2.dp, if (colourIndex.value == 7)TextWhite else Tertiary7 ),RoundedCornerShape(8.dp))
                            .background(Tertiary7)
                    )
                }
                Box(
                    modifier = Modifier.fillMaxHeight().weight(1f),
                    contentAlignment = Alignment.Center
                ) {

                    Spacer(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Tertiary8)
                            .clickable { colourIndex.value = 8
                                onChooseColour(8)}
                    )

                    Spacer(
                        modifier = Modifier
                            .size(22.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .border(BorderStroke(2.dp, if (colourIndex.value == 8)TextWhite else Tertiary8 ),RoundedCornerShape(8.dp))
                            .background(Tertiary8)
                    )
                }
                Box(
                    modifier = Modifier.fillMaxHeight().weight(1f),
                    contentAlignment = Alignment.Center
                ) {

                    Spacer(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Tertiary9)
                            .clickable { colourIndex.value = 9
                                onChooseColour(9)}
                    )

                    Spacer(
                        modifier = Modifier
                            .size(22.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .border(BorderStroke(2.dp, if (colourIndex.value == 9)TextWhite else Tertiary9 ),RoundedCornerShape(8.dp))
                            .background(Tertiary9)
                    )
                }
            }
        }
    }
}