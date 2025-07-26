package eu.gryta.autoduty.db

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import eu.gryta.autoduty.db.database.SettingDatabase
import kotlinx.coroutines.Dispatchers

expect object DatabaseContext {
    fun getSettingDatabaseBuilder(): RoomDatabase.Builder<SettingDatabase>
}


object Databases {
    fun getSettingDatabase(): SettingDatabase {
        return DatabaseContext.getSettingDatabaseBuilder()
            .fallbackToDestructiveMigrationOnDowngrade(true)
            .fallbackToDestructiveMigration(true)
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }
}