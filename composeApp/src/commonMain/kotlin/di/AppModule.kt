package di
import data.MatchRepository
import data.impl.MatchRepositoryImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import data.preferences.DataPreferencesStore
import httpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.factoryOf
import screens.onBoarding.OnBoardingViewModel
import data.MatchSource
import screens.settings.SettingsViewModel
import screens.leagues.LeaguesViewModel
import screens.main.MainViewModel
import screens.detail.MatchDetailViewModel
import screens.neweventanddetail.NewEventViewModel
import screens.neweventanddetail.DetailEventViewModel

val appModule = module {
    singleOf(::DataPreferencesStore)
    singleOf(::MatchSource)
    single<MatchRepository>(createdAtStart = true) { MatchRepositoryImpl(prefStore = get(), appDatabase = get(), matchSource = get()) }
    single {
        httpClient {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
            }
        }
    }

    factoryOf(::OnBoardingViewModel)
    factoryOf(::SettingsViewModel)
    factoryOf(::LeaguesViewModel)
    factoryOf(::MainViewModel)
    factoryOf(::MatchDetailViewModel)
    factoryOf(::NewEventViewModel)
    factoryOf(::DetailEventViewModel)
}

