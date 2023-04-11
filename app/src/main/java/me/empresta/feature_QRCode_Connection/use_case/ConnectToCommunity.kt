package me.empresta.feature_QRCode_Connection.use_case

import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import me.empresta.RemoteAPI.CommunityAPI
import me.empresta.RemoteAPI.DTO.CommunityInfo
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class ConnectToCommunity @Inject constructor(private val APIBuilder: Retrofit.Builder) {

    var url: String? = null

    var communityAPI: CommunityAPI?= null

    fun seturl(url: String){
            this.url = url
            println("here + " + this.url)

            communityAPI = Retrofit.Builder().baseUrl(this.url).addConverterFactory(GsonConverterFactory.create()).build().create(CommunityAPI::class.java)
    }

    suspend fun getInfo() : CommunityInfo? {

        val response =   communityAPI!!.getInfo(this.url+"meta/info"!!)
        print(response)
        val gson = Gson()
        return if (response != null) {
            return gson.fromJson(response.string(), CommunityInfo::class.java)
        } else {
            null
        }
    }



}