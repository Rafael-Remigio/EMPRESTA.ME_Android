package me.empresta.feature_QRCode_Connection.view

import android.content.res.Resources
import android.graphics.Bitmap
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder


class DisplayQRCodeView {

    //private var static ConnectionInfo: "{Name: Name filler ; Public Key: 123456}"

    // on below line we are creating
    // a variable for bitmap
    lateinit var bitmap: Bitmap
    // on below line we are creating
    // a variable for qr encoder.
    lateinit var qrEncoder: QRGEncoder


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

            val bitMatrix = writer.encode("{Name: Name filler ; Public Key: 123456}", BarcodeFormat.QR_CODE, dimen,dimen)

            val barcodeEncoder = BarcodeEncoder()


            bitmap = barcodeEncoder.createBitmap(bitMatrix)

            return bitmap

        } catch (e: Exception) {
            // on below line we
            // are handling exception
            e.printStackTrace()
        }
        return Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888)
    }


    fun getScreenWidth(): Int {
        return Resources.getSystem().getDisplayMetrics().widthPixels
    }

    fun getScreenHeight(): Int {
        return Resources.getSystem().getDisplayMetrics().heightPixels
    }

}