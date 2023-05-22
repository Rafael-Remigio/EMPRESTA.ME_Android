package me.empresta.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface ItemAnnouncementDAO{

    @Upsert
    fun insertItem(item: ItemAnnouncement)

    @Query("select * from ItemAnnouncement")
    fun getAllItems(): Flow<List<ItemAnnouncement>>

    @Query("delete from ItemAnnouncement")
    fun deleteAllItems()


}