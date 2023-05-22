package me.empresta.DAO

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class InfoRequest(
    val sender: String,
    val message: String = ""
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as InfoRequest

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}
