package com.example.medcare.splash_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.medcare.Home_Screen.HomeScreen
import com.example.medcare.login_signup.AuthScreen
import kotlinx.coroutines.launch

// -----------------------------------
// DATA CLASS
// -----------------------------------

data class OnboardingPage(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val color: Color
)

// -----------------------------------
// MAIN SCREEN
// -----------------------------------

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen(navController: NavHostController) {

    val pagerState = rememberPagerState(pageCount = { 4 })
    val scope = rememberCoroutineScope()

    val pages = listOf(
        OnboardingPage(
            title = "Book Appointments",
            description = "Schedule appointments with trusted doctors quickly and easily.",
            icon = Icons.Default.CalendarMonth,
            color = Color(0xFF4CAF50)
        ),
        OnboardingPage(
            title = "Medical Records",
            description = "Access your medical history and reports securely anytime.",
            icon = Icons.Default.HealthAndSafety,
            color = Color(0xFF2196F3)
        ),
        OnboardingPage(
            title = "Medication Reminders",
            description = "Get smart reminders for medicines and clinic visits.",
            icon = Icons.Default.Notifications,
            color = Color(0xFFFF9800)
        ),
        OnboardingPage(
            title = "Talk to Specialists",
            description = "Chat with doctors securely from anywhere at any time.",
            icon = Icons.Default.Chat,
            color = Color(0xFF9C27B0)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFF5F9FF),
                        Color.White
                    )
                )
            )
    )

    {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            // -----------------------------------
            // TOP SECTION
            // -----------------------------------

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Surface(
                    modifier = Modifier
                        .size(90.dp)
                        .shadow(10.dp, CircleShape),
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primary
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "M",
                            color = Color.White,
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "MediCare",
                    fontSize = 34.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Your Health, Simplified",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            // -----------------------------------
            // PAGER
            // -----------------------------------

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->

                OnboardingPageContent(
                    page = pages[page]
                )
            }

            // -----------------------------------
            // BOTTOM SECTION
            // -----------------------------------

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Indicators
                Row(
                    horizontalArrangement = Arrangement.Center
                ) {

                    repeat(pages.size) { index ->

                        val selected = pagerState.currentPage == index

                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .height(10.dp)
                                .width(if (selected) 28.dp else 10.dp)
                                .clip(CircleShape)
                                .background(
                                    if (selected)
                                        MaterialTheme.colorScheme.primary
                                    else
                                        Color.LightGray
                                )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))

                // Button
                Button(
                    onClick = {

                        if (pagerState.currentPage == pages.lastIndex) {

                            navController.navigate("login") {
                                popUpTo("onboarding") {
                                    inclusive = true
                                }
                            }

                        } else {

                            scope.launch {
                                pagerState.animateScrollToPage(
                                    pagerState.currentPage + 1
                                )
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp)
                        .height(58.dp),
                    shape = RoundedCornerShape(18.dp)
                ) {

                    Text(
                        text = if (pagerState.currentPage == pages.lastIndex)
                            "Get Started"
                        else
                            "Next",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "Already have an account? Sign In",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable {

                        navController.navigate("login") {
                            popUpTo("onboarding") {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }
    }
}

// -----------------------------------
// PAGE CONTENT
// -----------------------------------

@Composable
fun OnboardingPageContent(page: OnboardingPage) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Surface(
            modifier = Modifier
                .size(240.dp)
                .shadow(14.dp, RoundedCornerShape(36.dp)),
            shape = RoundedCornerShape(36.dp),
            color = page.color.copy(alpha = 0.12f)
        ) {

            Box(
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector = page.icon,
                    contentDescription = page.title,
                    tint = page.color,
                    modifier = Modifier.size(120.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(45.dp))

        Text(
            text = page.title,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = page.description,
            fontSize = 17.sp,
            lineHeight = 28.sp,
            textAlign = TextAlign.Center,
            color = Color.Gray
        )
    }
}

// -----------------------------------
// NAVIGATION
// -----------------------------------

@Composable
fun MediCareNavGraph(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = "onboarding"
    ) {

        composable("onboarding") {
            OnboardingScreen(navController)
        }

        composable("login") {
            AuthScreen(navController)
        }

        composable("home") {
            HomeScreen(navController)
        }
    }
}

// -----------------------------------
// PREVIEW
// -----------------------------------

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun OnboardingPreview() {

    MaterialTheme {

        OnboardingScreen(
            navController = rememberNavController()
        )
    }
}