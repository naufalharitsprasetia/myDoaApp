package com.example.mydoa

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WelcomeScreen(onGetStartedClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Ganti teks "App Logo" dengan gambar logo aplikasi
        Image(
            painter = painterResource(id = R.drawable.mydoa_logo), // Gantilah "your_logo" dengan nama file gambar Anda
            contentDescription = "App Logo",
            modifier = Modifier.size(100.dp) // Sesuaikan ukuran sesuai kebutuhan Anda
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "MyDoa Application",
            fontSize = 24.sp,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onGetStartedClick) {
            Text("Get Started")
        }
    }
}
