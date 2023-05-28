package me.empresta.feature_register.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import me.empresta.DI.Repository
import me.empresta.PubSub.PubSub
import me.empresta.feature_register.use_case.RegisterUseCase
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCases: RegisterUseCase, private val repository: Repository
) : ViewModel() {

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    fun sendMessage(message: String) {
        viewModelScope.launch {
            _toastMessage.emit(message)
        }
    }

    fun alreadyRegistered () {

        GlobalScope.launch {
            if (repository.getAccount() != null){
                viewModelScope.launch {
                    _toastMessage.emit("Already Logged in")
                }
            }

        }
    }

    fun onRegister (nickName:String,description:String, contact:String): Boolean {
        return registerUseCases.Register(nickName,description, contact)
    }


}
