package di

import com.liftric.kvault.KVault
import data.room.AppDatabasee
import getDatabase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import utils.PlatformUtils

actual fun platformModule() = module {
    single { KVault() }
    single<AppDatabasee> { getDatabase() }
    singleOf(::PlatformUtils)
}