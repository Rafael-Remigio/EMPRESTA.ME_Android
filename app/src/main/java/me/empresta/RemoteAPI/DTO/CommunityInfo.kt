package me.empresta.RemoteAPI.DTO

import com.google.gson.annotations.SerializedName
import java.security.PublicKey

data class CommunityInfo (

    @SerializedName("bio") var bio : String? = null,
    @SerializedName("public_key") var public_key  : String? = null,
    @SerializedName("title") var title  : String? = null
)