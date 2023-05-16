package me.empresta.feature_QRCode_Connection.use_case

import com.google.gson.Gson
import me.empresta.Crypto.Base58
import me.empresta.Crypto.KeyGen
import me.empresta.Crypto.Utils
import me.empresta.DAO.Community
import me.empresta.DAO.CommunityDao
import me.empresta.DI.Repository
import me.empresta.RemoteAPI.CommunityAPI
import me.empresta.RemoteAPI.DTO.CommunityInfo
import me.empresta.RemoteAPI.DTO.CommunityResponse
import org.bouncycastle.asn1.cmp.Challenge
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.interfaces.ECPublicKey
import javax.inject.Inject

class ConnectToCommunity @Inject constructor(private val repository: Repository, private val communityDao: CommunityDao) {

    var url: String = ""
    var token: String? = null

    fun seturl(url: String){
            this.url = url
    }

    suspend fun getInfo() : CommunityInfo? {

        val response =   repository.getInfo(this.url)
        val gson = Gson()
        return try {
            gson.fromJson(response.string(), CommunityInfo::class.java)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun challengeCommunity(): Boolean{


        /*val challenge = generateChallenge()
        val response =   repository.postChallenge(this.url ,challenge)
        val gson = Gson()
        val communityResponse = gson.fromJson(response.string(),CommunityResponse::class.java)
        return validateResponse(communityResponse,challenge)*/
        return true

    }


    suspend fun associate(password: String,communityName:String,pubKey: String): Boolean{

        var response =   repository.postAssociate(this.url,password)
        val gson = Gson()
        token =  response.string()



        //val bytes = Utils.toUncompressedPoint(pubKey as ECPublicKey?)
        /*TODO*/
        //Utils.setParams((pubKey as ECPublicKey).params)
        //response =   communityAPI!!.getChallenge(this.url + "auth/challenge", token!!,Base58.encode(bytes))

        //println("response: "+ response)



        val community = Community(communityName,this.url, Base58.decode(pubKey))
        repository.insertCommunity(community)


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