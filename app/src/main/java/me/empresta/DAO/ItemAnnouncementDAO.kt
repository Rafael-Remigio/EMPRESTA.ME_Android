package me.empresta.DAO

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface ItemAnnouncementDAO{

    @Upsert
    fun insertItem(item: ItemAnnouncement)

    @Query("select * from ItemAnnouncement")
    fun getAllItems(): List<ItemAnnouncement>

    @Query("delete from ItemAnnouncement")
    fun deleteAllItems()

    // Get all items from the observer node
    @Query("SELECT * FROM ItemAnnouncement WHERE user = :myPublicKey")
    fun getMyItems(myPublicKey: String): List<ItemAnnouncement>
}