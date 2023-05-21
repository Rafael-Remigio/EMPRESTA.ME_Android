package me.empresta.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface ItemRequestDAO{

    @Upsert
    fun insertItem(item: Item_Request)

    @Query("select * from Item_Request")
    fun getAllItems(): Flow<List<Item_Request>>

    @Query("delete from Item_Request")
    fun deleteAllItems()


}