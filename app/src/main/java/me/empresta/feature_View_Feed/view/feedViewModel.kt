package me.empresta.feature_View_Feed.view

import android.app.ActivityManager.TaskDescription
import android.util.Log
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.lifecycle.ViewModel
import coil.compose.ImagePainter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.empresta.DAO.ItemAnnouncement
import me.empresta.DAO.ItemRequest
import me.empresta.DI.Repository
import me.empresta.PubSub.PubSub
import me.empresta.R
import javax.inject.Inject
@HiltViewModel
class feedViewModel @Inject constructor(
    private val repository: Repository,    private val pubSub: PubSub

) : ViewModel()
{
    var itemRequests: List<ItemRequest>? = null
    var itemAnnouncenents: List<ItemAnnouncement>? = null



    var threads : ArrayList<Thread> = ArrayList<Thread>()
    fun startListining(){
        GlobalScope.launch {
            for (f in repository.getAllFriends()){

                threads.add(pubSub.start_listening(f.publicKey, f.community))
            }
        }
    }

     fun getData() {

         // Mock data
        GlobalScope.launch {

            Log.d("alor","asdsda")
            var user : String = "Joana";
            var name : String = "Blender";
            var description : String = "This is a blender";
            var image : String = "Home and Garden";
            repository.insertItemAnnouncement(

                ItemAnnouncement((user + name+ description).hashCode(),
                    user,
                    name,
                    description,
                    image
                )
            )

            user = "John";
            name  = "Bike";
            description  = "This is a bike";
            image  = "Clothing and Accessories";
            repository.insertItemAnnouncement(
                ItemAnnouncement(
                    (user+name+description).hashCode(),
                    user,
                    name,
                    description,
                    image
                )
            )
            Log.d("ITEM", "Requests $itemRequests");

            user  = "Teles";
            name  = "DND Set";
            description = "This is a Dungeons and Dragons Set";
            image = "Electronics";
            repository.insertItemAnnouncement(
                ItemAnnouncement(
                    (user+name+description).hashCode(),
                    user,
                    name,
                    description,
                    image
                )
            )


            Log.d("ITEM", "Requests $itemRequests")

            Log.d("ITEM", "Requests $itemRequests")



            repository.insertItemRequest(ItemRequest("JohnBikeThis is a bike".hashCode(),"John", "Bike", "This is a bike","Clothing and Accessories"))
            repository.insertItemRequest(ItemRequest("MariaBikeThis is a bike".hashCode(),"Maria", "Bike", "This is a bike","Electronics"))
            repository.insertItemRequest(ItemRequest("TaylorBikeThis is a bike".hashCode(),"Taylor", "Bike", "This is a bike","Home and Garden"))

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
        //PubSub.Publish_Item_Request("my_pub_key","Signature","Item Name", "issuer_pub_key");*/

    }

    fun post_Lending (title: String,description: String,type:String,category: String) : Boolean{


        GlobalScope.launch {

            var com : String =  repository.getCommunities()[0].url;

            if(type.equals("NEED")){
                PubSub.Publish_Item_Request(com.substring(7,com.length - 6), repository.getAccount().publicKey, title, description,category)
            }else{print("\n\n" + type + "\n\n")
                PubSub.Publish_Item_Announcement_Update(com.substring(7,com.length - 6), repository.getAccount().publicKey, title, description, category)
            }
        }

        return true
    }

    fun ask_for_info(target_key:String, message:String) : Boolean{

        GlobalScope.launch {
            var com : String =  repository.getCommunities()[0].url;
            PubSub.Publish_Ask_Info(com.substring(7,com.length - 6), repository.getAccount().publicKey, target_key, message)
        }

        return true
    }

    fun get_ItemRequests(): List<ItemRequest>? {
        return itemRequests
    }

    fun get_ItemAnnouncements(): List<ItemAnnouncement>? {
        return itemAnnouncenents
    }


    fun getImageByCategory( category: String): Int {
        /*TODO*/
        //Return images from the actual category

        when (category) {
            "Clothing and Accessories" -> return R.drawable.clothing_image
            "Electronics" -> return R.drawable.eletronics_image
            "Sports and Fitness" -> return R.drawable.sports_image
            "Books and Media" -> return  R.drawable.books_image
            "Home and Garden" -> return  R.drawable.tools_image
            "Kitchen" -> return R.drawable.kitchen_image
            else -> { // Note the block
                return  R.drawable.ic_launcher_foreground
            }
        }

    }

}