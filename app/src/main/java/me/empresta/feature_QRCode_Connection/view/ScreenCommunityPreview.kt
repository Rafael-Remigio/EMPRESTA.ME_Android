package me.empresta.feature_QRCode_Connection.view

import android.R
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
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
    code: String, usesIDP: Boolean = false,
    viewModel: CommunityPreviewView = hiltViewModel()
){

    val context = LocalContext.current
    viewModel.getInfo(code)
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val showDialog =  remember { mutableStateOf(false) }

    if(showDialog.value){
        CustomDialog(value = "", setShowDialog = {
            showDialog.value = it
        }){
            viewModel.connectWithCommunity(it)
        }
    }

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
        if (viewModel.reached) {
            if (viewModel.get() == null) {
                CircularProgressIndicator(
                    color = BrightOrange,
                    strokeWidth = 5.dp,
                    modifier = Modifier.fillMaxSize()
                )
            }
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

            if (viewModel.reached) {
            Box(modifier = Modifier.padding(vertical = 60.dp))

                Button(
                    onClick = {

                        showDialog.value = true
                              },
                    content = {
                        Text(
                            text = "Join Community",
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
        }else {
            Text(
                text = "QRCode invalid or server unreachable",
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Normal,
                color = White,
                modifier = Modifier.padding(start = 5.dp, top = 20.dp)

            )
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
        }

            }

    }

}

@Composable
fun CustomDialog(value: String, setShowDialog: (Boolean) -> Unit, setValue: (String) -> Unit) {

    val txtFieldError = remember { mutableStateOf("") }
    val txtField = remember { mutableStateOf(value) }
    val passwordVisible = remember { mutableStateOf(false) }

    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = White
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier.padding(20.dp)) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Password",
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontFamily = FontFamily.Default,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Icon(
                            imageVector = Icons.Filled.Cancel,
                            contentDescription = "",
                            tint = colorResource(android.R.color.darker_gray),
                            modifier = Modifier
                                .width(30.dp)
                                .height(30.dp)
                                .clickable { setShowDialog(false) }
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                BorderStroke(
                                    width = 2.dp,
                                    color = colorResource(id = if (txtFieldError.value.isEmpty()) R.color.holo_green_light else R.color.holo_red_dark)
                                ),
                                shape = RoundedCornerShape(50)
                            ),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        placeholder = { Text(text = "Enter value") },
                        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                        value = txtField.value,
                        onValueChange = {
                            txtField.value = it.take(50)
                        },
                        trailingIcon = {
                            val image = if (passwordVisible.value)
                                Icons.Filled.Visibility
                            else Icons.Filled.VisibilityOff

                            // Please provide localized description for accessibility services
                            val description = if (passwordVisible.value) "Hide password" else "Show password"

                            IconButton(onClick = {passwordVisible.value = !passwordVisible.value}){
                                Icon(imageVector  = image, description)
                            }
                        }
                    )

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
                            Text(text = "Submit")
                        }
                    }
                }
            }
        }
    }
}