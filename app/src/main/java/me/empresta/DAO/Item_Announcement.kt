package me.empresta.DAO

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.security.PrivateKey
import java.security.PublicKey

@Entity
data class Item_Announcement(
    @PrimaryKey
    val id: Integer,
    val user: String,
    val name: String,
    val description: String = "",
    val image: String = ""
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Item_Announcement

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}
