package me.empresta.feature_View_Feed.view

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.empresta.DAO.ItemAnnouncement
import me.empresta.DAO.ItemRequest
import me.empresta.DI.Repository
import javax.inject.Inject
@HiltViewModel
class feedViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel()
{
    var itemRequests: List<ItemRequest>? = null
    var itemAnnouncenents: List<ItemAnnouncement>? = null

     fun getData() {

         // Mock data
        GlobalScope.launch {
            Log.d("alor","asdsda")

            repository.insertItemAnnouncement(
                ItemAnnouncement(
                    "Joana",
                    "Blender",
                    "This is a blender",
                    "https://s.aolcdn.com/hss/storage/midas/1b8e6c255e1632157aaaf5bd05d9c8/205127975/06-blender-2000.jpg"
                )
            )

            repository.insertItemAnnouncement(
                ItemAnnouncement(
                    "John",
                    "Bike",
                    "This is a bike",
                    "https://th.bing.com/th/id/R.5bcdf75c71d0e9fd9cc8142fcb3307e3?rik=vknt2Qh16ObY0g&riu=http%3a%2f%2f1.bp.blogspot.com%2f-ADXEt7Ian14%2fTkke0CXr5qI%2fAAAAAAAAAC4%2fbhkwY7YYRL4%2fs1600%2fDSC00108.jpg&ehk=Grp1rsJauyAnZGkGd72mW2gI6O0qqXyjajhjfXX6HrY%3d&risl=&pid=Img"
                )
            )
            Log.d("ITEM", "Requests $itemRequests")


            repository.insertItemAnnouncement(
                ItemAnnouncement(
                    "Teles",
                    "DND Set",
                    "This is a Dungeons and Dragons Set",
                    "https://th.bing.com/th/id/R.1b87091133e762c641b49977ecaec156?rik=CK4S2lHxfUYw%2bw&pid=ImgRaw&r=0"
                )
            )


            Log.d("ITEM", "Requests $itemRequests")

            Log.d("ITEM", "Requests $itemRequests")



            repository.insertItemRequest(ItemRequest("John", "Bike", "This is a bike"))
            repository.insertItemRequest(ItemRequest("Maria", "Bike", "This is a bike"))
            repository.insertItemRequest(ItemRequest("Taylor", "Bike", "This is a bike"))

            // Get the list of available items to lend (from the database) -> Correspondents to ItemRequests
            itemRequests = repository.getAllItemRequests()
            print("itemRequests: $itemRequests")
            Log.d("ITEM", "Requests $itemRequests")
            itemAnnouncenents = repository.getAllItemAnnouncements()
            print("itemAnnouncements: $itemAnnouncenents")
            Log.d("ITEM", "Announcements $itemAnnouncenents")
            }

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

    fun get_ItemRequests(): List<ItemRequest>? {
        return itemRequests
    }

    fun get_ItemAnnouncements(): List<ItemAnnouncement>? {
        return itemAnnouncenents
    }

    /*
    @OptIn(DelicateCoroutinesApi::class)
    operator fun invoke(): List {

        try {
            // on below line we are
            // initializing our bitmap
            val writer = MultiFormatWriter()
            GlobalScope.launch{
                val it : Account
                        = itemAnnouncementsFlow.toList().flatten()
            }

            return

        } catch (e: Exception) {
            // on below line we
            // are handling exception
            e.printStackTrace()
        }
        return Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888)
    }
    */
}