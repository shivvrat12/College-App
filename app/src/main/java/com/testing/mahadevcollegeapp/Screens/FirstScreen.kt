package com.testing.mahadevcollegeapp.Screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.testing.mahadevcollegeapp.CollegeViewModel
import com.testing.mahadevcollegeapp.Navigation.Navigation
import com.testing.mahadevcollegeapp.R
import com.testing.mahadevcollegeapp.TransparentButton
import com.testing.mahadevcollegeapp.checkSignedIn
import com.testing.mahadevcollegeapp.navigateTo
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext

@Composable
fun FirstScreen(vm: CollegeViewModel, navController: NavController) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.images),
            contentDescription = "College Logo",
            modifier = Modifier
                .padding(top = 100.dp)
                .size(100.dp)
        )
        Text(
            text = "Welcome to MPGC",
            style = TextStyle(fontSize = 40.sp, color = Color(0xFF161697)),
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 30.dp)
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Text(text = "LET ACCESS ALL WORK FROM HERE")
        Spacer(modifier = Modifier.padding(4.dp))
        TransparentButton(text = "Log in",onClicked = { navigateTo(navController,Navigation.LoginScreen.route) })
        TransparentButton(text = "Sign up",onClicked = { Toast.makeText(context,"only Admins can sign up!",Toast.LENGTH_LONG).show()})
        Image(painter = painterResource(id = R.drawable.image_3), contentDescription = null, modifier = Modifier.size(500.dp))
    }
}

