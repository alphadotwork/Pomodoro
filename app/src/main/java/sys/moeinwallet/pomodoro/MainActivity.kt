package sys.moeinwallet.pomodoro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import sys.moeinwallet.pomodoro.rewardlist.RewardListScreen
import sys.moeinwallet.pomodoro.timer.TimerScreen
import sys.moeinwallet.pomodoro.ui.theme.PomodoroTheme

@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PomodoroTheme {
                ScreenContent()
            }
        }
    }
}

//poke and chill
@OptIn(ExperimentalAnimationApi::class)
@ExperimentalMaterialApi
@Composable
fun ScreenContent() {
    val navController = rememberNavController()

    Scaffold(bottomBar = {
        BottomNavigation {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            bottomNavDestinations.forEach { bottomNavigationItem ->
                BottomNavigationItem(
                    label = {
                        Text(text = stringResource(id = bottomNavigationItem.label))
                    },
                    selected = currentDestination?.hierarchy?.any {
                        bottomNavigationItem.route == it.route
                    } == true,
                    onClick = {
                        navController.navigate(bottomNavigationItem.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    alwaysShowLabel = false,
                    icon = {
                        Icon(imageVector = bottomNavigationItem.icon,
                            contentDescription = "")
                    },

                    )
            }

        }
    }) { innerPadding ->

        NavHost(navController = navController,
            startDestination = bottomNavDestinations[0].route,
            modifier = Modifier.padding(innerPadding),
        )
        {
            composable("timer") { TimerScreen() }
            composable("rewardList") { RewardListScreen() }
        }
    }


}

val bottomNavDestinations = listOf<BottomNavDestination>(
    BottomNavDestination.Timer,
    BottomNavDestination.RewardList,
)


sealed class BottomNavDestination(
    val route: String,
    val icon: ImageVector,
    @StringRes val label: Int,
) {

    object Timer : BottomNavDestination("timer", Icons.Outlined.Timer, R.string.timer)
    object RewardList :
        BottomNavDestination("rewardList", Icons.Outlined.List, R.string.reward_list)

}


@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PomodoroTheme {
        ScreenContent()
    }
}
