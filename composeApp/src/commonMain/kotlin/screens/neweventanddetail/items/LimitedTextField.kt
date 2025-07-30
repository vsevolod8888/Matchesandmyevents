package screens.neweventanddetail.items

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import theme.TextStyleLocal
import theme.TextWhite

@Composable
fun LimitedTextField(
    textDescription: String,
    onTextChange: (String) -> Unit,
    maxNumbersEditTextText: Int
) {
    TextField(
        value = textDescription,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            cursorColor = TextWhite,
            focusedTextColor = TextWhite,
            unfocusedTextColor = TextWhite,
            disabledTextColor = TextWhite,
        ),
        textStyle = TextStyleLocal.regular16,
        onValueChange = {
            if (it.length <= maxNumbersEditTextText) {
                onTextChange(it)
            }
        },
        keyboardActions = KeyboardActions(
            onDone = {
            }
        ),
        minLines = 5,
        maxLines = 5,
        modifier = Modifier.fillMaxSize(),
        singleLine = false,
        placeholder = {
            Text(
                text = "Type the note here...",
                modifier = Modifier
                    .fillMaxHeight()
                    .wrapContentWidth()
                    .padding(start = 0.dp, top = 3.dp),
                color = TextWhite,
                style = TextStyleLocal.regular16,
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LimitedTextFieldDet(
    textDescription: String,
    onTextChange: (String) -> Unit,
    maxNumbersEditTextText: Int
) {
    var text by remember { mutableStateOf(textDescription) }
    LaunchedEffect(textDescription) {
        text = textDescription
    }
    TextField(
        value = text,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            cursorColor = TextWhite,
            focusedTextColor = TextWhite,
            unfocusedTextColor = TextWhite,
            disabledTextColor = TextWhite,
        ),
        textStyle = TextStyleLocal.regular16,
        onValueChange = {
            if (it.length <= maxNumbersEditTextText) {
                text = it
                onTextChange(it)
            }
        },
        keyboardActions = KeyboardActions(
            onDone = {
            }
        ),
        minLines = 5,
        maxLines = 5,
        modifier = Modifier.fillMaxSize(),
        singleLine = false,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text
        )
    )
}