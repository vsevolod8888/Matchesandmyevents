sealed class AppRoute(val route: String) {
    data object Loading : AppRoute("Loading")
    data object OnBoarding : AppRoute("OnBoarding")

    data object Leagues : AppRoute("Leagues")
    data object Settings : AppRoute("Settings")

    data object Main : AppRoute("Main")
    data object NewEvent : AppRoute("New")

    data object Detail : AppRoute("Detail")
    data object DetailEvent : AppRoute("DetailEvent")

    data object PrivacyPolicy : AppRoute("Privacy Policy")
    data object TermsOfUse : AppRoute("Terms of Use")
}