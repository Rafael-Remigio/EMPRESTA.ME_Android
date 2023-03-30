package me.empresta.feature_QRCode_Connection.use_case

import android.graphics.ImageFormat
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.zxing.BarcodeFormat
import com.google.zxing.BinaryBitmap
import com.google.zxing.DecodeHintType
import com.google.zxing.MultiFormatReader
import com.google.zxing.PlanarYUVLuminanceSource
import com.google.zxing.common.HybridBinarizer
import java.nio.ByteBuffer

class QRCodeAnalyser(private val onQRCodeScanned: (String) -> Unit) : ImageAnalysis.Analyzer {

    private val supportedImageFormats  = listOf(
        ImageFormat.YUV_420_888,
        ImageFormat.YUV_422_888,
        ImageFormat.YUV_444_888,
    )


    override fun analyze(image: ImageProxy) {
        if (supportedImageFormats.contains(image.format)){
            val bytes = image.planes.first().buffer.toByteArray()

            val source = PlanarYUVLuminanceSource(
                bytes,image.width,image.height,0,0,image.width,image.height,false
            )

            val binaryBitMap = BinaryBitmap(HybridBinarizer(source))

            try {
                val result = MultiFormatReader().apply {
                    setHints(
                        mapOf(
                            DecodeHintType.POSSIBLE_FORMATS to arrayListOf(
                                BarcodeFormat.QR_CODE
                            )
                        )
                    )
                }.decode(binaryBitMap)
                onQRCodeScanned(result.text)
            }
            catch (e: Exception){
                e.printStackTrace()
            } finally {
                image.close()
            }

        }

    }



    private fun ByteBuffer.toByteArray(): ByteArray {
        rewind()
        return ByteArray(remaining()).also { get(it) }
    }

}