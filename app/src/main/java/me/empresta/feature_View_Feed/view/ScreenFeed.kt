package me.empresta.feature_View_Feed.view


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.empresta.*
import me.empresta.Navigation.BottomBar
import me.empresta.Navigation.BottomNavItem
import me.empresta.Navigation.EmprestameScreen


@Composable
fun ScreenFeed(
    navController: NavController){


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
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(Black)
            .fillMaxSize()
            .padding(innerPadding)) {
        Text(
            text = "Hello!",
            style = MaterialTheme.typography.h2,
            fontWeight = FontWeight.Bold,
            color = White
        )


        Button(onClick = { navController.navigate(EmprestameScreen.ShowQR.name)},
            content = {Text(text = "ShowQRCode", color = White, fontWeight = FontWeight.Bold, fontSize = 15.sp)},
            colors = ButtonDefaults.buttonColors(backgroundColor = BrightOrange) ,
            modifier=Modifier.width(200.dp).height(60.dp),
            shape = RoundedCornerShape(15)
        )


        Button(onClick = { navController.navigate(EmprestameScreen.ReadQR.name) },
            content = {Text(text = "ScanQRCode", color = White, fontWeight = FontWeight.Bold, fontSize = 15.sp)},
            colors = ButtonDefaults.buttonColors(backgroundColor = BrightOrange) ,
            modifier=Modifier.width(200.dp).height(60.dp),
            shape = RoundedCornerShape(15)
        )

    }

    }

}