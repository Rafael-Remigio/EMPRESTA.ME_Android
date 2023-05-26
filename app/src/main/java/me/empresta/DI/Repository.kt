package me.empresta.DI

import kotlinx.coroutines.flow.Flow
import me.empresta.DAO.*
import me.empresta.RemoteAPI.CommunityAPI
import me.empresta.RemoteAPI.DTO.RegisterBody
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


    suspend fun getChallenge(url: String,token: String,keyBytes: String): ResponseBody {
        return communityAPI.getChallenge(url + "auth/challenge",token,keyBytes)
    }


    suspend fun postAssociate(url: String,password: String): ResponseBody {
        return communityAPI.postAssociate(url +  "auth/associate",password)
    }


    suspend fun postRegister(url: String,body: RegisterBody): ResponseBody {
        return communityAPI.postRegister(url +  "acc/register",body)
    }

    suspend fun getContact(url: String,body: RegisterBody): ResponseBody {
        return communityAPI.postRegister(url +  "acc/register",body)
    }


    suspend fun insertCommunity(community:Community){
        communityDao.insertCommunity(community)
    }


    suspend fun getCommunities():List<Community>{
        return communityDao.getCommunityList()
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
    fun insertItemRequest(item: ItemRequest){
        itemRequestDAO.insertItem(item)


        // Item Request
        fun insertItemRequest(item:ItemRequest){
            itemRequestDAO.insertItem(item)
        }

        fun getAllItemRequests(): List<ItemRequest> {
            return itemRequestDAO.getAllItems()
        }

        fun deleteAllItemRequests() {
            itemRequestDAO.deleteAllItems()
        }


        // Item Announcement
        fun insertItemAnnouncement(item: ItemAnnouncement){
            itemAnnouncementDAO.insertItem(item)
        }

        fun getAllItemAnnouncements(): List<ItemAnnouncement> {
            return itemAnnouncementDAO.getAllItems()
        }

        fun deleteAllItemAnnouncements() {
            itemAnnouncementDAO.deleteAllItems()
        }


        // Info Request
        fun insertInfoRequest(request: InfoRequest){
            InfoRequestDAO.insertItem(request)
        }

        fun getAllInfoRequests(): Flow<List<InfoRequest>> {
            return InfoRequestDAO.getAllItems()
        }

        fun deleteAllInfoRequests() {
            InfoRequestDAO.deleteAllItems()
        }
    }

    fun getAllItemRequests(): List<ItemRequest> {
        return itemRequestDAO.getAllItems()
    }

    // Item Announcement
    fun insertItemAnnouncement(item:ItemAnnouncement){
        itemAnnouncementDAO.insertItem(item)
    }

    fun getAllItemAnnouncements(): List<ItemAnnouncement> {
        return itemAnnouncementDAO.getAllItems()
    }

}