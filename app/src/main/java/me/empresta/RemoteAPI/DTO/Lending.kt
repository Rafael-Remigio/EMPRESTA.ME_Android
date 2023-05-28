package me.empresta.RemoteAPI.DTO

import com.google.gson.annotations.SerializedName

data class Lending (

    @SerializedName("img"         ) var img        : String?           = null,
    @SerializedName("score"       ) var score      : Double?           = null,
    @SerializedName("terms"       ) var terms      : ArrayList<String> = arrayListOf(),
    @SerializedName("timestamp"   ) var timestamp  : String?           = null,
    @SerializedName("title"       ) var title      : String?           = null,
    @SerializedName("user"        ) var user       : String?           = null,
    @SerializedName("user_avatar" ) var userAvatar : String?           = null
)