package me.empresta.feature_View_Feed.view

import android.app.ActivityManager.TaskDescription
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.lifecycle.ViewModel
import coil.compose.ImagePainter
import com.google.gson.Gson
import com.google.gson.JsonObject
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

    val names_map = mutableMapOf<String, String>()
    val contact_map = mutableMapOf<String, String>()
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

            for (i in itemRequests!!){
                names_map[i.user] = "Anonymous"
                contact_map[i.user] = ""

            }
            for (i in itemAnnouncenents!!){
                names_map[i.user] = "Anonymous"
                contact_map[i.user] = ""
            }

            for (name in names_map.keys){
                getInfo(name);
            }

            Log.d("DEBUG", "new map" + names_map.toString())

        }

    }

    fun post_Lending (title: String,description: String,type:String,category: String) : Boolean{

        Log.d("DEBUG",category)

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

        return when (category) {
            "Clothing and Accessories" -> R.drawable.clothing_image
            "Electronics" -> R.drawable.eletronics_image
            "Sports and Fitness" -> R.drawable.sports_image
            "Books and Media" -> R.drawable.books_image
            "Home and Garden" -> R.drawable.tools_image
            "Kitchen" -> R.drawable.kitchen_image
            else -> { // Note the block
                R.drawable.ic_launcher_foreground
            }
        }

    }

    fun getInfo(guestPK: String) {

        var res: String? = null;

        GlobalScope.launch {
            val account = repository.getAccount()

            for (community in repository.getCommunities()) {

                try {
                    res = repository.requestInfo(community.url,account.publicKey,guestPK).string()
                    val gson = Gson()
                    val jsonObject = gson.fromJson(res, JsonObject::class.java)
                    //_state.value = jsonObject.get("alias").asString
                    names_map[guestPK] = jsonObject.get("alias").asString
                    contact_map[guestPK] = jsonObject.get("contact").asString
                    Log.d("DEBUG",
                        names_map.keys!!.toString()
                    );
                    Log.d("DEBUG",names_map.toString())
                    break

                }catch (e: Exception)
                {
                    Log.d("DEBUG","error" + e)
                }
            }


        }


    }
    fun get_name(p_k : String): String {

        if (names_map.containsKey(p_k)){
            return names_map[p_k]!!
        }
        else {
            return "Anonymous"
        }
    }




    fun get_contact(p_k : String): String {

        if (contact_map.containsKey(p_k)){
            return contact_map[p_k]!!
        }
        else {
            return ""
        }
    }
}