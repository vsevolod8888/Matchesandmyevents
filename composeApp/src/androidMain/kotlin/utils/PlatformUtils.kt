package utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.pm.ResolveInfo
import android.media.MediaPlayer
import android.net.Uri
import android.os.Vibrator
import android.provider.Settings
import android.widget.Toast
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import data.room.AppDatabasee
import kotlinx.coroutines.Dispatchers

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class PlatformUtils(private val context: Context) {
    actual fun canShowTermsOfUse(): Boolean = false


    @SuppressLint("QueryPermissionsNeeded")
    actual fun shareApp(title: String, link: String) {
        val appPackageName = context.packageName
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, "Share app")
        intent.putExtra(
            Intent.EXTRA_TEXT,
            "https://play.google.com/store/apps/details?id=$appPackageName"
        )
        val activities: List<ResolveInfo> =
            context.packageManager.queryIntentActivities(intent, 0)
        if (activities.isNotEmpty()) {
            val chooser = Intent.createChooser(intent, "Choose app")
            chooser.flags = FLAG_ACTIVITY_NEW_TASK
            context.startActivity(chooser)
        }
    }
    actual fun rateApp(title: String, link: String) {
        val packageName = context.packageName
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName"))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
            )
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }


    actual fun exitApp() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
        val activity = context as? Activity
        activity?.finishAffinity()
    }


    actual fun goToSettings(){
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            data = Uri.parse("package:${context.packageName}")
        }
        context.startActivity(intent)
    }
}
fun getDatabaseBuilder(ctx: Context): RoomDatabase.Builder<AppDatabasee> {
    val appContext = ctx.applicationContext
    val dbFile = appContext.getDatabasePath("my_room.db")
    return Room.databaseBuilder<AppDatabasee>(
        context = appContext,
        name = dbFile.absolutePath
    ).setDriver(BundledSQLiteDriver()).setQueryCoroutineContext(Dispatchers.IO)
}

fun getDatabase(ctx: Context): AppDatabasee {
    return getDatabaseBuilder(ctx).build()
}