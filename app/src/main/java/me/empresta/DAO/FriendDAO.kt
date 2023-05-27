package me.empresta.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface FriendDAO{

    @Upsert
    fun insertFriend(friend: Friend)

    @Query("select * from Friend")
    fun getAllFriends(): List<Friend>

    @Query("delete from Friend")
    fun deleteAllFriends()


    @Query("select count(publicKey) from Friend where publicKey = :id")
    fun friendExists( id:String): Int

}