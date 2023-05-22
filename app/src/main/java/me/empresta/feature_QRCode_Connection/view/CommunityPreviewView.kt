package me.empresta.feature_QRCode_Connection.view

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.empresta.RemoteAPI.DTO.CommunityInfo
import me.empresta.feature_QRCode_Connection.use_case.ConnectToCommunity
import javax.inject.Inject

@HiltViewModel
class CommunityPreviewView @Inject constructor(
    private val connectToCommunity_useCase: ConnectToCommunity
) : ViewModel() {


    private val _state = mutableStateOf(CommunityInfo())
    val state : CommunityInfo = _state.value
    var reached = true
    var usesIDP = false

    fun getInfo(url: String) {
        usesIDP = usesIDP
        connectToCommunity_useCase.seturl("http://$url/")
        GlobalScope.launch{
            try {
                val result = connectToCommunity_useCase.getInfo()
                if (result != null){
                    _state.value =  result
                    reached = true
                }
                else {reached = false}
            }
            catch (e:Exception){
                reached = false
            }
        }

    }

    fun connectWithCommunity(password: String){
        GlobalScope.launch {
            print(_state)
            if (! connectToCommunity_useCase.challengeCommunity()){
                /*TODO*/
                // Launch an exception Screen
            }

            if (!_state.value.title?.let {
                    _state.value.public_key?.let { it1 ->
                        connectToCommunity_useCase.associate(password,
                            it, it1
                        )
                    }
                }!!){
                /*TODO*/
                // Launch an exception Screen
            }
        }
    }

    fun get() = _state.value
}