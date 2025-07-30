package screens.settings

import data.MatchRepository
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import utils.PlatformUtils

const val SHARE_APP_ID = "6651841757"

class SettingsViewModel(
    private val repository: MatchRepository,
    private val platformUtils: PlatformUtils
) : ViewModel() {

    suspend fun deleteAllEvents() {
        repository.deleteAllEvents()
    }

    fun canShowTermsOfUse() = platformUtils.canShowTermsOfUse()

    fun shareApp() {
        platformUtils.shareApp("Share App", "https://apps.apple.com/app/id$SHARE_APP_ID")
    }


    fun rateApp() {
        platformUtils.rateApp("Rate App", "https://apps.apple.com/app/id$SHARE_APP_ID")
    }

    fun goToSettings() {
        platformUtils.goToSettings()
    }
    fun deleteAllChangesInMatches() {
        viewModelScope.launch {
            repository.saveIdListChangedMatches(emptyList())
            repository.deleteAllEvents()
            repository.deleteAllGamesInDatabase()

        }
    }
}