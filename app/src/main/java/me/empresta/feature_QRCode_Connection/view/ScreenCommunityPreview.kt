package me.empresta.feature_QRCode_Connection.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import me.empresta.Black
import me.empresta.BrightOrange
import me.empresta.Navigation.BottomBar
import me.empresta.Navigation.BottomNavItem
import me.empresta.Navigation.EmprestameScreen
import me.empresta.White


@Composable
fun ScreenCommunityPreview(
    navController: NavController,
    code: String,
    viewModel: CommunityPreviewView = hiltViewModel()
){

    val info = mutableSetOf(viewModel.state)
    viewModel.getInfo(code)
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            BottomBar(
                items = listOf(
                    BottomNavItem(name = "Feed", route = "Feed", icon = Icons.Default.Home),
                    BottomNavItem(name = "Qr", route = "ShowQR", icon = Icons.Default.QrCode),
                    BottomNavItem(name = "Network", route = "Network", icon = Icons.Default.AutoGraph),
                    BottomNavItem(name = "Qr", route = "ShowQR", icon = Icons.Default.QrCode)
                ),
                navController = navController,
                onItemClick = {
                    navController.navigate(it.route)
                }
            )
        }
    )

    {

            innerPadding -> Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .background(Black)
            .fillMaxSize()
            .padding(innerPadding)) {
        viewModel.get().title?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.h2,
                fontWeight = FontWeight.Bold,
                color = White,
                modifier = Modifier.padding(start = 5.dp, top = 100.dp)
            )
        }
        viewModel.get().bio?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Normal,
                color = White,
                modifier = Modifier.padding(start = 5.dp, top = 20.dp)

            )
        }

        Box(modifier = Modifier.padding(vertical = 60.dp))
        
        Button(onClick = { navController.navigate(EmprestameScreen.ReadQR.name) },
            content = {Text(text = "Join Community", color = White, fontWeight = FontWeight.Bold, fontSize = 15.sp)},
            colors = ButtonDefaults.buttonColors(backgroundColor = BrightOrange) ,
            modifier= Modifier
                .width(200.dp)
                .height(60.dp),
            shape = RoundedCornerShape(15)
            )

            }

    }

}