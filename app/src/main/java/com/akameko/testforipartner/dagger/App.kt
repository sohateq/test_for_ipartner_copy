package com.akameko.testforipartner.dagger

import android.app.Application

/**
 * Simple App class
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .repositoryModule(RepositoryModule())
                .build()

    }

    companion object {
        lateinit var component: AppComponent
            private set
    }
}