package me.empresta.feature_QRCode_Connection.view

import android.content.res.Resources
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.empresta.DAO.AccountDao
import me.empresta.RemoteAPI.DTO.CommunityInfo
import me.empresta.feature_QRCode_Connection.use_case.ConnectToCommunity
import javax.inject.Inject

@HiltViewModel
class CommunityPreviewView @Inject constructor(
    private val connectToCommunity: ConnectToCommunity
) : ViewModel() {



    private val _state = mutableStateOf(CommunityInfo())
    val state : CommunityInfo = _state.value
    var reached = false

    fun getInfo(url: String) {
        println("here we are" + url)
        connectToCommunity.seturl("http://$url/")
        GlobalScope.launch{


            val result = connectToCommunity.getInfo()
            if (result != null){
                _state.value =  result
                reached = true
            }
        }

    }


    fun get() = _state.value
}