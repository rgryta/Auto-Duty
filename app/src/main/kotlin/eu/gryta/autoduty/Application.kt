package eu.gryta.autoduty

import android.app.Application
import eu.gryta.autoduty.db.DatabaseContext
import eu.gryta.autoduty.db.RoomInstance
import eu.gryta.autoduty.db.schema.Setting
import eu.gryta.autoduty.db.schema.SettingKey
import eu.gryta.ktor.utils.ApiInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class App : Application(), CoroutineScope {
    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job


    suspend fun applyToken(token: String) {
        RoomInstance.instance.dao.upsert(
            Setting(
                key = SettingKey.API_TOKEN,
                value = token,
            )
        )
        ApiInstance.getInstance().token = "Token token=${token}"
    }

    init {
        instance = this

    }

    override fun onCreate() {
        super.onCreate()

        DatabaseContext.applicationContext = this
        RoomInstance()

        launch(this.coroutineContext) {
            ApiInstance.getInstance().token = null
        }
    }

    companion object {
        @JvmStatic
        lateinit var instance: App
            private set
    }
}