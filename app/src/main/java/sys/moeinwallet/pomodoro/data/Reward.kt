package sys.moeinwallet.pomodoro.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rewards")
data class Reward(
    val iconName: String,
    val title: String,
    val chanceInPercent: Int,
    val isUnlocked:Boolean=false,
    @PrimaryKey(autoGenerate = true) val pk: Long = 0,
)