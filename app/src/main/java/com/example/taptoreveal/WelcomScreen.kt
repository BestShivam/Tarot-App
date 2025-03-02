package com.example.taptoreveal

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.taptoreveal.room.ReadingModel

@Preview(showBackground = true)
@Composable
fun welcomeScreen(navController: NavController= rememberNavController()){
    var question by remember { mutableStateOf("") }

    Column (modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){

        Card (modifier = Modifier.padding(15.dp)
            .padding(10.dp),
            elevation = CardDefaults.elevatedCardElevation(10.dp)){
            Column (modifier = Modifier.fillMaxWidth().
            padding(10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally){
                Text("Welcome to ",
                    modifier = Modifier.padding(top = 10.dp),
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.primary)
                Text("tarot app",
                    modifier = Modifier.padding(bottom = 5.dp),
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.primary)
                TextField(
                    value = question,
                    onValueChange = {question = it},
                    label = { Text("Ask Question", fontSize = 20.sp) },
                    modifier = Modifier.padding(15.dp)
                )
                Button(onClick = {
                    if(question.isNotEmpty())
                        navController.navigate("card")

                }) {
                    Text(text = "start")
                }
                IconButton(onClick = {
                    navController.navigate("reading")
                },
                    modifier = Modifier.padding(10.dp)) {
                    Image(painter = painterResource(R.drawable.reading),
                        contentDescription = "reading screen")
                }

            }
        }
    }
}