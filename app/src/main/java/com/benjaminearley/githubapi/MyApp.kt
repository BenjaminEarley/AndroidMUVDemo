package com.benjaminearley.githubapi

import android.app.Application
import com.facebook.stetho.Stetho


class MyApp : Application() {

    companion object {
        lateinit var netComponent: NetComponent
            private set
    }

    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this)

        netComponent = DaggerNetComponent.builder()
                .netModule(NetModule("https://api.github.com"))
                .build()

    }
}
