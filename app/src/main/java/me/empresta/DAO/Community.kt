package me.empresta.DAO

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.security.PublicKey

@Entity
data class Community(
    val name: String,
    @PrimaryKey val url: String,
    val publicKey: ByteArray,
)
