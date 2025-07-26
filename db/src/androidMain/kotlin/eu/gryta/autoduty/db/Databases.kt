package eu.gryta.autoduty.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import eu.gryta.autoduty.db.database.SettingDatabase

actual object DatabaseContext {

    lateinit var applicationContext: Context

    actual fun getSettingDatabaseBuilder(): RoomDatabase.Builder<SettingDatabase> {
        val appContext = applicationContext
        val dbFile = appContext.getDatabasePath(SettingDatabase.FILENAME)
        return Room.databaseBuilder<SettingDatabase>(
            context = appContext,
            name = dbFile.absolutePath
        )
    }
}