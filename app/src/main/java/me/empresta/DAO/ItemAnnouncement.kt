package me.empresta.DAO

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ItemAnnouncement(
    val user: String,
    val name: String,
    val description: String = "",
    val image: String = ""
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
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
