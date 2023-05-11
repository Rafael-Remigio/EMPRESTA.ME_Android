package me.empresta.feature_register.view

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import me.empresta.PubSub.PubSub
import me.empresta.feature_register.use_case.RegisterUseCase
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCases: RegisterUseCase
) : ViewModel() {


    fun onRegister (nickName:String,description:String, contact:String): Boolean {
        return registerUseCases.Register(nickName,description, contact)
    }


}
