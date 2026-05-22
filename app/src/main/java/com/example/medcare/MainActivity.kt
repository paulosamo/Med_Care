package com.example.medcare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.medcare.splash_screen.MediCareNavGraph
import com.example.medcare.ui.theme.MedCareTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            MedCareTheme {

                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {

                    val navController = rememberNavController()

                    MediCareNavGraph(navController = navController)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {

    MedCareTheme {

        val navController = rememberNavController()

        MediCareNavGraph(navController = navController)
    }
}