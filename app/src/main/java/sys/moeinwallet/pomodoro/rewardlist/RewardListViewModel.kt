package sys.moeinwallet.pomodoro.rewardlist

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import sys.moeinwallet.pomodoro.data.Reward
import javax.inject.Inject

@HiltViewModel
class RewardListViewModel @Inject constructor() : ViewModel() {

    private val _dummyRewardsLiveData = MutableLiveData<List<Reward>>(listOf())
    val dummyRewards: LiveData<List<Reward>> = _dummyRewardsLiveData

    init {

        viewModelScope.launch {
delay(1000)
            val dummyRewards = mutableListOf<Reward>()
            repeat(100) { index ->
                dummyRewards += Reward(icon = Icons.Default.Star,
                    title = "title $index",
                    chanceInPercent = index)
            }
            _dummyRewardsLiveData.value = dummyRewards


        }
    }

}