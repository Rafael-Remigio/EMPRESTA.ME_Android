package me.empresta.feature_QRCode_Connection.view

import android.content.res.Resources
import android.graphics.Bitmap
import android.os.AsyncTask
import androidmads.library.qrgenearator.QRGEncoder
import androidx.lifecycle.ViewModel
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import me.empresta.DAO.Account
import me.empresta.DAO.AccountDao
import me.empresta.DAO.Community
import me.empresta.DI.Repository
import me.empresta.feature_register.use_case.RegisterUseCase
import okio.ByteString.Companion.encode
import java.util.Vector
import javax.inject.Inject

@HiltViewModel
class DisplayQRCodeView @Inject constructor(
    private val repository: Repository
) : ViewModel()
{


    // on below line we are creating
    // a variable for bitmap
    lateinit var bitmap: Bitmap
    // on below line we are creating
    // a variable for qr encoder.
    lateinit var qrEncoder: QRGEncoder


    @OptIn(DelicateCoroutinesApi::class)
    operator fun invoke(): Bitmap {


        // on below line we are getting
        // height and width of our point
        val width = getScreenWidth()
        val height = getScreenHeight()

        // on below line we are generating
        // dimensions for width and height
        var dimen = if (width < height) width else height
        dimen = dimen * 3 / 4

        // on below line we are initializing our qr encoder

        // on below line we are running a try
        // and catch block for initializing our bitmap
        try {
            // on below line we are
            // initializing our bitmap
            val writer = MultiFormatWriter()
            GlobalScope.launch{
                val personalAccount : Account
                        = getAccount()


                val array = ByteArray(10);
                val communities = getCommunities()
                var communityString: String = "";

                for (i in communities){
                    communityString.plus("{name:"+ i.name +";url: "+i.url+";},")
                }


                val bitMatrix = writer.encode("{NickName: \"" + personalAccount.NickName +"\"; Description: \""+personalAccount.Description+ "\"; PublicKey: \""+personalAccount.publicKey+"\"; Customization: \""+personalAccount.customization +"\"; Communities: [" +""  +  "]}", BarcodeFormat.QR_CODE, dimen,dimen)


            val barcodeEncoder = BarcodeEncoder()


            bitmap = barcodeEncoder.createBitmap(bitMatrix)
            }

            return bitmap

        } catch (e: Exception) {
            // on below line we
            // are handling exception
            e.printStackTrace()
        }
        return Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888)
    }


    private fun getScreenWidth(): Int {
        return Resources.getSystem().getDisplayMetrics().widthPixels
    }

    private fun getScreenHeight(): Int {
        return Resources.getSystem().getDisplayMetrics().heightPixels
    }

    private suspend fun getAccount():Account{
        return repository.getAccount()
    }


    suspend fun getCommunities():List<Community>{
        val data = repository.getCommunities()
        return data
    }

}