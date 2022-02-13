package sys.moeinwallet.pomodoro.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bathroom
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Tv
import androidx.compose.ui.graphics.vector.ImageVector


fun getIconByName(iconName: String): ImageVector = iconsMap[iconName]?:Icons.Default.Star

private fun getPairForIcon(imageVector: ImageVector): Pair<String, ImageVector> = Pair(imageVector.name, imageVector)

val iconsMap= mapOf(
    getPairForIcon(Icons.Default.Cake),
    getPairForIcon(Icons.Default.Bathroom),
    getPairForIcon(Icons.Default.Tv),
)



