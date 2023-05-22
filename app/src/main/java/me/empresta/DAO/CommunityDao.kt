package me.empresta.DAO

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface CommunityDao {

    @Upsert
    suspend fun insertCommunity(community: Community)

    @Query("select * from community")
    fun getCommunityList(): List<Community>
}