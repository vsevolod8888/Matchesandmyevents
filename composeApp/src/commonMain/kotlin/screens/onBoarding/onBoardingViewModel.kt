package screens.onBoarding

import data.MatchRepository
import moe.tlaster.precompose.viewmodel.ViewModel
import utils.PlatformUtils

class OnBoardingViewModel (private val repository: MatchRepository,
                           private val platformUtils: PlatformUtils
) : ViewModel() {

    fun setFirstLaunch(b:Int){
        repository.saveFirstLaunch(b)
    }
    fun getFirstLaunch():Int = repository.getFirstLaunch()
    fun exitApp() {
        platformUtils.exitApp()
    }

    fun canShow() = platformUtils.canShowTermsOfUse()
}