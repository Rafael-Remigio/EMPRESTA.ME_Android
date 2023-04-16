package me.empresta.feature_QRCode_Connection.use_case

import com.google.gson.Gson
import me.empresta.Crypto.Base58
import me.empresta.Crypto.KeyGen
import me.empresta.Crypto.Utils
import me.empresta.RemoteAPI.CommunityAPI
import me.empresta.RemoteAPI.DTO.CommunityInfo
import me.empresta.RemoteAPI.DTO.CommunityResponse
import org.bouncycastle.asn1.cmp.Challenge
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.interfaces.ECPublicKey
import javax.inject.Inject

class ConnectToCommunity @Inject constructor(private val APIBuilder: Retrofit.Builder) {

    var url: String? = null
    var token: String? = null
    var communityAPI: CommunityAPI?= null

    fun seturl(url: String){
            this.url = url
            println("here + " + this.url)

            communityAPI = Retrofit.Builder().baseUrl(this.url).addConverterFactory(GsonConverterFactory.create()).build().create(CommunityAPI::class.java)
    }

    suspend fun getInfo() : CommunityInfo? {

        val response =   communityAPI!!.getInfo(this.url+"meta/info"!!)
        val gson = Gson()
        return if (response != null) {
            return gson.fromJson(response.string(), CommunityInfo::class.java)
        } else {
            null
        }
    }

    suspend fun challengeCommunity(): Boolean{


        val challenge = generateChallenge()
        val response =   communityAPI!!.postChallenge(this.url + "meta/verify_key",challenge)
        val gson = Gson()
        val communityResponse = gson.fromJson(response.string(),CommunityResponse::class.java)
        return validateResponse(communityResponse,challenge)

    }

    suspend fun associate(password: String): Boolean{

        var response =   communityAPI!!.postAssociate(this.url + "auth/associate",password)
        val gson = Gson()
        token =  response.string()

        var pubKey = KeyGen.getPubCert().publicKey
        //val bytes = Utils.toUncompressedPoint(pubKey as ECPublicKey?)
        /*TODO*/
        //Utils.setParams((pubKey as ECPublicKey).params)
        //response =   communityAPI!!.getChallenge(this.url + "auth/challenge", token!!,Base58.encode(bytes))

        //println("response: "+ response)


        return true
    }

    private fun generateChallenge(): String{
        val random = SecureRandom()
        val bytes = ByteArray(16)
        random.nextBytes(bytes)
        return Base58.encode(bytes)
    }

    private fun validateResponse(response: CommunityResponse,challenge: String): Boolean{
        /*TODO*/
        val responseBytes = Base58.decode(response.response)
        val pubkeyBytes = Base58.decode(response.public_key)
        println(pubkeyBytes.toString())
        //val pubkeyBytesUncompressed = Utils.fromUncompressedPoint(pubkeyBytes)
        val challengeBytes = Base58.decode(challenge)
        //println("" + response.response + "   "+response.public_key + " " +challenge)
        //return Utils.verifySignature(pubkeyBytesUncompressed.encoded,challengeBytes,responseBytes)
        return true
    }


}