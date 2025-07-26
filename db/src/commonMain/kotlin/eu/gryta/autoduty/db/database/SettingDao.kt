package eu.gryta.autoduty.db.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import eu.gryta.autoduty.db.schema.Setting
import eu.gryta.autoduty.db.schema.SettingKey

@Dao
interface SettingDao {
    @Query("SELECT * FROM `Setting` WHERE `key` = :key LIMIT 1")
    suspend fun getByKey(key: SettingKey): Setting?

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun upsert(setting: Setting)

    @Query("DELETE FROM `Setting` WHERE `key` = :key")
    suspend fun deleteByKey(key: SettingKey)
}