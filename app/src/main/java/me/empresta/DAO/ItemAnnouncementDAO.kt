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
    fun insertItem(item: Item_Announcement)

    @Query("select * from Item_Announcement")
    fun getAllItems(): Flow<List<Item_Announcement>>

    @Query("delete from Item_Announcement")
    fun deleteAllItems()


}