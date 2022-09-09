package com.tian.counterta

import android.content.Context
import androidx.room.Room
import com.tian.counterta.data.TripDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {



    @Singleton
    @Provides
    fun provideRoomInstance(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        TripDatabase::class.java,
        "trip-db"
    ).fallbackToDestructiveMigration().build()

    @Binds
    @Singleton
    abstract fun bindCommonRepository(
        commonRepositoryImpl: TripRepository
    ): CommonRepository

    @Singleton
    @Provides
    fun provideAppDao(db: TripDatabase) = db.tripDao()

}