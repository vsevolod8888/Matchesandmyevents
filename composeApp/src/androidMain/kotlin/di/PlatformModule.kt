package di

import com.liftric.kvault.KVault
import data.room.AppDatabasee
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import utils.PlatformUtils
import utils.getDatabase

actual fun platformModule() = module {
    single { KVault(get()) }
    single<AppDatabasee> { getDatabase(get()) }
    singleOf(::PlatformUtils)
}