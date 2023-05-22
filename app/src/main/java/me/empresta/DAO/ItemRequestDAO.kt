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
    fun insertItem(item: ItemRequest)

    @Query("select * from ItemRequest")
    fun getAllItems(): Flow<List<ItemRequest>>

    @Query("delete from ItemRequest")
    fun deleteAllItems()


}