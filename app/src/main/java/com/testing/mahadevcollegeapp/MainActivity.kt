       package com.testing.mahadevcollegeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.testing.mahadevcollegeapp.Navigation.CollegeNav
import com.testing.mahadevcollegeapp.Navigation.Navigation
import com.testing.mahadevcollegeapp.Screens.LoginPage
import com.testing.mahadevcollegeapp.ui.theme.MahadevCollegeAppTheme
import dagger.hilt.android.AndroidEntryPoint

       @AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MahadevCollegeAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CollegeNav()
                }
            }
        }
    }
}
