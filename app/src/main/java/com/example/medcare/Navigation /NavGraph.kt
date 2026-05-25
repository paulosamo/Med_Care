package com.example.medcare.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.medcare.Appointment_Screen.AppointmentScreen

// HOME
import com.example.medcare.Home_Screen.HomeScreen

// AUTH
import com.example.medcare.login_signup.AuthScreen

// PROFILE
import com.example.medcare.HealthProfile.HealthProfileScreen

@Composable
fun MediCareNavGraph(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = "auth"
    ) {

        // AUTH SCREEN

        composable("auth") {

            AuthScreen(navController)
        }

        // HOME SCREEN

        composable("home") {

            HomeScreen(navController)
        }



        // HEALTH PROFILE SCREEN

        composable("health_profile") {

            HealthProfileScreen(navController)
        }

        // APPOINTMENT SCREEN

        composable("appointment") {

            AppointmentScreen(navController)
        }

    }
}