package me.empresta.DAO

import androidx.room.RoomDatabase
import androidx.room.Database

@Database(
    entities = [Account::class,Community::class,Vouch::class,ItemRequest::class,ItemAnnouncement::class,InfoRequest::class],
    version = 1
)
abstract class Database: RoomDatabase() {

    abstract val AccountDao: AccountDao

    abstract val CommunityDao: CommunityDao


    abstract val VouchDAO: VouchDAO

    abstract val ItemRequestDAO: ItemRequestDAO

    abstract val ItemAnnouncementDAO: ItemAnnouncementDAO

    abstract val InfoRequestDAO: InfoRequestDAO

}