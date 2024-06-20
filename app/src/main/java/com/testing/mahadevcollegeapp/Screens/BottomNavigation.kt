package com.testing.mahadevcollegeapp.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.testing.mahadevcollegeapp.Navigation.Navigation
import com.testing.mahadevcollegeapp.navigateTo


enum class BottomNavigationItem(
    val navDestination: Navigation,
    val icon: ImageVector,
) {
    HomeScreen(Navigation.HomeScreen, Icons.Filled.Home),
    ProfileScreen(Navigation.ProfileScreen,  Icons.Filled.AccountCircle)
}

@Composable
fun BottomNavigation(selectedItem: BottomNavigationItem,navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xff9747FF)), verticalAlignment = Alignment.Bottom
    ) {


        for (items in BottomNavigationItem.entries) {
            Image(
                imageVector = items.icon,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .padding(4.dp)
                    .weight(1f)
                    .clickable { navigateTo(navController = navController,items.navDestination.route) },
                colorFilter = if (selectedItem == items){
                    ColorFilter.tint(color = Color.White)
                }
            else{
                ColorFilter.tint(Color.Black)
            })
        }
    }
}
