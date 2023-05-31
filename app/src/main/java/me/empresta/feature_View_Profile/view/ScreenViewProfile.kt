package me.empresta.feature_View_Profile.view

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageButton
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import me.empresta.BrightOrange
import me.empresta.DAO.ItemAnnouncement
import me.empresta.DAO.ItemRequest
import me.empresta.Navigation.BottomBar
import me.empresta.Navigation.BottomNavItem
import me.empresta.Navigation.EmprestameScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenProfile(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    var count_image_clicks = 0
    var showDialog = remember { mutableStateOf(false) }

    val imageRequestModifier: (ItemRequest) -> Modifier = { item ->
        Modifier
            .width(205.dp)
            .height(149.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable {
            }
    }

    val imageAnnouncementModifier: (ItemAnnouncement) -> Modifier = { item ->
        Modifier
            .width(205.dp)
            .height(149.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable {
            }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(EmprestameScreen.Feed.name) }) {
                        Icon(Icons.Filled.ArrowBack, "backIcon")
                    }
                },
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "PROFILE",
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
        floatingActionButton = {
            FloatingActionButton(
                onClick = {showDialog.value = true},
                backgroundColor = BrightOrange,
                contentColor = Black){

                Icon(Icons.Filled.Add, "")
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
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
                    painter = rememberAsyncImagePainter("https://th.bing.com/th/id/OIP.8t1WtYLAPVB189hu7pCP3gHaHa?pid=ImgDet&rs=1"),
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

                Text(
                    text = "Inventory",
                    style = MaterialTheme.typography.h5.copy(
                        fontWeight = FontWeight.Light,
                        color = Color.White
                    ),
                    modifier = Modifier.padding(horizontal = 16.dp),
                    textAlign = TextAlign.Start
                )

                Spacer(modifier = Modifier.height(16.dp))

                val availableItemsToBorrow = viewModel.get_Items()

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
                            availableItemsToBorrow?.get(item)
                                ?.let { imageAnnouncementModifier(it) }?.let {
                                    Image(
                                        painter = rememberImagePainter(availableItemsToBorrow?.get(item)?.category),
                                        contentDescription = availableItemsToBorrow?.get(item)?.name,
                                        modifier = it
                                    )
                                }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                availableItemsToBorrow?.get(item)?.let {
                                    Text(
                                        text = it.name,
                                        style = MaterialTheme.typography.body2.copy(
                                            fontWeight = FontWeight.Thin,
                                            color = Color.White
                                        ),
                                        modifier = Modifier.padding(horizontal = 16.dp),
                                        textAlign = TextAlign.Start
                                    )
                                }
                                availableItemsToBorrow?.get(item)?.let {
                                    Text(
                                        text = it.user,
                                        style = MaterialTheme.typography.body2.copy(
                                            fontWeight = FontWeight.Thin,
                                            color = Color.White
                                        ),
                                        modifier = Modifier.padding(horizontal = 16.dp),
                                        textAlign = TextAlign.Start
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }


}


@Composable
fun CustomDialog(value: String, setShowDialog: (Boolean) -> Unit, setValue: (String) -> Unit) {
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val bitmap =  remember {
        mutableStateOf<Bitmap?>(null)
    }

    val launcher = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
    }
    val txtFieldError = remember { mutableStateOf("") }
    val txtField = remember { mutableStateOf(value) }
    val passwordVisible = remember { mutableStateOf(false) }
    val context = LocalContext.current
    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier
                    .padding(20.dp)
                    .size(600.dp)
                    .scrollable(rememberScrollState(), Orientation.Vertical)) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Create your Post",
                            style = MaterialTheme.typography.h5,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                        )
                        Icon(
                            imageVector = Icons.Filled.Cancel,
                            contentDescription = "",
                            tint = colorResource(me.empresta.R.color.darker_gray),
                            modifier = Modifier
                                .width(30.dp)
                                .height(30.dp)
                                .clickable { setShowDialog(false) }
                        )
                    }

                    Spacer(modifier = Modifier.size(10.dp))

                    Row() {
                        Button(onClick = { /*TODO*/ }) {
                            Text(text = "I need a ...")
                        }
                        Spacer(modifier = Modifier.size(10.dp))
                        Button(onClick = { /*TODO*/ }) {
                            Text(text = "I have a ...")
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                        ,
                        placeholder = { Text(text = "Title") },
                        value = txtField.value,
                        onValueChange = {
                            txtField.value = it.take(200)
                        },
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        shape = RoundedCornerShape(5.dp),
                        value = txtField.value,
                        placeholder = { Text(text = "Description") },

                        onValueChange = { txtField.value = it },
                        maxLines = 3,
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                        Button(
                            onClick = {
                                launcher.launch("image/*")
                            },
                            shape = RoundedCornerShape(50.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            Text(text = "Select an Image")
                        }
                    }

                    Spacer(modifier = Modifier.size(17.dp))

                    bitmap.value?.let {  btm ->
                        Row(horizontalArrangement = Arrangement.Center){
                            Image(bitmap = btm.asImageBitmap(),
                                contentDescription =null,
                                modifier = Modifier.size(30.dp))
                        }
                    }

                    imageUri?.let {
                        if (Build.VERSION.SDK_INT < 28) {
                            bitmap.value = MediaStore.Images
                                .Media.getBitmap(context.contentResolver,it)

                        } else {
                            val source = ImageDecoder
                                .createSource(context.contentResolver,it)
                            bitmap.value = ImageDecoder.decodeBitmap(source)
                        }
                    }

                    Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                        Button(
                            onClick = {
                                if (txtField.value.isEmpty()) {
                                    txtFieldError.value = "Field can not be empty"
                                    return@Button
                                }
                                setValue(txtField.value)
                                setShowDialog(false)

                            },
                            shape = RoundedCornerShape(50.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            Text(text = "Submit")
                        }
                    }
                }
            }
        }
    }
}