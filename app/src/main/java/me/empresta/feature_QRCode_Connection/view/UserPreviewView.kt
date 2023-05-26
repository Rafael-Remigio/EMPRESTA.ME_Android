package me.empresta.feature_QRCode_Connection.view

import android.os.AsyncTask
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import me.empresta.RemoteAPI.DTO.CommunityInfo
import me.empresta.feature_QRCode_Connection.use_case.ConnectToCommunity
import me.empresta.feature_QRCode_Connection.use_case.VouchUseCase
import javax.inject.Inject

@HiltViewModel
class UserPreviewView @Inject constructor(
    private val vouchUseCase: VouchUseCase
) : ViewModel() {

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    fun sendMessage(message: String) {
        viewModelScope.launch {
            _toastMessage.emit(message)
        }
    }

     fun connect(pubkey:String, communities: List<LinkedHashMap<String, Any>>, description: String, value:Int) {
         viewModelScope.launch {

             if (vouchUseCase.connect(pubkey,communities,description,value)){

                 viewModelScope.launch {
                     _toastMessage.emit("Vouched Successfully")
                 }

             }
             else {
                 viewModelScope.launch {
                     _toastMessage.emit("Something went wrong")
                 }
             }



         }


        return
    }

}