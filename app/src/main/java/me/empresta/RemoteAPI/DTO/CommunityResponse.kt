package me.empresta.RemoteAPI.DTO
import com.google.gson.annotations.SerializedName

data class CommunityResponse
(
    @SerializedName("public_key") var public_key : String? = null,
    @SerializedName("response") var response  : String? = null
)