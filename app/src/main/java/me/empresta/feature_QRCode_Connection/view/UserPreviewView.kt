package me.empresta.feature_QRCode_Connection.view

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.empresta.RemoteAPI.DTO.CommunityInfo
import me.empresta.feature_QRCode_Connection.use_case.ConnectToCommunity
import me.empresta.feature_QRCode_Connection.use_case.VouchUseCase
import javax.inject.Inject

@HiltViewModel
class UserPreviewView @Inject constructor(
    private val vouchUseCase: VouchUseCase
) : ViewModel() {

    fun connect(pubkey:String, communities: List<LinkedHashMap<String, Any>>,description: String, value:Int): Boolean {
        return vouchUseCase.connect(pubkey,communities,description,value)
    }

}