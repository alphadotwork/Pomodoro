package sys.moeinwallet.pomodoro.addedreward

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collect
import sys.moeinwallet.pomodoro.R
import sys.moeinwallet.pomodoro.ui.theme.PomodoroTheme

@Composable
fun AddEditRewardScreen(navController: NavController) {
    val viewModel: AddEditRewardViewModel = hiltViewModel()
    val rewardNameInput by viewModel.rewardNameInput.observeAsState("")
    val chanceInPercent by viewModel.chanceInPercentInput.observeAsState(10)
    val isEditMode = viewModel.isEditMode


    LaunchedEffect(Unit){
         viewModel.event.collect {event->
             when(event){
                 AddEditRewardViewModel.AddEditRewardEvent.RewardCreated ->
                     navController.popBackStack()
             }
         }
    }
    
    ScreenContent(
        isEditMode = isEditMode,
        rewardNameInput = rewardNameInput,
        chanceInPercent = chanceInPercent,
        onRewardNameInputChanged = viewModel::onRewardNameInputChanged,
        onChanceInputChanged = viewModel::onChanceInPercentChanged,
        onCloseClicked = navController::popBackStack,
        onSaveClicked = viewModel::onSaveClicked,
        )

}


@Composable
private fun ScreenContent(
    isEditMode: Boolean,
    rewardNameInput: String,
    chanceInPercent: Int,
    onRewardNameInputChanged: (String) -> Unit,
    onChanceInputChanged: (Int) -> Unit,
    onCloseClicked: () -> Unit,
    onSaveClicked: () -> Unit,
) {
    Scaffold(
        topBar = {
            val appbarTitle = if (isEditMode) R.string.edit_reward else R.string.add_reward
            TopAppBar(
                title = {
                    Text(text = stringResource(appbarTitle))
                }, navigationIcon = {
                    IconButton(onClick = onCloseClicked) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = stringResource(
                            id = R.string.close))
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onSaveClicked, modifier = Modifier.padding(16.dp)) {
                Icon(imageVector = Icons.Default.Check,
                    contentDescription = stringResource(id = R.string.save_reward))
            }
        }
    ) {

        Column(modifier = Modifier.padding(16.dp)) {
            TextField(
                value = rewardNameInput,
                onValueChange = onRewardNameInputChanged,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = stringResource(id = R.string.reward_name)) },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = stringResource(id = R.string.chance) + ":$chanceInPercent%")
            Slider(
                value = chanceInPercent.toFloat().div(100),
                onValueChange = { chanceAsFloat ->
                    onChanceInputChanged(chanceAsFloat.times(100).toInt())
                }
            )

        }
    }

}

@Preview(name = "dark mode", uiMode = UI_MODE_NIGHT_YES)
@Preview(name = "light mode", uiMode = UI_MODE_NIGHT_NO)
@Composable
private fun ScreenContentPreview() {
    PomodoroTheme {
        Surface {
            ScreenContent(
                isEditMode = false,
                rewardNameInput = "",
                10,
                onRewardNameInputChanged = {},
                onChanceInputChanged = {},
                onCloseClicked = {},
                onSaveClicked = {},
            )
        }
    }
}

