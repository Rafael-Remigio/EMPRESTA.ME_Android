package me.empresta.DAO

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.security.PrivateKey
import java.security.PublicKey

@Entity
data class Account(
    @PrimaryKey
    val id : String = "Account",
    val privateKey: ByteArray,
    val publicKey: ByteArray,
    val NickName: String,
    val Description: String = "",
    val contactInfo: String = "",
    val customization: String = "",
)
