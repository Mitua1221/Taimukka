package com.arjental.taimukka.other.di

import android.content.Context
import androidx.room.Room
import com.arjental.taimukka.data.cash.Database
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class DatabaseModule {

    private val DATABASE_NAME: String = "TAIMUKKA_DATABASE"

    @Provides
    @Singleton
    fun provideFilerDatabases(
        context: Context
    ): Database = Room.databaseBuilder(context.applicationContext, Database::class.java, DATABASE_NAME)
        .addMigrations()
        .build()

}