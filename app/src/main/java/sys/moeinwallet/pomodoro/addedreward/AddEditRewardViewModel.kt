package sys.moeinwallet.pomodoro.addedreward

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import sys.moeinwallet.pomodoro.data.Reward
import sys.moeinwallet.pomodoro.data.RewardsDao
import javax.inject.Inject

@HiltViewModel
class AddEditRewardViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
   val rewardsDao: RewardsDao
) : ViewModel() {


    private val eventChannel = Channel<AddEditRewardEvent>()
    val event = eventChannel.receiveAsFlow()

    private val rewardId = savedStateHandle.get<Long>(ARG_REWARD_ID)

    val isEditMode = rewardId != null

    private val rewardNameInputLiveData: MutableLiveData<String> =
        savedStateHandle.getLiveData("rewardNameInputLiveData")
    val rewardNameInput: LiveData<String> = rewardNameInputLiveData

    private val chanceInPercentInputLiveData: MutableLiveData<Int> =
        savedStateHandle.getLiveData("chanceInPercentLiveData")
    val chanceInPercentInput: LiveData<Int> = chanceInPercentInputLiveData

    fun onRewardNameInputChanged(input: String) {
        rewardNameInputLiveData.value = input
    }


    fun onChanceInPercentChanged(chanceInPercent: Int) {
        chanceInPercentInputLiveData.value = chanceInPercent
    }

    fun onSaveClicked() {
        val rewardNameInput = rewardNameInput.value
        val chanceInPercentInput = chanceInPercentInput.value
        viewModelScope.launch {
            if (rewardNameInput != null && chanceInPercentInput != null && rewardNameInput.isNotBlank()) {
                if (rewardId != null) {
                    //updateReward()
                } else createReward(
                    Reward(
                        iconName = Icons.Default.Star.name,
                        title = rewardNameInput,
                        chanceInPercent = chanceInPercentInput,
                        isUnlocked = false,
                        pk = 0)
                )
            } else {
            }
        }

    }

    private suspend fun createReward(reward: Reward) {
        rewardsDao.insertReward(reward = reward)
        eventChannel.send(AddEditRewardEvent.RewardCreated)
    }

    private suspend fun updateReward(reward: Reward) {
        rewardsDao.insertReward(reward = reward)
    }


    sealed class AddEditRewardEvent{
        object RewardCreated:AddEditRewardEvent()
    }

}




const val ARG_REWARD_ID = "ARG_REWARD_ID"


