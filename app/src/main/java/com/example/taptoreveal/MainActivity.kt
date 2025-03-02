package com.example.taptoreveal

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chartbot.ChatViewModel
import com.example.chartbot.chartPage
import com.example.taptoreveal.cardScreen.cardScreen
import com.example.taptoreveal.ui.theme.TapToRevealTheme



class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModel : ChatViewModel =
            ViewModelProvider(this)[ChatViewModel::class.java]
        setContent {
            val navController = rememberNavController()

            TapToRevealTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    navigation(navController = navController,
                        modifier = Modifier.padding(innerPadding),
                        viewModel)
                }
            }

        }
    }
}


@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun navigation(
    navController: NavHostController,
    modifier: Modifier,
    chatViewModel: ChatViewModel
){
    NavHost(navController= navController, startDestination = "welcome") {
        composable(route = "welcome"){
            welcomeScreen( navController = navController)
        }
        composable(route = "card"){
            cardScreen(modifier,navController = navController)
        }
        composable(route="reading"){
            pastReadingScreen()
        }
        composable(route="chat"){
            chartPage(modifier=modifier, viewModel = chatViewModel)
        }
    }
}


