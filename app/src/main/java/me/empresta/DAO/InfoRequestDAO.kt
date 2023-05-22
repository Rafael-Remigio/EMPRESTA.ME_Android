package me.empresta.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface InfoRequestDAO{

    @Upsert
    fun insertItem(request: InfoRequest)

    @Query("select * from InfoRequest")
    fun getAllItems(): Flow<List<InfoRequest>>

    @Query("delete from InfoRequest")
    fun deleteAllItems()


}