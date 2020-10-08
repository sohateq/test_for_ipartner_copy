package com.akameko.testforipartner.dagger

import com.akameko.testforipartner.repository.retrofit.Repository
import com.akameko.testforipartner.ui.SharedViewModel
import dagger.Component
import javax.inject.Singleton

/**
 * Simple Dagger Component class.
 */
@Singleton
@Component(dependencies = [], modules = [AppModule::class, RepositoryModule::class])
interface AppComponent {
    fun injectSharedViewModel(sharedViewModel: SharedViewModel)

    val repository: Repository

    val application: App
}