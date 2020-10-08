package com.akameko.testforipartner.dagger

import com.akameko.testforipartner.repository.retrofit.Repository
import dagger.Module
import dagger.Provides

/**
 * Simple Dagger Module class. Provides [Repository].
 */
@Module
class RepositoryModule {
    @Provides
    fun provideRepository(): Repository {
        return Repository()
    }
}