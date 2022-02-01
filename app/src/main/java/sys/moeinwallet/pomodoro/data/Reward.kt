package sys.moeinwallet.pomodoro.data

import androidx.compose.ui.graphics.vector.ImageVector

data class Reward(
    val icon: ImageVector,
    val title: String, val chanceInPercent: Int,
)