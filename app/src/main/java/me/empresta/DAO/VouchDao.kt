package me.empresta.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface VouchDAO{

    @Upsert
    fun insertVouch(account: Vouch)

    @Query("select * from Vouch")
    fun getAllVouches(): Flow<List<Vouch>>

    @Query("delete from Vouch")
    fun deleteAllVouches()


}