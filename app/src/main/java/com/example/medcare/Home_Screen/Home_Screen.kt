package com.example.medcare.Home_Screen
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController? = null) {

    val tips = listOf(
        Triple("Stay Hydrated", "Drink more water daily", Icons.Default.WaterDrop),
        Triple("Exercise", "Walk 30 mins everyday", Icons.Default.DirectionsWalk),
        Triple("Healthy Food", "Eat fruits & vegetables", Icons.Default.Restaurant)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("MediCare", fontWeight = FontWeight.Bold)
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Notifications, null)
                    }
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Person, null)
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            // Greeting
            Text(
                "Hello Paul 👋",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                "Your health matters",
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Appointment Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(20.dp)) {

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.CalendarMonth, null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Next Appointment")
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        "Dr. Innocent Mose",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text("Tomorrow • 10:30 AM")
                    Text("The Nairobi Hospital")

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {},
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("View Details")
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Quick Actions
            Text(
                "Quick Actions",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {

                ActionItem(Icons.Default.Add, "Book", Color(0xFF2196F3))
                ActionItem(Icons.Default.Description, "Records", Color(0xFF4CAF50))
                ActionItem(Icons.Default.Medication, "Medicine", Color(0xFFFF9800))
                ActionItem(Icons.Default.Call, "Emergency", Color(0xFFE53935))
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Health Stats
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                StatCard("Heart Rate", "78 bpm")
                StatCard("Blood Group", "O+")
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Tips
            Text(
                "Health Tips",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {

                items(tips) { tip ->

                    Card(
                        modifier = Modifier.size(width = 220.dp, height = 130.dp),
                        shape = RoundedCornerShape(20.dp)
                    ) {

                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {

                            Icon(
                                tip.third,
                                null,
                                tint = MaterialTheme.colorScheme.primary
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                tip.first,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                tip.second,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
fun ActionItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    color: Color
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { }
    ) {

        Box(
            modifier = Modifier
                .size(65.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(color.copy(.15f)),
            contentAlignment = Alignment.Center
        ) {

            Icon(
                icon,
                null,
                tint = color,
                modifier = Modifier.size(30.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            title,
            textAlign = TextAlign.Center,
            fontSize = 13.sp
        )
    }
}

@Composable
fun StatCard(title: String, value: String) {

    Card(
        modifier = Modifier
            .width(170.dp)
            .height(100.dp),
        shape = RoundedCornerShape(18.dp)
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                title,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                value,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen()
    }
}