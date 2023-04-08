package me.empresta.DAO

import androidx.room.RoomDatabase
import androidx.room.Database

@Database(
    entities = [Account::class,Community::class],
    version = 1
)
abstract class Database: RoomDatabase() {

    abstract val AccountDao: AccountDao

    abstract val CommunityDao: CommunityDao

}