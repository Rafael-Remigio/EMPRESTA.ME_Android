package me.empresta.feature_QRCode_Connection.use_case

import me.empresta.DAO.AccountDao
import me.empresta.DAO.CommunityDao
import me.empresta.RemoteAPI.CommunityAPI
import me.empresta.RemoteAPI.DTO.CommunityInfo
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class ConnectToCommunity @Inject constructor(private val APIBuilder: Retrofit.Builder) {

    var url: String? = null

    var communityAPI: CommunityAPI?= null

        //.addConverterFactory(GsonConverterFactory.create()).build().create(CommunityAPI::class.java)
    fun seturl(url: String){
            this.url = url
            communityAPI = Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build().create(CommunityAPI::class.java)
            println("here + " + url)
    }

    suspend fun getInfo() : CommunityInfo {

        return communityAPI!!.getInfo(this.url+"meta/info"!!)

    }



}