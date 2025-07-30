package utils

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class PlatformUtils {
    fun canShowTermsOfUse(): Boolean
    fun shareApp(title: String, link: String)
    fun rateApp(title: String, link: String)
    fun exitApp()
    fun goToSettings()
}