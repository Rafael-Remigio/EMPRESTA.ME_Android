package me.empresta.feature_View_Network.view

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.empresta.Crypto.Base58
import me.empresta.DAO.Account
import me.empresta.DAO.Community
import me.empresta.RemoteAPI.DTO.CommunityInfo
import me.empresta.feature_QRCode_Connection.use_case.VouchUseCase
import me.empresta.feature_View_Network.use_case.NetworkUseCase
import javax.inject.Inject

@HiltViewModel
class NetworkView @Inject constructor(
    private val useCase: NetworkUseCase
) : ViewModel() {


    private val _state = mutableStateOf(Community("","", Base58.decode("")))
    val state : Community = _state.value


    private val _account_state = mutableStateOf(Account("",""))
    val account_state : Account = _account_state.value
    var reached = true


    fun getCommunity() {

        GlobalScope.launch{
            try {
                val result = useCase.getFirstCommunity()
                val account = useCase.getMyInfo()
                if (result != null){
                    _state.value =  result
                    _account_state.value = account
                    println(account)
                    reached = true
                }
                else {reached = false}
            }
            catch (e:Exception){
                reached = false
            }
        }

    }


    fun get() = _state.value
    fun getAccount() = _account_state.value

}