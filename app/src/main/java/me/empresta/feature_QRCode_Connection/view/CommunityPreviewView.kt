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
    val state = _state


    @OptIn(DelicateCoroutinesApi::class)
    fun getInfo(url: String) {
        connectToCommunity.seturl("http://$url/")

        GlobalScope.launch{

            println("here we are" + url)

            val result = connectToCommunity.getInfo()
            _state.value =  result
        }
    }

}