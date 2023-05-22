package me.empresta.DAO

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.security.PrivateKey
import java.security.PublicKey

@Entity
data class Vouch(
    @PrimaryKey
    val id: String,
    val publicKey_1: String,
    val publicKey_2: String,
    val state: Integer,
    val Description: String = ""
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Vouch

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}