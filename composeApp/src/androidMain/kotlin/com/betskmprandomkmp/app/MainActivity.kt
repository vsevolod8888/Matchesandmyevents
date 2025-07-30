package com.betskmprandomkmp.app

import App
import android.app.Activity
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import theme.BackgroundMain
import theme.LayerOne
import theme.LayerThree
import theme.LayerTwo


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
//        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContent {


            val context = LocalContext.current
            SideEffect {
                val window = (context as Activity).window
                window.statusBarColor = LayerOne.toArgb()
            }
            App()
        }
    }
}