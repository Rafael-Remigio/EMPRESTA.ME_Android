package me.empresta.feature_QRCode_Connection.use_case

import me.empresta.DI.Repository
import me.empresta.PubSub.PubSub
import javax.inject.Inject

class VouchUseCase @Inject constructor(private val repository: Repository,private val pubSub: PubSub) {

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