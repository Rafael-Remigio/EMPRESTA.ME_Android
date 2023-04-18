package me.empresta.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert


@Dao
interface AccountDao {

    @Upsert
    fun insertAccount(account: Account)

    @Query("select * from Account")
    suspend fun getAccountById(): Account

    @Query("delete from Account")
    fun deletePreviousAccounts()


}