package me.empresta.DI

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import me.empresta.DAO.Account
import me.empresta.DAO.AccountDao
import me.empresta.DAO.Community
import me.empresta.DAO.CommunityDao
import me.empresta.DAO.ItemAnnouncementDAO
import me.empresta.DAO.ItemRequestDAO
import me.empresta.DAO.Item_Announcement
import me.empresta.DAO.Item_Request
import me.empresta.DAO.VouchDAO
import me.empresta.DAO.Vouch
import me.empresta.RemoteAPI.CommunityAPI
import okhttp3.ResponseBody
import javax.inject.Inject

class Repository @Inject constructor
    (private val communityDao:CommunityDao, private val accountDao:AccountDao, private val vouchDAO:VouchDAO, private val itemRequestDAO: ItemRequestDAO, private val itemAnnouncementDAO: ItemAnnouncementDAO, private val communityAPI: CommunityAPI) {

        suspend fun getInfo(url: String): ResponseBody {
            return communityAPI.getInfo(url+"meta/info"!!)
        }

        suspend fun postChallenge(url: String,challenge: String): ResponseBody {
            return communityAPI.postChallenge(url +  "meta/verify_key",challenge)
        }


        suspend fun postAssociate(url: String,password: String): ResponseBody {
            return communityAPI.postAssociate(url +  "auth/associate",password)
        }

        suspend fun insertCommunity(community:Community){
            communityDao.insertCommunity(community)
        }


        fun insertAccount(account:Account){
            accountDao.insertAccount(account)
        }

        suspend fun getAccount():Account{
            return accountDao.getAccountById()
        }

        fun deleteAccounts() {
            accountDao.deletePreviousAccounts()
        }


        // Vouches
        fun insertVouch(vouch:Vouch){
            vouchDAO.insertVouch(vouch)
        }

        fun getAllVouches(): Flow<List<Vouch>> {
            return vouchDAO.getAllVouches()
        }

        fun deleteAllVouches() {
            vouchDAO.deleteAllVouches()
        }


        // Item Request
        fun insertItemRequest(item:Item_Request){
            itemRequestDAO.insertItem(item)
        }

        fun getAllItemRequests(): Flow<List<Item_Request>> {
            return itemRequestDAO.getAllItems()
        }

        fun deleteAllItemRequests() {
            itemRequestDAO.deleteAllItems()
        }


        // Item Announcement
        // Item Request
        fun insertItemAnnouncement(item:Item_Announcement){
            itemAnnouncementDAO.insertItem(item)
        }

        fun getAllItemAnnouncements(): Flow<List<Item_Announcement>> {
            return itemAnnouncementDAO.getAllItems()
        }

        fun deleteAllItemAnnouncements() {
            itemAnnouncementDAO.deleteAllItems()
        }
    }