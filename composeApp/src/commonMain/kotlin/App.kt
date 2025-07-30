import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.PopUpTo
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import screens.detail.MatchesDetailScreen
import screens.leagues.LeaguesScreen
import screens.loading.LoadingScreen
import screens.main.MainScreen
import screens.neweventanddetail.DetailEventScreen
import screens.neweventanddetail.NewEventScreen
import screens.onBoarding.OnBoardingScreen
import screens.onBoarding.OnBoardingViewModel
import screens.privacy.PrivacyPolicyScreen
import screens.settings.SettingsScreen
import screens.termsofuse.TermOfUseScreen
import theme.AppTheme

@Composable
@Preview
fun App() {
    AppTheme {
        PreComposeApp {
            Scaffold {
                val navigator = rememberNavigator()
                NavHost(navigator = navigator,
                    initialRoute = AppRoute.Loading.route,
                    navTransition = remember {
                        NavTransition(
                            createTransition = fadeIn(),
                            destroyTransition = fadeOut(),
                            pauseTransition = fadeOut(),
                            resumeTransition = fadeIn(),
                        )
                    }) {
                    scene(AppRoute.Loading.route) {
                        LoadingScreen(goToOnboarding = {
                            navigator.navigate(
                                AppRoute.OnBoarding.route,
                                NavOptions(launchSingleTop = true, popUpTo = PopUpTo.First())
                            )
                        })
                    }

                    scene(AppRoute.OnBoarding.route) {
                        val viewModel: OnBoardingViewModel =
                            koinViewModel(OnBoardingViewModel::class)

                        OnBoardingScreen(onClickNext = {
                            navigator.navigate(
                                AppRoute.Leagues.route,
                                NavOptions(
                                    launchSingleTop = true,
                                    popUpTo = if (viewModel.getFirstLaunch() > 1) PopUpTo.First() else PopUpTo.None
                                )
                            )
                        })
                    }
                    scene(AppRoute.Leagues.route) {
                        LeaguesScreen(
                            onClickBackFromSelectLeagues = navigator::popBackStack,

                            onClickNextToMatchesScreen = {
                                navigator.navigate(
                                    AppRoute.Main.route, NavOptions(launchSingleTop = true)
                                )
                            }
                        )
                    }
                    scene(AppRoute.Main.route) {
                        MainScreen(
                            goToSettings = {
                                navigator.navigate(
                                    AppRoute.Settings.route,
                                    NavOptions(launchSingleTop = true)
                                )
                            },
                            goToDetailMatch = {
                                navigator.navigate(
                                    AppRoute.Detail.route,
                                    NavOptions(launchSingleTop = true)
                                )
                            },
                            goToNewEvent = {
                                navigator.navigate(
                                    AppRoute.NewEvent.route,
                                    NavOptions(launchSingleTop = true)
                                )
                            },
                            goToDetailEvent = {
                                navigator.navigate(
                                    AppRoute.DetailEvent.route,
                                    NavOptions(launchSingleTop = true)
                                )
                            },


                            )
                    }
                    scene(AppRoute.NewEvent.route) {
                        NewEventScreen(
                            onClickBackFromNewEvent =
                            {
                                navigator.navigate(
                                    AppRoute.Main.route,
                                    NavOptions(launchSingleTop = true)
                                )
                            }

                        )
                    }
                    scene(AppRoute.Detail.route) {
                        MatchesDetailScreen(
                            onClickBackFromDetail =
                            navigator::popBackStack,
                        )
                    }
                    scene(AppRoute.DetailEvent.route) {
                        DetailEventScreen(
                            onClickBackFromDetailEvent =
                            navigator::popBackStack,
                        )
                    }
                    scene(AppRoute.Settings.route) {
                        SettingsScreen(
                            goToPolicy = {
                                navigator.navigate(
                                    AppRoute.PrivacyPolicy.route,
                                    NavOptions(launchSingleTop = true)
                                )
                            },
                            goToTermOfUse = {
                                navigator.navigate(
                                    AppRoute.TermsOfUse.route,
                                    NavOptions(launchSingleTop = true)
                                )
                            },
                            onClickBackFromSettings =
                            navigator::popBackStack,
                            goToFavouriteLeagues = {
                                navigator.navigate(
                                    AppRoute.Leagues.route,
                                    NavOptions(launchSingleTop = true)
                                )
                            }
                        )
                    }

                    scene(AppRoute.PrivacyPolicy.route) {
                        PrivacyPolicyScreen(
                            onClickBackFromPolicy = navigator::popBackStack,
                        )
                    }
                    scene(AppRoute.TermsOfUse.route) {
                        TermOfUseScreen(
                            onClickBackFromTermsOfUse = navigator::popBackStack,
                        )
                    }


                }
            }
        }
    }
}































