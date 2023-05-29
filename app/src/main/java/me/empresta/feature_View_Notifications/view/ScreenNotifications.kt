package me.empresta.feature_View_Notifications.view


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import me.empresta.Black
import me.empresta.BrightOrange
import me.empresta.DAO.AccountDao
import me.empresta.DI.Repository
import me.empresta.DarkGreen
import me.empresta.Navigation.BottomBar
import me.empresta.Navigation.BottomNavItem
import me.empresta.Navigation.EmprestameScreen
import me.empresta.PubSub.PubSub
import me.empresta.White
import javax.inject.Inject
import androidx.hilt.navigation.compose.hiltViewModel
import io.reactivex.Notification
import me.empresta.DAO.InfoRequest


@Composable
fun ScreenNotifications(
    navController: NavController,
    viewModel: NotificationViewModel = hiltViewModel()
){




    /*
    PubSub.Publish_Vouch("my_pub_key","other_pub_key","Vouch description", 0);
    PubSub.Publish_Item_Request("my_pub_key","Dnd Set","Vouch description");
    PubSub.Publish_Item_Announcement_Update("my_pub_key","Bikleta","Vouch description","image_url");
    PubSub.Publish_Item_Request("my_pub_key","Dnd Set","Vouch description");
    PubSub.Publish_Item_Announcement_Update("my_pub_key","Bikleta","Vouch description","image_url");
    PubSub.Publish_Vouch("my_pub_key","other_pub_key","Vouch description", 0);
    */

    //PubSub.Publish_Item_Announcement_Update("my_pub_key","Signature","Item Name", "Item Description", "Photo");
    //PubSub.Publish_Item_Request("my_pub_key","Signature","Item Name", "issuer_pub_key");



    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val scrollState = rememberScrollState()
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {navController.navigate(EmprestameScreen.Feed.name)}) {
                        Icon(Icons.Filled.ArrowBack, "backIcon")
                        }
                 },
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            text = "NOTIFICATIONS",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.h6.copy(
                                fontWeight = FontWeight.Light,
                                color = Color.White
                            )

                        )
                    }
                },
                actions = { // this is literally just padding to center the title lmao
                    IconButton(onClick = { /*navController.navigate(EmprestameScreen.Notifications.name)*/ }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Notifications", modifier = Modifier.alpha(0f))
                    }
                },)
        },
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
        }
    ) {
            innerPadding -> Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .background(Black)
            .fillMaxSize()
            .padding(top = 20.dp)
            .verticalScroll(scrollState)
            .padding(innerPadding)) {


        /*Text(
            text = "Notifications",
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold,
            color = White

        )*/

        Spacer(modifier = Modifier.size(48.dp))


        var infoRequestsFlow by remember { mutableStateOf<List<InfoRequest>?>(null) }


        LaunchedEffect(Unit) {

            val info = viewModel.getInfoRequests()
            infoRequestsFlow = viewModel.info
        }

        infoRequestsFlow?.forEach {
            singleNotification(
                name = it.sender,
                message= it.message,viewModel
            )

            Spacer(modifier = Modifier.size(20.dp))
        }


        }

    }

}


@Composable
fun singleNotification(
    //notification: Notification,
    name: String,
    message: String,
    viewModel:NotificationViewModel
    //onAccept: (String) -> Unit,
    //onRefuse: (String) -> Unit
    //account: AccountDao
){

    Box( // we cant round the row so we need to encapsulate it in a rounded box which will cut its corners
    modifier = Modifier
        .clip(RoundedCornerShape(4.dp))
        .width(300.dp)
    ) {
        Row(
            modifier = Modifier
                .border(width = 3.dp, color = BrightOrange)
                .padding(10.dp)
                .width(300.dp)
            //.padding(horizontal= 10.dp)
            //.padding(vertical= 10.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter("https://upload.wikimedia.org/wikipedia/commons/b/b5/1dayoldkitten.JPG"),
                contentDescription = "avatar",
                contentScale = ContentScale.Crop,            // crop the image if it's not a square
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)                       // clip to the circle shape
                    .border(2.dp, BrightOrange, CircleShape) // add a border (optional)
                //.clickable{/*send to profile maybe?*/},
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 5.dp)
            ){
                Text(
                    name + " requested your number",
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(bottom = 5.dp)
                )

                Text(
                    "'$message' - $name",
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(bottom = 5.dp)
                )

                Row(
                ) {
                    Button(
                        onClick = {

                            /*onAccept()*/;

                            viewModel.postInfo(name)

                            Log.d("yay", "botao 1")

                          },
                        content = {
                            Text(
                                text = "Accept",
                                color = White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = DarkGreen),
                        modifier = Modifier
                            .height(40.dp),
                        shape = RoundedCornerShape(15)
                    );


                    Button(
                        onClick = {

                            /*onRemove()*/;
                            Log.d("yay", "botao 2")

                        },
                        content = {
                            Text(
                                text = "Refuse",
                                color = White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                        modifier = Modifier
                            .height(40.dp)
                            .padding(horizontal = 5.dp),
                        shape = RoundedCornerShape(15)
                    )
                }
            }
        }
    }

}