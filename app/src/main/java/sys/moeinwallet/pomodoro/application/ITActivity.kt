package sys.moeinwallet.pomodoro.application

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.runtime.*
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
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import sys.moeinwallet.pomodoro.R
import sys.moeinwallet.pomodoro.addedreward.AddEditRewardScreen
import sys.moeinwallet.pomodoro.rewardlist.RewardListScreen
import sys.moeinwallet.pomodoro.timer.TimerScreen
import sys.moeinwallet.pomodoro.ui.theme.PomodoroTheme
import javax.inject.Qualifier

var a = mutableStateOf(1)

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //a22.test()
        setContent {
            PomodoroTheme {

                //Text(text = "Aa")

                ScreenContent()

                //LaunchEffectStuffs()

                //TestDispatcherEffect()

            }
        }
    }

    @Composable
    fun TestRememberCoroutineScope() {
        val rememberCoroutineScope = rememberCoroutineScope()


    }


    @Composable
    fun TestDispatcherEffect() {
        DisposableEffect(key1 = Unit, effect = {
            onDispose {
                println("on dispose")
            }
        })

        Text(text = "AA")
    }


    @Composable
    private fun LaunchEffectStuffs() {
        Column {

            Button(onClick = { a.value++ }) {
                Text(text = "AA")
            }

            TestLaunchedEffect()
        }
    }
}


@Composable
fun TestLaunchedEffect() {
    println("out")

    val (b, setB) = remember {
        mutableStateOf(0)
    }

    LaunchedEffect(key1 = "Unit$a", block = {
        println("in")
    })

    Button(onClick = { setB(b + 1) }) {
        Text(text = "${a.value}")
    }
}


@Composable
fun TestSideEffect() {

    println("out")

    val (a, setA) = remember {
        mutableStateOf(0)
    }

    var b by remember {
        mutableStateOf(false)
    }

    SideEffect {
        b = a < 2
    }

    Button(onClick = { setA(a + 1) }, enabled = b) {
        Text(text = "$a")
    }
}

//poke and chill
@ExperimentalAnimationApi
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

        NavHost(
            navController = navController,
            startDestination = bottomNavDestinations[0].route,
            modifier = Modifier.padding(innerPadding),
        )
        {
            composable(BottomNavDestination.TimerScreen.route) { TimerScreen() }
            composable(BottomNavDestination.RewardListScreen.route) { RewardListScreen(navController) }
            composable(FullScreenDestinations.AddEditRewardScreen.route) {
                AddEditRewardScreen(navController)
            }
        }
    }


}

val bottomNavDestinations = listOf<BottomNavDestination>(
    BottomNavDestination.TimerScreen,
    BottomNavDestination.RewardListScreen,
)


sealed class BottomNavDestination(
    val route: String,
    val icon: ImageVector,
    @StringRes val label: Int,
) {

    object TimerScreen : BottomNavDestination("timer", Icons.Outlined.Timer, R.string.timer)
    object RewardListScreen :
        BottomNavDestination("rewardList", Icons.Outlined.List, R.string.reward_list)

}


sealed class FullScreenDestinations(val route: String) {
    object AddEditRewardScreen : FullScreenDestinations(route = "addEditRewardScreen")
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PomodoroTheme {
        ScreenContent()
    }
}


class A (val type: Int,c:Context) {
    fun test() {
        println("testA$type")
    }
}

@Module
@InstallIn(SingletonComponent::class)
class M {

    @AFromM
    @Provides
    fun provideA(@ApplicationContext c:Context): A = A(1,c)

    @AFromN
    @Provides
    fun provideA2(@ApplicationContext c:Context): A = A(2,c)
}



@Qualifier
annotation class AFromM

@Qualifier
annotation class AFromN
