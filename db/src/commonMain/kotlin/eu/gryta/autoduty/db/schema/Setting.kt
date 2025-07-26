package eu.gryta.autoduty.db.schema

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Setting(
    @PrimaryKey val key: SettingKey,
    val value: String?
)