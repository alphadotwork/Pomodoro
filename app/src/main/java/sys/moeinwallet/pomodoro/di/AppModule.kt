package sys.moeinwallet.pomodoro.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import sys.moeinwallet.pomodoro.data.ITDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(
        app: Application,
        callback: ITDatabase.CallBack,
    ): ITDatabase =
        Room.databaseBuilder(app, ITDatabase::class.java, "pomodoro_database")
            .addCallback(callback)
            .build()


    @Provides
    fun providesDao(itDatabase: ITDatabase) = itDatabase.rewardsDao()


    @ApplicationScope
    @Singleton
    @Provides
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())


}