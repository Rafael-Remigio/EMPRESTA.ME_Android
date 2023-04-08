package me.empresta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import me.empresta.feature_QRCode_Connection.view.ScreenDisplayQRCode
import me.empresta.feature_QRCode_Connection.view.ScreenReadQRCode
import me.empresta.feature_register.view.ScreenRegister
import androidx.compose.material.Surface
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.room.Room
import me.empresta.DAO.AccountDao
import me.empresta.DAO.Database
import me.empresta.feature_View_Feed.view.ScreenFeed
import me.empresta.feature_register.use_case.RegisterUseCase

class MainActivity : ComponentActivity() {

    /* TODO */
    // I need to change to a better way of handling dependency injection
    private val db by lazy {
        Room.databaseBuilder(
            context = applicationContext,
            Database::class.java,
            "EMPRESTAMEBB"
        ).build()
    }



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
                }
            }
        }
    }
}
}


