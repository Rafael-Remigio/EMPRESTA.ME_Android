package me.empresta.feature_register.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.rememberScaffoldState
import androidx.navigation.NavController
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.empresta.*
import me.empresta.feature_register.use_case.RegisterUseCase


@Composable
fun ScreenRegister(
    navController: NavController){


    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    var nickName by remember { mutableStateOf(TextFieldValue("")) }
    var contact by remember { mutableStateOf(TextFieldValue("")) }
    var description by remember { mutableStateOf(TextFieldValue("")) }

    Scaffold(
        scaffoldState = scaffoldState

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
            Box(modifier = Modifier.padding(10.dp))
            Text(
                text = "Register or Load your Data!",
                style = MaterialTheme.typography.h5,
                color = Grey
            )
            Box(modifier = Modifier.padding(15.dp))

            Row(horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier
                    .width(110.dp)
                    .height(1.dp)
                    .background(color = Grey))

                Text(
                    modifier = Modifier.padding(6.dp),
                    text = "I'm new around!",
                    fontSize = 15.sp,
                    color = Grey,
                )

                Box(modifier = Modifier
                    .width(110.dp)
                    .height(1.dp)
                    .background(color = Grey))

            }

            Box(modifier = Modifier.padding(10.dp))

            OutlinedTextField(
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = BrightOrange,
                    unfocusedBorderColor = Grey),
                value = nickName,
                onValueChange = {
                                nickName = it
                },
                label = { Text(text = "NickName", color = BrightOrange)},
            )

            Box(modifier = Modifier.padding(5.dp))

            OutlinedTextField(
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = BrightOrange,
                    unfocusedBorderColor = Grey),
                value = description,
                onValueChange = {
                                description = it
                },
                label = { Text(text = "Description (not necessary)", color = BrightOrange)},
            )


            OutlinedTextField(
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = BrightOrange,
                    unfocusedBorderColor = Grey),
                value = contact,
                onValueChange = {
                                contact = it
                },
                label = { Text(text = "Phone Number (not necessary)", color = BrightOrange)},
            )


        Box(modifier = Modifier.padding(5.dp))


            Button(onClick = {
                    val success = RegisterUseCase.Register(nickName.text,description.text, contact.text);

                    if (success) {
                        navController.navigate(EmprestameScreen.Feed.name)
                    }
                             },
                content = {Text(text = "Register", color = White, fontWeight = FontWeight.Bold, fontSize = 15.sp)},
                colors = ButtonDefaults.buttonColors(backgroundColor = BrightOrange) ,
                modifier=Modifier.width(200.dp).height(60.dp),
                shape = RoundedCornerShape(15)
            )

        Box(modifier = Modifier.padding(10.dp))

        Row(horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier
                .width(100.dp)
                .height(1.dp)
                .background(color = Grey))

            Text(
                modifier = Modifier.padding(6.dp),
                text = "Not my first Rodeo!",
                fontSize = 15.sp,
                color = Grey,
            )

            Box(modifier = Modifier
                .width(100.dp)
                .height(1.dp)
                .background(color = Grey))
        }

        Box(modifier = Modifier.padding(10.dp))


        Button(onClick = { navController.navigate(EmprestameScreen.Feed.name) },
            content = {Text(text = "Load my Data", color = White, fontWeight = FontWeight.Bold, fontSize = 15.sp)},
            colors = ButtonDefaults.buttonColors(backgroundColor = BrightOrange) ,
            modifier=Modifier.width(200.dp).height(60.dp),
            shape = RoundedCornerShape(15)

            )

    }



    }


}