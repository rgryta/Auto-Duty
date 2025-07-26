package eu.gryta.autoduty.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import eu.gryta.autoduty.db.schema.Setting

@Database(entities = [Setting::class], version = 1)
abstract class SettingDatabase : RoomDatabase() {
    abstract fun getDao(): SettingDao

    companion object {
        const val FILENAME: String = "setting_database.db"
    }
}