package me.empresta.feature_View_Network

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import me.empresta.*
import me.empresta.Navigation.BottomBar
import me.empresta.Navigation.BottomNavItem
import me.empresta.feature_QRCode_Connection.view.UserPreviewView
import me.empresta.feature_View_Network.view.NetworkView


@Composable
fun ScreenDisplayNetwork(navController: NavController,viewModel: NetworkView = hiltViewModel()) {

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    viewModel.getCommunity()

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            BottomBar(
                items = listOf(
                    BottomNavItem(name = "Feed", route = "Feed", icon = Icons.Default.Home),
                    BottomNavItem(name = "Qr", route = "ShowQR", icon = Icons.Default.QrCode),
                    BottomNavItem(name = "Qr", route = "ShowQR", icon = Icons.Default.QrCode),
                    BottomNavItem(name = "Profile", route = "Profile", icon = Icons.Default.Person)
                ),
                navController = navController,
                onItemClick = {
                    navController.navigate(it.route)
                }
            )
        }
        )
            {

                    innerPadding ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .background(Black)
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    if (viewModel.get().url != "") {

                        AndroidView(factory = {
                            WebView(it).apply {
                                layoutParams = ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT
                                )
                                webViewClient = WebViewClient()
                                settings.javaScriptEnabled = true

                                loadUrl(viewModel.get().url + "network/diagram?observer=" + viewModel.getAccount().publicKey)
                            }
                        }, update = {
                            it.loadUrl(viewModel.get().url + "network/diagram?observer=" + viewModel.getAccount().publicKey)
                        })

                    }
                    else {
                        CircularProgressIndicator(
                            color = BrightOrange,
                            strokeWidth = 5.dp,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

    }
}

