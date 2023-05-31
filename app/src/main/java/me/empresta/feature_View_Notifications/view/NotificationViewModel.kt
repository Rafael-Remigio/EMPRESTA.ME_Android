package me.empresta.feature_View_Notifications.view

import android.provider.Settings.Global
import android.util.Log
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import me.empresta.DAO.InfoRequest
import me.empresta.DI.Repository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import me.empresta.RemoteAPI.DTO.CommunityInfo
import me.empresta.feature_QRCode_Connection.view.UserPreviewView
import okhttp3.ResponseBody

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val repository: Repository

): ViewModel() {

    public var info: List<InfoRequest>? = null

    init{
        // Mock data
        GlobalScope.launch{

            /*
            repository.insertInfoRequest(
                InfoRequest(
                    "Shor Manel",
                    "ola"
                )
            )

            repository.insertInfoRequest(
                InfoRequest(
                    "Casemiro",
                    "preciso da torradeira, a minha estragou-se"
                )
            )

            repository.insertInfoRequest(
                InfoRequest(
                    "Shor Manel",
                    "saudaçoes camarada!"
                )
            )

            repository.insertInfoRequest(
                InfoRequest(
                    "Juan",
                    "holla"
                )
            )

            repository.insertInfoRequest(
                InfoRequest(
                    "Shor Manel",
                    "e viva o Puorto!"
                )
            )*/

            val a = getInfoRequests()
        }
    }



     suspend fun getInfoRequests() {
         GlobalScope.launch {
             info = repository.getAllInfoRequests()
         }
         return

    }

    fun permitInfo(guestPK: String) {

        GlobalScope.launch {
            val account = repository.getAccount()

            repository.getCommunities().forEach(){ community ->
                Log.d("DEBUG",
                    "\n Response v"
                );
                Log.d("DEBUG",
                    repository.postPermitInfo(community.url,account.publicKey,guestPK).toString()
                );

            }
        }
        return

    }

    fun getInfo(guestPK: String): ResponseBody? {

        var res: ResponseBody? = null;

        GlobalScope.launch {
            val account = repository.getAccount()
            /*
            for (community in repository.getCommunities()) {

                try {
                    res = repository.requestInfo(community.url,account.publicKey,guestPK)
                    Log.d("DEBUG",
                        res.toString()
                    );
                    if (res != null){
                        break
                    }

                }catch (e: Exception)
                {
                    println(e)
                }
            }
            */
            try {
                res = repository.requestInfo(repository.getCommunities()[0].url,account.publicKey,guestPK)
            }catch (e: Exception)
            {
                println(e)
            }
        }
        return res;

    }


}