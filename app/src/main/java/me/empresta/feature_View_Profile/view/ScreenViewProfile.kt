package me.empresta.feature_View_Profile.view

import android.util.Log
import android.widget.ImageButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.R
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import me.empresta.Black
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import me.empresta.BrightOrange
import me.empresta.Navigation.BottomBar
import me.empresta.Navigation.BottomNavItem
import me.empresta.Navigation.EmprestameScreen

@Composable
fun ScreenProfile(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    var count_image_clicks = 0


    Scaffold(
        topBar = {TopAppBar(navigationIcon = {
            IconButton(onClick = {}) {
                Icon(Icons.Filled.ArrowBack, "backIcon")
            }
        },title = {Text("Profile", textAlign = TextAlign.Center)}, backgroundColor = BrightOrange,contentColor = Black, actions = {
            IconButton(onClick = {navController.navigate(EmprestameScreen.Notifications.name)}) {
                Icon(Icons.Filled.Notifications, null)
            }})},
        scaffoldState = scaffoldState,
        bottomBar = {
            BottomBar(
                items = listOf(
                    BottomNavItem(name = "Feed", route = "Feed", icon = Icons.Default.Home),
                    BottomNavItem(name = "Qr", route = "ShowQR", icon = Icons.Default.QrCode),
                    BottomNavItem(name = "Network", route = "Network", icon = Icons.Default.AutoGraph),
                    BottomNavItem(name = "Profile", route = "Profile", icon = Icons.Default.Person)
                ),
                navController = navController,
                onItemClick = {
                    navController.navigate(it.route)
                }
            )
        },
        floatingActionButton = { FloatingActionButton(onClick = { /* ... */ }, backgroundColor = BrightOrange,) {
                Icon(Icons.Filled.AddCard, null)
            }
        }
    )
    {

            innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .background(Black)
                .fillMaxSize()
                .padding(innerPadding).padding(horizontal = 20.dp)
        ) {
            if (viewModel.reached){
                Spacer(modifier = Modifier.size(48.dp))


                    Image(
                        painter = rememberAsyncImagePainter("https://upload.wikimedia.org/wikipedia/commons/b/b5/1dayoldkitten.JPG"),
                        contentDescription = "avatar",
                        contentScale = ContentScale.Crop,            // crop the image if it's not a square
                        modifier = Modifier
                            .size(128.dp)
                            .clip(CircleShape)                       // clip to the circle shape
                            .border(2.dp, BrightOrange, CircleShape) // add a border (optional)
                            .clickable{
                                count_image_clicks++
                                if (count_image_clicks > 100) {
                                    Log.d("yay", "amogus")
                                }
                              },
                    )

                Spacer(modifier = Modifier.size(10.dp))
                Text(text = viewModel.viewInfo().NickName)
                Text(text = viewModel.viewInfo().publicKey.substring(startIndex = 0, endIndex = 33))
                Text(text = viewModel.viewInfo().contactInfo)


            }
        }
    }


}



@Preview
@Composable
fun previewAppBar(){
    TopAppBar(navigationIcon = {
        IconButton(onClick = {}) {
            Icon(Icons.Filled.ArrowBack, "backIcon")
        }
        },title = {Text("Profile", textAlign = TextAlign.Center)}, backgroundColor = BrightOrange,contentColor = Black, actions = {
        IconButton(onClick = {/* Do Something*/ }) {
            Icon(Icons.Filled.Notifications, null)
        }})
}