import androidx.room.Room
import androidx.room.RoomDatabase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import platform.Foundation.NSHomeDirectory
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import data.room.AppDatabasee
import data.room.instantiateImpl
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask


fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabasee> {
    //val dbFilePath = NSHomeDirectory() + "/my_room.db"
    val dbFilePath = "${fileDirectory()}/my_room.db"
    return Room.databaseBuilder<AppDatabasee>(
        name = dbFilePath,
        factory =  {AppDatabasee::class.instantiateImpl() }
    )
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)

}

fun getDatabase(): AppDatabasee {
    return getDatabaseBuilder().build()
}
@OptIn(ExperimentalForeignApi::class)
private fun fileDirectory(): String {
    val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory).path!!
}