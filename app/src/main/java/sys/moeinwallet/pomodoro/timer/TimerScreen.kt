package sys.moeinwallet.pomodoro.timer

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import sys.moeinwallet.pomodoro.R
import sys.moeinwallet.pomodoro.ui.theme.PomodoroTheme


@Composable
fun TimerScreen() {
    ScreenContent()
}

@Composable
private fun ScreenContent() {

    Box(modifier = Modifier
        .fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = stringResource(id = R.string.timer))
    }
}


@Preview(name = "Night Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun ScreenContentPreview() {

    PomodoroTheme {
        Surface {
            ScreenContent()
        }
    }
}
