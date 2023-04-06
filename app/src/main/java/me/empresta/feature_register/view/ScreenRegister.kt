package me.empresta.feature_register.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.empresta.*


@Composable
fun ScreenRegister(
    navController: NavController){


    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

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
                value = "",
                onValueChange = {},
                label = { Text(text = "Name", color = BrightOrange)},
            )

            Box(modifier = Modifier.padding(5.dp))

            OutlinedTextField(
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = BrightOrange,
                    unfocusedBorderColor = Grey),
                value = "",
                onValueChange = {},
                label = { Text(text = "Email address", color = BrightOrange)},
            )

            Box(modifier = Modifier.padding(5.dp))


            Button(onClick = { navController.navigate(EmprestameScreen.Feed.name)},
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