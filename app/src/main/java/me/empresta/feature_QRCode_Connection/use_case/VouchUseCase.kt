package me.empresta.feature_QRCode_Connection.use_case

import me.empresta.DI.Repository
import javax.inject.Inject

class VouchUseCase @Inject constructor(private val repository: Repository) {

    fun connect() : Boolean{
        try {

            /*TODO*/

            return true
        }
        catch (e:Exception){
            return false
        }
    }

}