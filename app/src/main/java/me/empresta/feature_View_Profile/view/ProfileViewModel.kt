package me.empresta.feature_View_Profile.view

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.empresta.DAO.Account
import me.empresta.RemoteAPI.DTO.CommunityInfo
import me.empresta.feature_View_Profile.use_case.ProfileUseCase
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val useCase: ProfileUseCase
) : ViewModel() {


    var account : Account = Account("","");
    var reached = true

    init {
        GlobalScope.launch {
            account = useCase.getProfileInfo()
            reached = true
        }
    }

    fun getProfileInfo() {
    }

    fun viewInfo(): Account{
        return account
    }
}
