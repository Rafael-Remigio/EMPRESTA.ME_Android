package me.empresta.RemoteAPI.DTO

import com.google.gson.annotations.SerializedName

data class CommunityInfo (

    @SerializedName("user_name") var userName : String? = null,
    @SerializedName("email") var email    : String? = null,
    @SerializedName("name") var name     : String? = null

)