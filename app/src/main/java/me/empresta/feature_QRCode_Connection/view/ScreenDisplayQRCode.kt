package me.empresta.feature_QRCode_Connection.view

import android.graphics.Bitmap
import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import me.empresta.Black
import me.empresta.BrightOrange
import me.empresta.Grey
import me.empresta.Navigation.BottomBar
import me.empresta.Navigation.BottomNavItem
import me.empresta.Navigation.EmprestameScreen
import me.empresta.White


@Composable
fun ScreenDisplayQRCode(navController: NavController,
                        viewModel: DisplayQRCodeView = hiltViewModel()
    ) {


    val bitmap: Bitmap = viewModel.invoke()
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
                    text = "Share your QRCode to make a connection",
                    style = MaterialTheme.typography.h5,
                    color = Grey,
                    textAlign = TextAlign.Center,

                )
                Box(modifier = Modifier.padding(15.dp))

                Image(bitmap = bitmap.asImageBitmap(), contentDescription = "")

                Box(modifier = Modifier.padding(15.dp))

                Button(onClick = { navController.navigate(EmprestameScreen.ReadQR.name) },
                    content = {Text(text = "Scan a Code", color = White, fontWeight = FontWeight.Bold, fontSize = 15.sp)},
                    colors = ButtonDefaults.buttonColors(backgroundColor = BrightOrange) ,
                    modifier=Modifier.width(200.dp).height(60.dp),
                    shape = RoundedCornerShape(15)
                )

    }

    }


}

