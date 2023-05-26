package me.empresta.feature_QRCode_Connection.use_case

import me.empresta.Crypto.Base58
import me.empresta.Crypto.KeyGen
import me.empresta.Crypto.Utils
import me.empresta.DI.Repository
import me.empresta.PubSub.PubSub
import java.security.interfaces.ECPublicKey
import javax.inject.Inject

class VouchUseCase @Inject constructor(private val repository: Repository,private val pubSub: PubSub) {

    fun connect(pubkey: String, communities: List<LinkedHashMap<String, Any>>, description:String,value:Int) : Boolean{
        try {


            val keybytes = Utils.uncompressedToCompressed(Utils.toUncompressedPoint(KeyGen.getPubKey() as ECPublicKey?))
            val mykeyBase58 = Base58.encode(keybytes)


            PubSub.Publish_Vouch(mykeyBase58,pubkey,"my communities", communities,description,value)

            return true
        }
        catch (e:Exception){
            return false
        }
    }

}