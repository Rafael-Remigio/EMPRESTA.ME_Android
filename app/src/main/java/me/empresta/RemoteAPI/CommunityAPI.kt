package me.empresta.RemoteAPI

import me.empresta.RemoteAPI.DTO.CommunityInfo
import retrofit2.http.GET
import retrofit2.http.Url

interface CommunityAPI {

    @GET()
    suspend fun getInfo(@Url url: String): CommunityInfo

}