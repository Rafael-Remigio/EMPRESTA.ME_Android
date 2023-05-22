package me.empresta

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import me.empresta.feature_QRCode_Connection.view.ScreenDisplayQRCode
import me.empresta.feature_QRCode_Connection.view.ScreenReadQRCode
import me.empresta.feature_register.view.ScreenRegister
import androidx.compose.material.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import me.empresta.Navigation.EmprestameScreen
import me.empresta.feature_QRCode_Connection.view.ScreenCommunityPreview
import me.empresta.feature_QRCode_Connection.view.ScreenUserPreview
import me.empresta.feature_View_Feed.view.ScreenFeed
import me.empresta.feature_View_Network.ScreenDisplayNetwork
import me.empresta.feature_View_Profile.view.ScreenProfile

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
        CleanArchitecture {
            Surface(
                color = MaterialTheme.colors.background
            ) {
                val navController = rememberNavController()
                NavHost(navController = navController,
                    startDestination = EmprestameScreen.Start.name
                ) {
                    composable(route = EmprestameScreen.Start.name) {
                        ScreenRegister(navController = navController)
                    }
                    composable(route = EmprestameScreen.Feed.name) {
                        ScreenFeed(navController = navController)
                    }
                    composable(route = EmprestameScreen.ShowQR.name) {
                        ScreenDisplayQRCode(navController = navController)
                    }
                    composable(route = EmprestameScreen.ReadQR.name) {
                        ScreenReadQRCode(navController = navController)
                    }
                    composable(route = EmprestameScreen.Network.name) {
                        ScreenDisplayNetwork(navController = navController)
                    }
                    composable(route = EmprestameScreen.CommunityPreview.name+"/{code}?usesIDP={usesIDP}"  ,  arguments = listOf(navArgument("usesIDP") { defaultValue = "false" }))
                    {backStackEntry ->
                        val code = backStackEntry.arguments?.getString("code")
                        if (code != null) {
                            ScreenCommunityPreview(navController = navController,  code, usesIDP = true )
                        }
                    }
                    composable(route = EmprestameScreen.UserPreview.name+"/{code}")
                    {backStackEntry ->
                        var code = backStackEntry.arguments?.getString("code")
                        // decode it from string Uri string format to a Json String
                        code =  Uri.decode(code)
                        if (code != null) {
                            ScreenUserPreview(navController = navController, code )
                        }
                    }
                    composable(route = EmprestameScreen.Profile.name) {
                        ScreenProfile(navController = navController)
                    }
                }
            }
        }
    }
}
}


