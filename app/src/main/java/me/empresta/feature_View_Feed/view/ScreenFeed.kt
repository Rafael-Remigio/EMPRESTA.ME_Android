package me.empresta.feature_View_Feed.view

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import me.empresta.*
import me.empresta.DI.Repository
import me.empresta.Navigation.BottomBar
import me.empresta.Navigation.BottomNavItem
import me.empresta.feature_View_Feed.view.model.AvailableItem
import me.empresta.PubSub.PubSub
import javax.inject.Inject
import me.empresta.DAO.ItemAnnouncement
import me.empresta.DAO.ItemRequest
import me.empresta.feature_QRCode_Connection.view.DisplayQRCodeView


@Composable
fun ScreenFeed(navController: NavController, viewModel: feedViewModel = hiltViewModel()) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val imageModifier = Modifier
        .width(205.dp)
        .height(149.dp)
        .clip(RoundedCornerShape(20.dp))
        .clickable {
            navController.navigate("itemPage")
        }
    val availableItemsToLend = listOf(
        AvailableItem("Bike", "https://th.bing.com/th/id/R.5bcdf75c71d0e9fd9cc8142fcb3307e3?rik=vknt2Qh16ObY0g&riu=http%3a%2f%2f1.bp.blogspot.com%2f-ADXEt7Ian14%2fTkke0CXr5qI%2fAAAAAAAAAC4%2fbhkwY7YYRL4%2fs1600%2fDSC00108.jpg&ehk=Grp1rsJauyAnZGkGd72mW2gI6O0qqXyjajhjfXX6HrY%3d&risl=&pid=ImgRaw&r=0"),
        AvailableItem("Bike", "https://th.bing.com/th/id/R.5bcdf75c71d0e9fd9cc8142fcb3307e3?rik=vknt2Qh16ObY0g&riu=http%3a%2f%2f1.bp.blogspot.com%2f-ADXEt7Ian14%2fTkke0CXr5qI%2fAAAAAAAAAC4%2fbhkwY7YYRL4%2fs1600%2fDSC00108.jpg&ehk=Grp1rsJauyAnZGkGd72mW2gI6O0qqXyjajhjfXX6HrY%3d&risl=&pid=ImgRaw&r=0"),
        AvailableItem("Bike", "https://th.bing.com/th/id/R.5bcdf75c71d0e9fd9cc8142fcb3307e3?rik=vknt2Qh16ObY0g&riu=http%3a%2f%2f1.bp.blogspot.com%2f-ADXEt7Ian14%2fTkke0CXr5qI%2fAAAAAAAAAC4%2fbhkwY7YYRL4%2fs1600%2fDSC00108.jpg&ehk=Grp1rsJauyAnZGkGd72mW2gI6O0qqXyjajhjfXX6HrY%3d&risl=&pid=ImgRaw&r=0"),
        AvailableItem("Bike", "https://th.bing.com/th/id/R.5bcdf75c71d0e9fd9cc8142fcb3307e3?rik=vknt2Qh16ObY0g&riu=http%3a%2f%2f1.bp.blogspot.com%2f-ADXEt7Ian14%2fTkke0CXr5qI%2fAAAAAAAAAC4%2fbhkwY7YYRL4%2fs1600%2fDSC00108.jpg&ehk=Grp1rsJauyAnZGkGd72mW2gI6O0qqXyjajhjfXX6HrY%3d&risl=&pid=ImgRaw&r=0"),
        AvailableItem("Bike", "https://th.bing.com/th/id/R.5bcdf75c71d0e9fd9cc8142fcb3307e3?rik=vknt2Qh16ObY0g&riu=http%3a%2f%2f1.bp.blogspot.com%2f-ADXEt7Ian14%2fTkke0CXr5qI%2fAAAAAAAAAC4%2fbhkwY7YYRL4%2fs1600%2fDSC00108.jpg&ehk=Grp1rsJauyAnZGkGd72mW2gI6O0qqXyjajhjfXX6HrY%3d&risl=&pid=ImgRaw&r=0"),
    )
    val availableItemsToBorrow = listOf(
        AvailableItem("Bike", "https://th.bing.com/th/id/R.5bcdf75c71d0e9fd9cc8142fcb3307e3?rik=vknt2Qh16ObY0g&riu=http%3a%2f%2f1.bp.blogspot.com%2f-ADXEt7Ian14%2fTkke0CXr5qI%2fAAAAAAAAAC4%2fbhkwY7YYRL4%2fs1600%2fDSC00108.jpg&ehk=Grp1rsJauyAnZGkGd72mW2gI6O0qqXyjajhjfXX6HrY%3d&risl=&pid=ImgRaw&r=0"),
        AvailableItem("Bike", "https://th.bing.com/th/id/R.5bcdf75c71d0e9fd9cc8142fcb3307e3?rik=vknt2Qh16ObY0g&riu=http%3a%2f%2f1.bp.blogspot.com%2f-ADXEt7Ian14%2fTkke0CXr5qI%2fAAAAAAAAAC4%2fbhkwY7YYRL4%2fs1600%2fDSC00108.jpg&ehk=Grp1rsJauyAnZGkGd72mW2gI6O0qqXyjajhjfXX6HrY%3d&risl=&pid=ImgRaw&r=0"),
        AvailableItem("Bike", "https://th.bing.com/th/id/R.5bcdf75c71d0e9fd9cc8142fcb3307e3?rik=vknt2Qh16ObY0g&riu=http%3a%2f%2f1.bp.blogspot.com%2f-ADXEt7Ian14%2fTkke0CXr5qI%2fAAAAAAAAAC4%2fbhkwY7YYRL4%2fs1600%2fDSC00108.jpg&ehk=Grp1rsJauyAnZGkGd72mW2gI6O0qqXyjajhjfXX6HrY%3d&risl=&pid=ImgRaw&r=0"),
        AvailableItem("Bike", "https://th.bing.com/th/id/R.5bcdf75c71d0e9fd9cc8142fcb3307e3?rik=vknt2Qh16ObY0g&riu=http%3a%2f%2f1.bp.blogspot.com%2f-ADXEt7Ian14%2fTkke0CXr5qI%2fAAAAAAAAAC4%2fbhkwY7YYRL4%2fs1600%2fDSC00108.jpg&ehk=Grp1rsJauyAnZGkGd72mW2gI6O0qqXyjajhjfXX6HrY%3d&risl=&pid=ImgRaw&r=0"),
        AvailableItem("Bike", "https://th.bing.com/th/id/R.5bcdf75c71d0e9fd9cc8142fcb3307e3?rik=vknt2Qh16ObY0g&riu=http%3a%2f%2f1.bp.blogspot.com%2f-ADXEt7Ian14%2fTkke0CXr5qI%2fAAAAAAAAAC4%2fbhkwY7YYRL4%2fs1600%2fDSC00108.jpg&ehk=Grp1rsJauyAnZGkGd72mW2gI6O0qqXyjajhjfXX6HrY%3d&risl=&pid=ImgRaw&r=0"),
    )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "TIMELINE",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.h6.copy(
                                fontWeight = FontWeight.Light,
                                color = Color.White
                            )
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* Handle notifications icon click */ }) {
                        Icon(Icons.Default.Notifications, contentDescription = "Notifications")
                    }
                },
            )
        },
        bottomBar = {
            BottomBar(
                items = listOf(
                    BottomNavItem(name = "Feed", route = "Feed", icon = Icons.Default.Home),
                    BottomNavItem(name = "Qr", route = "ShowQR", icon = Icons.Default.QrCode),
                    BottomNavItem(name = "Network", route = "Network", icon = Icons.Default.AutoGraph),
                    BottomNavItem(name = "Qr", route = "ShowQR", icon = Icons.Default.Person)
                ),
                navController = navController,
                onItemClick = {
                    navController.navigate(it.route)
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(color = MaterialTheme.colors.primaryVariant)
            ) {
                Text(
                    modifier = Modifier
                        .padding(16.dp),
                    text = "Welcome to My App!",
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.onPrimary
                )
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .background(Black)
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            TextField(
                value = "",
                onValueChange = { /* Handle value change */ },
                modifier = Modifier
                    .width(370.dp)
                    .padding(horizontal = 16.dp)
                    .background(Color.DarkGray, RoundedCornerShape(12.dp)),
                textStyle = TextStyle(color = Color.Black),
                placeholder = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Search Icon",
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(
                            text = "Search for an icon",
                            style = MaterialTheme.typography.body1,
                            color = Color.Gray
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyRow {
                items(listOf("Books", "Electronics", "Tools", "Kitchen")) { category ->
                    Button(
                        onClick = { /* Handle button click */ },
                        modifier = Modifier
                            .padding(start = 14.dp, end = 14.dp)
                            .width(120.dp)
                            .clip(RoundedCornerShape(18.dp))
                            .background(Color.LightGray) // Set the button background color
                    ) {
                        Text(
                            text = category,
                            color = Color.Black, // Set the text color to black
                            style = MaterialTheme.typography.body2 // Set the text style to a smaller size
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(26.dp))

            Text(
                text = "Do you have this item?",
                style = MaterialTheme.typography.h5.copy(
                    fontWeight = FontWeight.Light,
                    color = Color.White
                ),
                modifier = Modifier.padding(horizontal = 16.dp),
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(5.dp))

            // Horizontal scrolling showcase
            LazyRow(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
            ) {
                val imageModifier = Modifier
                    .width(205.dp)
                    .height(149.dp)
                    .clip(RoundedCornerShape(20.dp))
                items(4) { item ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    ) {
                        Image(
                            painter = rememberImagePainter(availableItemsToLend[item].imageUrl),
                            contentDescription = availableItemsToLend[item].name,
                            modifier = imageModifier
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = availableItemsToLend[item].name,
                                style = MaterialTheme.typography.body2.copy(
                                    fontWeight = FontWeight.Light,
                                    color = Color.White
                                ),
                                modifier = Modifier.padding(horizontal = 16.dp),
                                textAlign = TextAlign.Start
                            )
                            Text(
                                text = "32 minutes ago",
                                style = MaterialTheme.typography.body2.copy(
                                    fontWeight = FontWeight.ExtraLight,
                                    color = Color.White
                                ),
                                modifier = Modifier.padding(horizontal = 16.dp),
                                textAlign = TextAlign.End
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(26.dp))

            Text(
                text = "Looking for an item?",
                style = MaterialTheme.typography.h5.copy(
                    fontWeight = FontWeight.Light,
                    color = Color.White
                ),
                modifier = Modifier.padding(horizontal = 16.dp),
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(5.dp))

            // Horizontal scrolling showcase
            LazyRow(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
            ) {
                items(4) { item ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    ) {
                        Image(
                            painter = rememberImagePainter(availableItemsToBorrow[item].imageUrl),
                            contentDescription = availableItemsToBorrow[item].name,
                            modifier = imageModifier
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = availableItemsToLend[item].name,
                                style = MaterialTheme.typography.body2.copy(
                                    fontWeight = FontWeight.Thin,
                                    color = Color.White
                                ),
                                modifier = Modifier.padding(horizontal = 16.dp),
                                textAlign = TextAlign.Start
                            )
                            Text(
                                text = "32 minutes ago",
                                style = MaterialTheme.typography.body2.copy(
                                    fontWeight = FontWeight.ExtraLight,
                                    color = Color.White
                                ),
                                modifier = Modifier.padding(horizontal = 16.dp),
                                textAlign = TextAlign.End
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { startIDPoauth2(context) },
                content = {
                    Text(
                        text = "IDP",
                        color = White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = BrightOrange),
                modifier = Modifier
                    .width(200.dp)
                    .height(60.dp),
                shape = RoundedCornerShape(15)
            )

        }
    }
}

// Starting the IDP oauth2 flow
fun startIDPoauth2(context: Context) {
    val auth = IDPAuthenticator(context)
    val apiService = auth.createApiService()
    auth.associateWithIDP(apiService, context)
}
