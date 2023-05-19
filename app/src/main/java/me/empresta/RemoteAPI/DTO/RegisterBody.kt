package me.empresta.RemoteAPI.DTO
import com.google.gson.annotations.SerializedName

data class RegisterBody
    (
    @SerializedName("public_key") var public_key : String? = null,
    @SerializedName("alias") var alias  : String? = null,
    @SerializedName("bio") var bio  : String? = null,
    @SerializedName("contact") var contact  : String? = null,
    @SerializedName("custom") var custom  : String? = null,
    @SerializedName("response") var response  : String? = null,
)