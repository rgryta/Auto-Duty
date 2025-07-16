package eu.gryta.autoduty

import android.app.Application
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

    init {
        instance = this
        launch(this.coroutineContext) {
            ApiInstance.getInstance().token = ""
        }
    }

    companion object {
        @JvmStatic
        lateinit var instance: App
            private set
    }
}