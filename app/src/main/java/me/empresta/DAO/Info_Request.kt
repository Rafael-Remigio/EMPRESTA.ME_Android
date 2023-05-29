package me.empresta.DAO

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class InfoRequest(
    @PrimaryKey
    val sender: String,
    val message: String = ""
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as InfoRequest

        if (sender != other.sender) return false

        return true
    }

    override fun hashCode(): Int {
        return sender.hashCode()
    }

}
