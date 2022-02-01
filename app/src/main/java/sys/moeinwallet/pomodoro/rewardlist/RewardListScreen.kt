package sys.moeinwallet.pomodoro.rewardlist

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import sys.moeinwallet.pomodoro.R
import sys.moeinwallet.pomodoro.data.Reward
import sys.moeinwallet.pomodoro.ui.ListBottomPadding
import sys.moeinwallet.pomodoro.ui.theme.PomodoroTheme


@ExperimentalAnimationApi
@Composable
@ExperimentalMaterialApi
fun RewardListScreen() {
    val viewModel: RewardListViewModel = hiltViewModel()
    val dummyRewards: List<Reward>? by viewModel.dummyRewards.observeAsState()
    ScreenContent(dummyRewards)
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
private fun ScreenContent(dummyRewards: List<Reward>?) {

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {

                },
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.add_new_reward))
            }
        },
        topBar = {
            TopAppBar(title = {
        Text(text = stringResource(id = R.string.reward_list))
            })
        },
    ) {
        val listState = rememberLazyListState()

        val coroutineScope = rememberCoroutineScope()

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = stringResource(id = R.string.reward_list))
            LazyColumn(contentPadding = PaddingValues(top = 8.dp,
                start = 8.dp,
                end = 8.dp,
                bottom = ListBottomPadding), state = listState) {
                items(dummyRewards!!) {
                    RewardItem(reward = it)
                }
            }

            AnimatedVisibility(
                visible = listState.firstVisibleItemIndex > 5,
                modifier = Modifier
                    .align(Alignment.BottomCenter),
                content = {
                    FloatingActionButton(onClick = {
                        coroutineScope.launch {
                            listState.animateScrollToItem(0)
                        }
                    }, modifier = Modifier.padding(32.dp)) {
                        Icon(imageVector = Icons.Default.ExpandLess,
                            contentDescription = stringResource(id = R.string.show_top))
                    }
                },
                enter = fadeIn(),
                exit = fadeOut()
            )


        }

    }
}

@ExperimentalMaterialApi
@Composable
private fun RewardItem(
    modifier: Modifier = Modifier, reward: Reward,
) {

    Card(modifier = modifier
        .fillMaxWidth()
        .padding(8.dp), onClick = {}) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(reward.icon, null, modifier = Modifier
                .padding(8.dp)
                .size(48.dp)
                .fillMaxWidth())
            Column {
                Text(text = reward.title,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.h6)
                Text(text = "${reward.chanceInPercent}%",
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Gray)
            }
        }
    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun RewardItemPreview() {
    PomodoroTheme {
        Surface {
            RewardItem(reward = Reward(icon = Icons.Default.Star,
                title = "title",
                chanceInPercent = 5))
        }
    }
}


@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Preview(name = "Dark Mode", uiMode = UI_MODE_NIGHT_YES)
@Preview(name = "Light Mode", uiMode = UI_MODE_NIGHT_NO)
@Composable
private fun ScreenContentPreview() {

    PomodoroTheme {
        Surface {
            ScreenContent(listOf(
                Reward(icon = Icons.Default.Star,
                    title = "title $1",
                    chanceInPercent = 1),
                Reward(icon = Icons.Default.Star,
                    title = "title $1",
                    chanceInPercent = 1),
                Reward(icon = Icons.Default.Star,
                    title = "title $1",
                    chanceInPercent = 1),
            ))
        }
    }
}
