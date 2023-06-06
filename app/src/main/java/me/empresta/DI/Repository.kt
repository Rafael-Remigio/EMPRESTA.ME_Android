package me.empresta.DI

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import me.empresta.DAO.Account
import me.empresta.DAO.AccountDao
import me.empresta.DAO.Community
import me.empresta.DAO.CommunityDao
import me.empresta.DAO.Friend
import me.empresta.DAO.FriendDAO
import me.empresta.DAO.InfoRequest
import me.empresta.DAO.InfoRequestDAO
import me.empresta.DAO.ItemAnnouncement
import me.empresta.DAO.ItemAnnouncementDAO
import me.empresta.DAO.ItemRequest
import me.empresta.DAO.ItemRequestDAO
import me.empresta.DAO.VouchDAO
import me.empresta.DAO.Vouch
import me.empresta.RemoteAPI.CommunityAPI
import me.empresta.RemoteAPI.DTO.RegisterBody
import me.empresta.RemoteAPI.DTO.VouchBody
import okhttp3.Response
import okhttp3.ResponseBody
import javax.inject.Inject

class Repository @Inject constructor
    (private val communityDao:CommunityDao, private val accountDao:AccountDao, private val FriendDAO: FriendDAO, private val vouchDAO:VouchDAO, private val itemRequestDAO: ItemRequestDAO, private val itemAnnouncementDAO: ItemAnnouncementDAO, private val InfoRequestDAO: InfoRequestDAO, private val communityAPI: CommunityAPI) {

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

    suspend fun postVouch(url: String,body: VouchBody): ResponseBody {
        return communityAPI.postVouch(url +  "vouch",body)
    }


    suspend fun postPermitInfo(url: String,host: String,guest:String): ResponseBody {
        return communityAPI.postPermitInfo(url +  "acc/permit-info",host,guest)
    }

    suspend fun requestInfo(url: String,host: String,guest:String): ResponseBody {
        return communityAPI.requestInfo(url +  "acc/request-info",host,guest)
    }


    suspend fun requestTopology(url: String): ResponseBody {
        return communityAPI.requestTopology(url)
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
    fun insertItemRequest(item:ItemRequest){
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

        fun getAllInfoRequests(): List<InfoRequest> {
            return InfoRequestDAO.getAllItems()
        }

        fun deleteAllInfoRequests() {
            InfoRequestDAO.deleteAllItems()
        }
    }

    // Get Items from the observer node
    fun getObserverItems(myPublicKey: String): List<ItemAnnouncement> {
        return itemAnnouncementDAO.getMyItems(myPublicKey)
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

    fun getAllInfoRequests(): List<InfoRequest> {
        return InfoRequestDAO.getAllItems()
    }

    fun deleteAllInfoRequests() {
        InfoRequestDAO.deleteAllItems()
    }

    // Friend
    fun insertFriend(friend: Friend){
        FriendDAO.insertFriend(friend)
    }

    fun getAllFriends(): List<Friend> {
        return FriendDAO.getAllFriends()
    }
 
    fun deleteAllFriends() {
        FriendDAO.deleteAllFriends()
    }

    fun countFriends( id: String): Int {
        return FriendDAO.friendExists(id)
    }


}
