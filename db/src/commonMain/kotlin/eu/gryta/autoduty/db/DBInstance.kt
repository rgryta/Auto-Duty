package eu.gryta.autoduty.db

import eu.gryta.autoduty.db.database.SettingDao
import eu.gryta.autoduty.db.database.SettingDatabase

class RoomInstance {
    var settingDatabase: SettingDatabase? = null

    val dao: SettingDao
        get() {
            return settingDatabase!!.getDao()
        }

    init {
        instance = this

        settingDatabase = Databases.getSettingDatabase()
    }

    fun close() {
        settingDatabase?.close()
    }

    companion object {
        @JvmStatic
        lateinit var instance: RoomInstance
            private set
    }
}