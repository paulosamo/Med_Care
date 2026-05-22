package com.example.medcare.Navigation
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun MediCareNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        //composable("splash") { SplashScreen(navController) }
        composable("login") { SignIn_LogIn(navController) }
        composable("home") { Home_Screen(navController) }
        composable("appointment") { Appointment_Screen(navController) }

    }
}

@Composable
fun Appointment_Screen(x0: NavHostController) {
    TODO("Not yet implemented")
}

@Composable
fun SignIn_LogIn(x0: NavHostController) {
    TODO("Not yet implemented")
}

@Composable
fun Home_Screen(x0: NavHostController) {
    TODO("Not yet implemented")
}