package eu.gryta.autoduty

import android.app.Application


class App : Application() {

    init {
        instance = this
    }

    companion object {
        @JvmStatic
        lateinit var instance: App
            private set
    }
}