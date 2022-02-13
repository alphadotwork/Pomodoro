package sys.moeinwallet.pomodoro.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bathroom
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Tv
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import sys.moeinwallet.pomodoro.di.ApplicationScope
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Reward::class], version = 1)
abstract class ITDatabase : RoomDatabase() {

    abstract fun rewardsDao(): RewardsDao


    class CallBack @Inject constructor(
        private val database: Provider<ITDatabase>,
        @ApplicationScope private val coroutineScope: CoroutineScope,
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            coroutineScope.launch {
                val rewardsDao = database.get().rewardsDao()
                rewardsDao.insertReward(Reward(Icons.Default.Cake.name, "1 piece of cake ", 1))
                rewardsDao.insertReward(Reward(Icons.Default.Bathroom.name, "taking a bath ", 5))
                rewardsDao.insertReward(Reward(Icons.Default.Tv.name, "watch a film", 10))
            }
        }

    }
}