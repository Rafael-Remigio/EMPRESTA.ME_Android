package me.empresta.DAO

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.security.PublicKey

@Entity
data class Community(
    val name: String,
    @PrimaryKey val url: String,
    val publicKey: ByteArray,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Community

        if (url != other.url) return false
        if (!publicKey.contentEquals(other.publicKey)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = url.hashCode()
        result = 31 * result + publicKey.contentHashCode()
        return result
    }
}
