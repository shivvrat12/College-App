package com.testing.mahadevcollegeapp.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.testing.mahadevcollegeapp.CollegeViewModel
import com.testing.mahadevcollegeapp.CommonProgressBar
import com.testing.mahadevcollegeapp.checkSignedIn

@Composable
fun ChechSignedinScreen(vm: CollegeViewModel, navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Signing You In \n Wait for a few Seconds",
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
    checkSignedIn(vm = vm, navController = navController)
}