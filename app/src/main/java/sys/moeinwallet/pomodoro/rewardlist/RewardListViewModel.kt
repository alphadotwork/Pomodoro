package sys.moeinwallet.pomodoro.rewardlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import sys.moeinwallet.pomodoro.data.Reward
import sys.moeinwallet.pomodoro.data.RewardsDao
import javax.inject.Inject

@HiltViewModel
class RewardListViewModel @Inject constructor(
    private val rewardsDao: RewardsDao,
) : ViewModel() {

    val dummyRewards: LiveData<List<Reward>> = rewardsDao.getAllRewards().asLiveData()





}