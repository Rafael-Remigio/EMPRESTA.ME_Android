package me.empresta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import me.empresta.feature_QRCode_Connection.view.ScreenDisplayQRCode
import me.empresta.feature_QRCode_Connection.view.ScreenReadQRCode
import me.empresta.feature_register.view.ScreenRegister

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CleanArchitecture {
                ScreenReadQRCode()
            }
        }
    }
}

@Preview
@Composable
fun SimpleComposablePreview() {
    ScreenDisplayQRCode()
}