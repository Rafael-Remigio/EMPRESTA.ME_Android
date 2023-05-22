package me.empresta.feature_View_Feed.view

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import me.empresta.DAO.ItemAnnouncement
import me.empresta.DAO.ItemRequest
import me.empresta.DI.Repository
import me.empresta.feature_View_Feed.view.model.AvailableItem
import javax.inject.Inject
@HiltViewModel
class feedViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel()
{
    init {
        // Mock data
        repository?.insertItemAnnouncement(ItemAnnouncement("John","Bike 1","This is a bike", "https://th.bing.com/th/id/R.5bcdf75c71d0e9fd9cc8142fcb3307e3?rik=vknt2Qh16ObY0g&riu=http%3a%2f%2f1.bp.blogspot.com%2f-ADXEt7Ian14%2fTkke0CXr5qI%2fAAAAAAAAAC4%2fbhkwY7YYRL4%2fs1600%2fDSC00108.jpg&ehk=Grp1rsJauyAnZGkGd72mW2gI6O0qqXyjajhjfXX6HrY%3d&risl=&pid=Img"))
        repository?.insertItemAnnouncement(ItemAnnouncement("John","Bike 2","This is a bike", "https://th.bing.com/th/id/R.5bcdf75c71d0e9fd9cc8142fcb3307e3?rik=vknt2Qh16ObY0g&riu=http%3a%2f%2f1.bp.blogspot.com%2f-ADXEt7Ian14%2fTkke0CXr5qI%2fAAAAAAAAAC4%2fbhkwY7YYRL4%2fs1600%2fDSC00108.jpg&ehk=Grp1rsJauyAnZGkGd72mW2gI6O0qqXyjajhjfXX6HrY%3d&risl=&pid=Img"))
        repository?.insertItemAnnouncement(ItemAnnouncement("John","Bike 3","This is a bike", "https://th.bing.com/th/id/R.5bcdf75c71d0e9fd9cc8142fcb3307e3?rik=vknt2Qh16ObY0g&riu=http%3a%2f%2f1.bp.blogspot.com%2f-ADXEt7Ian14%2fTkke0CXr5qI%2fAAAAAAAAAC4%2fbhkwY7YYRL4%2fs1600%2fDSC00108.jpg&ehk=Grp1rsJauyAnZGkGd72mW2gI6O0qqXyjajhjfXX6HrY%3d&risl=&pid=Img"))

        repository?.insertItemRequest(ItemRequest("John","Bike 4","This is a bike"))
        repository?.insertItemRequest(ItemRequest("John","Bike 5","This is a bike"))
        repository?.insertItemRequest(ItemRequest("John","Bike 6","This is a bike"))

        // Get the list of available items to lend (from the database) -> Correspondents to ItemRequests
        val itemRequests = repository?.getAllItemRequests()
        System.out.println("itemRequests: $itemRequests")
        val itemAnnouncenents = repository?.getAllItemAnnouncements()
        System.out.println("itemAnnouncements: $itemAnnouncenents")


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

}