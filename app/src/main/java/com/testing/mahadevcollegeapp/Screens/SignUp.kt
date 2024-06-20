package com.testing.mahadevcollegeapp.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.testing.mahadevcollegeapp.CollegeViewModel
import com.testing.mahadevcollegeapp.CommonOutlineTextField
import com.testing.mahadevcollegeapp.CommonProgressBar
import com.testing.mahadevcollegeapp.CommonSpaceBar
import com.testing.mahadevcollegeapp.R
import com.testing.mahadevcollegeapp.TransparentButton
import com.testing.mahadevcollegeapp.checkSignedIn
import com.testing.mahadevcollegeapp.data.STUDENT
import com.testing.mahadevcollegeapp.data.TEACHER

@Composable
fun SignUp(vm: CollegeViewModel, navController: NavController) {

    val inProcess = vm.inProcess
    val context = LocalContext.current
    val fullname = remember {
        mutableStateOf("")
    }
    val emailId = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }
    var isexpanded by remember {
        mutableStateOf(false)
    }
    val User_Mode = remember {
        mutableStateOf("Choose Account Type")
    }
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
                    .padding(top = 80.dp)
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

            CommonSpaceBar(Padding = 30)
            CommonOutlineTextField(
                value = fullname.value,
                onValueChange = { fullname.value = it },
                HintName = "Full Name"
            )
            CommonSpaceBar(Padding = 30)
            CommonOutlineTextField(
                value = emailId.value,
                onValueChange = { emailId.value = it },
                HintName = "Email Address"
            )
            CommonSpaceBar(Padding = 30)
            CommonOutlineTextField(
                value = password.value,
                onValueChange = { password.value = it },
                HintName = "Create Password"
            )
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(30.dp))
                    .background(Color.LightGray)
            ) {
                TextButton(onClick = { isexpanded = true }) {
                    Text(User_Mode.value, fontWeight = FontWeight.Bold, color = Color.Blue)
                }
                DropdownMenu(expanded = isexpanded, onDismissRequest = { isexpanded = false }) {
                    DropdownMenuItem(onClick = {
                        User_Mode.value = TEACHER
                        isexpanded = false
                    },
                        text = { Text(text = TEACHER) })
                    DropdownMenuItem(text = { Text(text = STUDENT) }, onClick = {
                        User_Mode.value = STUDENT
                        isexpanded = false
                    })
                }
            }
            TransparentButton(
                text = "Sign up",
                onClicked = {
                    vm.signUp(
                        context,
                        fullname.value,
                        emailId.value,
                        password.value,
                        User_Mode.value
                    )
                })
            CommonSpaceBar(Padding = 3)

            Image(
                painter = painterResource(id = R.drawable.image_2__1_),
                contentDescription = null,
                modifier = Modifier.size(250.dp)
            )
        }
    }
}

