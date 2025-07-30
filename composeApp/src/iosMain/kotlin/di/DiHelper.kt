package di

import org.koin.core.context.startKoin

object DiHelper {
    fun startKoinIos() {
        startKoin {
            modules(appModule, platformModule())
        }
    }
}