package me.empresta.feature_View_Profile.view

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.empresta.DAO.Account
import me.empresta.DAO.ItemAnnouncement
import me.empresta.DAO.ItemRequest
import me.empresta.DI.Repository
import me.empresta.RemoteAPI.DTO.CommunityInfo
import me.empresta.feature_View_Profile.use_case.ProfileUseCase
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val useCase: ProfileUseCase,
    private val repository: Repository
) : ViewModel() {

    var myItems: List<ItemAnnouncement>? = null
    var account : Account = Account("","");
    var reached = true

    init {
        GlobalScope.launch {
            account = useCase.getProfileInfo()
            reached = true
        }
    }

    fun getProfileInfo() {
    }

    fun viewInfo(): Account{
        return account
    }

    fun getItems() {



        /*
          PubSub.Publish_Vouch("my_pub_key","other_pub_key","Vouch description", 0);
          PubSub.Publish_Item_Request("my_pub_key","Dnd Set","Vouch description");
          PubSub.Publish_Item_Announcement_Update("my_pub_key","Bikleta","Vouch description","image_url");
          PubSub.Publish_Item_Request("my_pub_key","Dnd Set","Vouch description");
          PubSub.Publish_Item_Announcement_Update("my_pub_key","Bikleta","Vouch description","image_url");
          PubSub.Publish_Vouch("my_pub_key","other_pub_key","Vouch description", 0);
          */

        //PubSub.Publish_Item_Announcement_Update("my_pub_key","Signature","Item Name", "Item Description", "Photo");
        //PubSub.Publish_Item_Request("my_pub_key","Signature","Item Name", "issuer_pub_key");

    }

    fun get_Items(): List<ItemAnnouncement>? {
        return myItems
    }

}
