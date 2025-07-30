package utils

import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFAudio.AVAudioPlayer
import platform.CoreGraphics.CGRectGetMidX
import platform.CoreGraphics.CGRectGetMidY
import platform.CoreGraphics.CGRectMake
import platform.Foundation.NSBundle
import platform.Foundation.NSURL
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication
import platform.UIKit.endEditing
import platform.UIKit.popoverPresentationController
import platform.AudioToolbox.AudioServicesPlaySystemSound
import platform.AudioToolbox.kSystemSoundID_Vibrate
import platform.UIKit.NSTextAlignmentCenter
import platform.UIKit.UILabel
import platform.UIKit.UIView

import platform.UIKit.UIColor
import platform.UIKit.UIFont
import platform.UIKit.UIViewAnimationOptionCurveEaseOut
import platform.UIKit.UIViewAutoresizingFlexibleWidth
import platform.UIKit.UIViewAutoresizingFlexibleHeight



@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class PlatformUtils {
    actual fun canShowTermsOfUse(): Boolean = true

    actual fun rateApp(title: String, link: String) {
        NSURL.URLWithString(link)?.let { urlHere ->
            UIApplication.sharedApplication.openURL(urlHere)
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    actual fun shareApp(title: String, link: String) {
        val viewController = UIApplication.sharedApplication().keyWindow()?.rootViewController
        if (viewController != null) {
            val uiActivityViewController = UIActivityViewController(
                activityItems = listOf(title, link),
                applicationActivities = null
            )
            viewController.presentViewController(uiActivityViewController, true, null)
            uiActivityViewController.popoverPresentationController()?.apply {
                sourceView = viewController.view
                sourceRect = CGRectMake(
                    CGRectGetMidX(viewController.view.bounds),
                    CGRectGetMidY(viewController.view.bounds),
                    0.0,
                    0.0
                )
                permittedArrowDirections = 0u
            }
        }
    }

    actual fun exitApp() {
        exitApp()
    }



    actual fun goToSettings(){
        val url = NSURL(string = "App-Prefs:root=")
        if (UIApplication.sharedApplication.canOpenURL(url)) {
            UIApplication.sharedApplication.openURL(url)
        }
    }
}
