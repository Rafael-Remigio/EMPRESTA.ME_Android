package me.empresta.DAO

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ItemAnnouncement(
    @PrimaryKey
    var id: Int,
    val user: String,
    val name: String,
    val description: String = "",
    val image: String = ""
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ItemAnnouncement

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}
