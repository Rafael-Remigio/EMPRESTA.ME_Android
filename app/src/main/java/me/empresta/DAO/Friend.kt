package me.empresta.DAO

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.security.PrivateKey
import java.security.PublicKey

@Entity
data class Friend(
    @PrimaryKey
    val publicKey: String,
    val community: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Friend

        if (publicKey != other.publicKey) return false

        return true
    }

    override fun hashCode(): Int {
        return publicKey.hashCode()
    }

}