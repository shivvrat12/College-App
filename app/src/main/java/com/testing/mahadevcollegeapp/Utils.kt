package com.testing.mahadevcollegeapp

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.Navigation

@Composable
fun CommonSpaceBar(Padding: Int){
    Spacer(modifier = Modifier.size(Padding.dp))
}

@Composable
fun TransparentButton(text: String, onClicked : () -> Unit){
    Button(onClick = { onClicked.invoke() }, colors = ButtonDefaults.buttonColors(
        containerColor = Color.Transparent,
        contentColor = Color.Blue
    ),
        modifier = Modifier
            .padding(8.dp)
            .border(1.dp, Color.Blue, CircleShape)) {
        Text(text = text, fontWeight = FontWeight.Bold)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonOutlineTextField(value: String, onValueChange: (String) -> Unit,HintName : String) {
    TextField(
        modifier = Modifier
            .wrapContentHeight()
            .clip(RoundedCornerShape(22.dp))
            .background(Color.LightGray),
        value = value, onValueChange = onValueChange,
        label = { Text(text = HintName, fontWeight = FontWeight.SemiBold)},
        singleLine = true,
        textStyle = TextStyle(fontSize = 15.sp, color = Color.Black, letterSpacing = 2.sp, fontWeight = FontWeight.Medium)
    )
}

@Composable
fun CommonProgressBar(){
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0x80000000)),
        contentAlignment = Alignment.Center){
        CircularProgressIndicator()
    }
}


@Composable
fun checkSignedIn(vm: CollegeViewModel, navController: NavController){

    val signedIn = vm.signedIn.value
    LaunchedEffect(key1 = Unit) {

    if (signedIn){
        Log.d("Checking","User is signed in We can redirect ${signedIn}")
        navController.navigate(com.testing.mahadevcollegeapp.Navigation.Navigation.HomeScreen.route) {
            popUpTo(0)
        }
    }
    else{
        Log.d("Checking","User is not signed in We can not redirect ${signedIn}")
        navController.navigate(com.testing.mahadevcollegeapp.Navigation.Navigation.StartScreen.route){
            popUpTo(0)
            }
        }
    }
}

fun navigateTo(navController: NavController,route : String){
    navController.navigate(route){
        popUpTo(route)
        launchSingleTop = true
    }
}
