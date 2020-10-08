package com.akameko.testforipartner.dagger

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Simple Dagger Module class. Provides [App].
 */
@Module
class AppModule(private var application: App) {
    @Provides
    @Singleton
    fun providesApplication(): App {
        return application
    }
}