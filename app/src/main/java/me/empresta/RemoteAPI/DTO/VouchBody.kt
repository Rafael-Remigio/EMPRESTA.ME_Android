package me.empresta.RemoteAPI.DTO

import com.google.gson.annotations.SerializedName

data class VouchBody
    (
    @SerializedName("header") var header : String? = null,
    @SerializedName("clock") var clock  : String? = null,
    @SerializedName("hash") var hash  : String? = null,
    @SerializedName("nonce") var nonce  : String? = null,
    @SerializedName("signature") var signature  : String? = null,
    @SerializedName("sender") var sender  : String? = null,
    @SerializedName("receiver") var receiver  : String? = null,
    @SerializedName("sender_community") var sender_community  : String? = null,
    @SerializedName("receiver_community") var receiver_community  : String? = null,
    @SerializedName("state") var state  : Int? = null,
    @SerializedName("message") var message  : String? = null,
    )