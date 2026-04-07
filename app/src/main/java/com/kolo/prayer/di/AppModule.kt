package com.kolo.prayer.di

import android.content.Context
import com.kolo.prayer.data.repository.BookRepository
import com.kolo.prayer.data.repository.PreferencesRepository
import com.kolo.prayer.data.source.AssetDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    // BookRepository is now provided via @Inject constructor


    @Provides
    @Singleton
    fun providePreferencesRepository(
        @ApplicationContext context: Context,
    ): PreferencesRepository = PreferencesRepository(context)
}
