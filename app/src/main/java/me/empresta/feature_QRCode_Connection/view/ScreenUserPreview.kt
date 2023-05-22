package me.empresta.feature_QRCode_Connection.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.rounded.School
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import me.empresta.Black
import me.empresta.BrightOrange
import me.empresta.Navigation.BottomBar
import me.empresta.Navigation.BottomNavItem
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

        Box(modifier = Modifier.padding(vertical = 60.dp))

                Button(
                    onClick = {
                        if (viewModel.connect()){
                            Toast.makeText(context,"Vouched successfully",Toast.LENGTH_SHORT).show()
                        }
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
                        .width(200.dp)
                        .height(60.dp),
                    shape = RoundedCornerShape(15)
                )



    }

    }

}