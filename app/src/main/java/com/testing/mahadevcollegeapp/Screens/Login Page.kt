package com.testing.mahadevcollegeapp.Screens

import android.view.RoundedCorner
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.testing.mahadevcollegeapp.CollegeViewModel
import com.testing.mahadevcollegeapp.CommonProgressBar
import com.testing.mahadevcollegeapp.CommonSpaceBar
import com.testing.mahadevcollegeapp.Navigation.Navigation
import com.testing.mahadevcollegeapp.R
import com.testing.mahadevcollegeapp.TransparentButton
import com.testing.mahadevcollegeapp.checkSignedIn
import com.testing.mahadevcollegeapp.navigateTo

@Composable
fun LoginPage(vm: CollegeViewModel, navController: NavController) {

    val isSignedIn = vm.signedIn.value
    val inProcess = vm.inProcess
    val emailId = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }
    if (isSignedIn){
        navigateTo(navController,Navigation.HomeScreen.route)
    }
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (inProcess){
                CommonProgressBar()
            }
            Image(
                painter = painterResource(id = R.drawable.images),
                contentDescription = "College Logo",
                modifier = Modifier
                    .padding(top = 100.dp)
                    .size(80.dp)
            )
            Text(
                text = "Welcome to MPGC",
                style = TextStyle(fontSize = 30.sp, color = Color(0xFF161697)),
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 30.dp)
            )
            CommonSpaceBar(4)
            Text(text = "LET ACCESS ALL WORK FROM HERE", fontSize = 10.sp)
            CommonSpaceBar(50)
            OutlineTextField(
                number = emailId.value,
                onValueChange = { emailId.value = it },
                icon = Icons.Rounded.Email
            )
            CommonSpaceBar(Padding = 30)
            OutlineTextField(
                number = password.value,
                onValueChange = { password.value = it },
                icon = Icons.Filled.Lock
            )
            CommonSpaceBar(Padding = 40)
            TransparentButton(text = "Login", onClicked = {
                if (emailId.value.isEmpty() || password.value.isEmpty()) {
                    Toast.makeText(context, "Please enter email and password", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    vm.logIn(emailId.value, password.value)
                }
            }
            )
            Image(
                painter = painterResource(id = R.drawable.image_1__1_),
                contentDescription = null,
                modifier = Modifier.size(500.dp)
            )

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlineTextField(number: String, onValueChange: (String) -> Unit,icon : ImageVector) {
    TextField(
        modifier = Modifier
            .wrapContentHeight()
            .clip(RoundedCornerShape(22.dp))
            .background(Color.LightGray),
        value = number, onValueChange = onValueChange, leadingIcon = { Icon(
            imageVector = icon,
            contentDescription = null
        )},
        singleLine = true,
        textStyle = TextStyle(fontSize = 15.sp, color = Color.Black, letterSpacing = 2.sp, fontWeight = FontWeight.Medium)
    )
}

