package me.empresta.feature_QRCode_Connection.view

import android.os.AsyncTask
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.rounded.School
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import me.empresta.Black
import me.empresta.BrightOrange
import me.empresta.EMPRESTAME
import me.empresta.Grey
import me.empresta.Navigation.BottomBar
import me.empresta.Navigation.BottomNavItem
import me.empresta.Navigation.EmprestameScreen
import me.empresta.White
import org.json.JSONArray
import org.json.JSONObject



@Composable
fun ScreenUserPreview(
    navController: NavController,
    code: String,
    viewModel: UserPreviewView = hiltViewModel()
){

    // set the user connection variables
    val jsonObj = JSONObject(code)
    val map = jsonObj.toMap()
    val nickname: String = map["NickName"] as String
    val description: String = map["Description"] as String
    val pubKey: String = map["PublicKey"] as String
    val communities: List<LinkedHashMap<String,Any>> = map["Communities"] as List<LinkedHashMap<String,Any>>



    // composable context
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()


    //text field


    LaunchedEffect(Unit) {
        viewModel
            .toastMessage
            .collect { message ->
                Toast.makeText(
                    context,
                    message,
                    Toast.LENGTH_SHORT,
                ).show()

                navController.navigate(EmprestameScreen.Feed.name)
            }
    }

    var vouchDescription by remember { mutableStateOf(TextFieldValue("")) }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(EmprestameScreen.ShowQR.name) }) {
                        Icon(Icons.Filled.ArrowBack, "backIcon")
                    }
                },
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "VOUCHING",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.h6.copy(
                                fontWeight = FontWeight.Light,
                                color = Color.White
                            )
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate(EmprestameScreen.Notifications.name) }) {
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



        Text(
            text = nickname,
            style = MaterialTheme.typography.h2,
            fontWeight = FontWeight.Bold,
            color = White,
            modifier = Modifier.padding(start = 5.dp, top = 100.dp)
        )

        Text(
            text = description,
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Normal,
            color = White,
            modifier = Modifier.padding(start = 5.dp, top = 20.dp)
        )

        Text(
            text = pubKey,
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Normal,
            color = White,
            modifier = Modifier.padding(start = 5.dp, top = 20.dp)
        )

        if (communities.isNotEmpty()){


        Text(
            text = "Communities",
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Normal,
            color = White,
            modifier = Modifier.padding(start = 5.dp, top = 20.dp)
        )

        Column(Modifier.border(2.dp, BrightOrange,shape = RoundedCornerShape(15))) {
            Spacer(modifier = Modifier.size(60.dp,5.dp))
            communities.forEach { community ->
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(all = 10.dp)) {
                    Column {
                        Text(community.get("name") as String, fontWeight = FontWeight.Bold)
                        Text(community.get("url") as String)
                    }
                }
            }
            Spacer(modifier = Modifier.size(20.dp,5.dp))

        }
        }
        OutlinedTextField(
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = BrightOrange,
                unfocusedBorderColor = Grey
            ),
            value = vouchDescription,
            onValueChange = {
                vouchDescription = it
            },
            label = { Text(text = "Description of Positive Vouch", color = BrightOrange)},
        )

        Row(modifier = Modifier.padding(vertical = 60.dp)){


                Button(
                    onClick = {

                            viewModel.connect(pubKey, communities,vouchDescription.text,1)
                                                 },
                    content = {
                        Text(
                            text = "Vouch",
                            color = White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = BrightOrange),
                    modifier = Modifier
                        .width(150.dp)
                        .height(60.dp),
                    shape = RoundedCornerShape(15)
                )


                Button(
                    onClick = {
                     viewModel.connect(pubKey, communities, vouchDescription.text,-1)
                    },
                    content = {
                    Text(
                        text = "Disapprove",
                        color = White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = BrightOrange),
                    modifier = Modifier
                    .width(150.dp)
                    .height(60.dp),
                    shape = RoundedCornerShape(15)
                )
        }

    }

    }

}