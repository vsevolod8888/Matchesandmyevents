package screens.commonelements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import theme.LayerTwo
import theme.TextStyleLocal
import theme.TextWhite


@OptIn(ExperimentalResourceApi::class)
@Composable
fun PopUpPin(titleText: String){
    Row(
        modifier = Modifier.padding(horizontal = 16.dp).padding(bottom = 18.dp)
            .fillMaxWidth().height(68.dp).clip(RoundedCornerShape(20.dp)).background(LayerTwo),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 26.dp),
            text = titleText,
            color = TextWhite,
            style = TextStyleLocal.semibold14
        )
    }
}