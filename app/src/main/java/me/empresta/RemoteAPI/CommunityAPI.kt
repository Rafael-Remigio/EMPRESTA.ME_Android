package me.empresta.RemoteAPI

import me.empresta.RemoteAPI.DTO.CommunityInfo
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface CommunityAPI {

    @GET()
    suspend fun getInfo(@Url url: String): ResponseBody

}