package me.empresta.feature_View_Feed.view

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import me.empresta.*
import me.empresta.Navigation.BottomBar
import me.empresta.Navigation.BottomNavItem
import me.empresta.DAO.ItemAnnouncement
import me.empresta.DAO.ItemRequest
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.window.Dialog
import coil.compose.rememberAsyncImagePainter
import me.empresta.R
import me.empresta.Navigation.EmprestameScreen


@Composable
fun ScreenFeed(navController: NavController, viewModel: feedViewModel = hiltViewModel()) {

    viewModel.getData()

    // Create a mutable state variable to track if the popup is shown
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val showDialog =  remember { mutableStateOf(false) }
    if(showDialog.value){
        CustomDialog(value = "", setShowDialog = {
            showDialog.value = it
        }) {

        }
    }

    var thislending = remember { mutableStateOf(ItemAnnouncement("","","")) }
    val showLending =  remember { mutableStateOf(false) }

    var thisRequesting = remember { mutableStateOf(ItemRequest("","","")) }
    val showRequesting =  remember { mutableStateOf(false) }

    val imageRequestModifier: (ItemRequest) -> Modifier = { item ->
        Modifier
            .width(205.dp)
            .height(149.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable {
                // Handle the click event here, using the 'item' parameter
                showRequesting.value = true
                thisRequesting.value = item
            }
    }

    val imageAnnouncementModifier: (ItemAnnouncement) -> Modifier = { item ->
        Modifier
            .width(205.dp)
            .height(149.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable {
                // Handle the click event here, using the 'item' parameter
                showLending.value = true
                thislending.value = item
            }
    }

    if(showLending.value){

            LendingDialog(thislending.value,value = "", setShowDialog = {
                showLending.value = it
            }) {

            }
    }

    if(showRequesting.value){

        BorrowingDialog(thisRequesting.value,value = "", setShowDialog = {
            showLending.value = it
        }) {

        }
    }

    /*
    // Define mutable state variables
    val availableItemsToLend = mutableStateOf<List<ItemRequest>>(emptyList())
    val availableItemsToBorrow = mutableStateOf<List<ItemAnnouncement>>(emptyList())

    Log.d("GET ITEM", "Getting Available Items and Announcements")

    // Update the mutable state variables
    availableItemsToLend.value = viewModel.getItemRequests()!!
    availableItemsToBorrow.value = viewModel.getItemAnnouncements()!!

    Log.d("TO LEND", "Available Items to lend ${availableItemsToLend.value}")
    Log.d("TO BORROW", "Available Items to lend ${availableItemsToBorrow.value}")
    */

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
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(7.dp),
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

            Text(
                text = "Looking for an item?",
                style = MaterialTheme.typography.h5.copy(
                    fontWeight = FontWeight.Light,
                    color = Color.White
                ),
                modifier = Modifier.padding(horizontal = 16.dp),
                textAlign = TextAlign.Start
            )


            Spacer(modifier = Modifier.height(16.dp))


            val availableItemsToBorrow = viewModel.get_ItemAnnouncements()
            val availableItemsToLend = viewModel.get_ItemRequests()
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
                                    painter = rememberImagePainter(availableItemsToBorrow?.get(item)?.image),
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


            Spacer(modifier = Modifier.height(25.dp))

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
                val availableItemsToBorrow = viewModel.get_ItemAnnouncements()
                val availableItemsToLend = viewModel.get_ItemRequests()
                Log.d("AVSJIHSIHQS", "AAAAAAAAAA $availableItemsToBorrow")

                Log.d("AVSJIHSIHQS", "BBBBBBBBBBBBB $availableItemsToLend")
                val imageModifier = Modifier
                    .width(205.dp)
                    .height(149.dp)
                    .clip(RoundedCornerShape(20.dp))
                items(1) { item ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    ) {
                        availableItemsToLend?.get(item)
                            ?.let { imageRequestModifier(it) }?.let {
                                Image(
                                    painter = rememberImagePainter("https://s.aolcdn.com/hss/storage/midas/1b8e6c255e1632157aaaf5bd05d9c8/205127975/06-blender-2000.jpg"),
                                    contentDescription = availableItemsToLend?.get(item)?.name,
                                    modifier = it
                                )
                            }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            availableItemsToLend?.get(item)?.let {
                                Text(
                                    text = it.name,
                                    style = MaterialTheme.typography.body2.copy(
                                        fontWeight = FontWeight.Light,
                                        color = Color.White
                                    ),
                                    modifier = Modifier.padding(horizontal = 16.dp),
                                    textAlign = TextAlign.Start
                                )
                            }
                            availableItemsToLend?.get(item)?.let {
                                Text(
                                    text = it.user,
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
            }
        }
    }
}

@Composable
fun LendingDialog(item:ItemAnnouncement, value: String, setShowDialog: (Boolean) -> Unit, setValue: (String) -> Unit) {
    val txtFieldError = remember { mutableStateOf("") }
    val txtField = remember { mutableStateOf(value) }
    val context = LocalContext.current
    Dialog(onDismissRequest = { setShowDialog(false) }) {
        androidx.compose.material3.Surface(
            shape = RoundedCornerShape(16.dp),
        ) {
            Box(
                contentAlignment = Alignment.Center,
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
                            text = "Lending",
                            style = MaterialTheme.typography.h5,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                        )
                        Icon(
                            imageVector = Icons.Filled.Cancel,
                            contentDescription = "",
                            tint = colorResource(R.color.darker_gray),
                            modifier = Modifier
                                .width(30.dp)
                                .height(30.dp)
                                .clickable { setShowDialog(false) }
                        )
                    }
                    Spacer(modifier = Modifier.size(10.dp))

                    Image(
                        painter = rememberAsyncImagePainter(item.image),
                        contentDescription = "",
                        contentScale = ContentScale.FillBounds,            // crop the image if it's not a square
                        modifier = Modifier
                            .size(400.dp, 300.dp)
                            .clip(RoundedCornerShape(CornerSize(20.dp)))  // add a border (optional)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    item.name?.let {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = it,
                            style = MaterialTheme.typography.h5,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Avatar",
                            tint = Color.Black,
                            modifier = Modifier
                                .size(50.dp)
                                .clip(RoundedCornerShape(20.dp))
                        )
                        Column() {
                            Text(text="Peterson")
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))

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
                            Text(text = "Request")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BorrowingDialog(item:ItemRequest, value: String, setShowDialog: (Boolean) -> Unit, setValue: (String) -> Unit) {
    val txtFieldError = remember { mutableStateOf("") }
    val txtField = remember { mutableStateOf(value) }
    val context = LocalContext.current
    Dialog(onDismissRequest = { setShowDialog(false) }) {
        androidx.compose.material3.Surface(
            shape = RoundedCornerShape(16.dp),
        ) {
            Box(
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .size(600.dp)
                        .scrollable(rememberScrollState(), Orientation.Vertical)
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Request",
                            style = MaterialTheme.typography.h5,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                        )
                        Icon(
                            imageVector = Icons.Filled.Cancel,
                            contentDescription = "",
                            tint = colorResource(R.color.darker_gray),
                            modifier = Modifier
                                .width(30.dp)
                                .height(30.dp)
                                .clickable { setShowDialog(false) }
                        )
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                    Spacer(modifier = Modifier.height(20.dp))

                    item.name?.let {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = it,
                            style = MaterialTheme.typography.h5,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Avatar",
                            tint = Color.Black,
                            modifier = Modifier
                                .size(50.dp)
                                .clip(RoundedCornerShape(20.dp))
                        )
                        Column() {
                            Text(text = "Peterson")
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))

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
                            Text(text = "Borrow")
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
                            tint = colorResource(R.color.darker_gray),
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