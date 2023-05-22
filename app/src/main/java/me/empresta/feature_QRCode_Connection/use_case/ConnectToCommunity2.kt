package me.empresta.feature_QRCode_Connection.use_case

import com.google.gson.Gson
import me.empresta.Crypto.Base58
import me.empresta.Crypto.KeyGen
import me.empresta.Crypto.Utils
import me.empresta.DAO.Community
import me.empresta.DAO.CommunityDao
import me.empresta.DI.Repository
import me.empresta.RemoteAPI.DTO.CommunityInfo
import me.empresta.RemoteAPI.DTO.CommunityResponse
import me.empresta.RemoteAPI.DTO.RegisterBody
import java.security.SecureRandom
import java.security.interfaces.ECPublicKey
import javax.inject.Inject
import retrofit2.HttpException
import java.net.URI

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
        val challenge = generateChallenge()
        val response =   repository.postChallenge(this.url ,challenge)
        val gson = Gson()
        val communityResponse = gson.fromJson(response.string(),CommunityResponse::class.java)
        return validateResponse(communityResponse,challenge)
    }

    suspend fun associate(password: String,communityName:String,pubKey: String): Boolean{
        // Post Password to get token
        var response =   repository.postAssociate(this.url,password)
        token =  response.string()

        // With the token, get the challenge and sign it
        val keybytes = Utils.uncompressedToCompressed(Utils.toUncompressedPoint(KeyGen.getPubKey() as ECPublicKey?))
        val keyBase58 = Base58.encode(keybytes)
        response =   repository.getChallenge(this.url , token!!,keyBase58)

        // Sign the challenge
        val challenge = response.string()
        val signedData = Base58.encode(KeyGen.sign(Base58.decode(challenge)))
        val account = repository.getAccount()

        val body = RegisterBody(keyBase58,account.NickName,account.Description,account.contactInfo,account.customization,signedData)

        // Post the signed challenge
        try {
            response = repository.postRegister(url,body)

            // Insert the community in the database
            val community = Community(communityName,this.url, Base58.decode(pubKey))
            repository.insertCommunity(community)

            return true
        }
        catch (e: HttpException){

            return false
        }
    }

    fun associateWithIDP(password: String,communityName:String,pubKey: String): Boolean{
        val uaAuthUriComplete =  buildURI("ua")
        print(uaAuthUriComplete)
        return true
    }

    private fun buildURI(provider: String): URI {
        val UA_OAUTH_AUTH_URI = "https://wso2-gw.ua.pt/authorize?"
        val UA_OAUTH_REDIRECT_URI = "http://localhost"
        val UA_OAUTH_SCOPE = "openid"
        val UA_OAUTH_RESPONSE_TYPE = "code"
        val UA_OAUTH_CLIENT_ID = "u8vpP8lcTQLURxk8itbLvY4pySoa"
        val UA_OAUTH_CLIENT_SECRET = "r5f7TTsA1wWTCbXjhznjFzf23sEa"

        if (provider == "ua") {
            val uaAuthUriComplete = URI("${UA_OAUTH_AUTH_URI}redirect_uri=${UA_OAUTH_REDIRECT_URI}&scope=${UA_OAUTH_SCOPE}&response_type=${UA_OAUTH_RESPONSE_TYPE}&client_id=${UA_OAUTH_CLIENT_ID}")
            return uaAuthUriComplete
        }
        throw IllegalArgumentException("Invalid login provider")
    }

    private fun generateChallenge(): String{
        val random = SecureRandom()
        val bytes = ByteArray(16)
        random.nextBytes(bytes)
        return Base58.encode(bytes)
    }

    private fun validateResponse(response: CommunityResponse,challenge: String): Boolean{
        val responseBytes = Base58.decode(response.response)
        val pubkeyBytes = Base58.decode(response.public_key)
        val pubKey = Utils.fromUncompressedPoint(Utils.compressedToUncompressed(pubkeyBytes))
        val challengeBytes = Base58.decode(challenge)
        return Utils.verifySignature(pubKey,challengeBytes,responseBytes)
    }



}