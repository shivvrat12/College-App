package com.testing.mahadevcollegeapp.Screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.testing.mahadevcollegeapp.CollegeViewModel
import com.testing.mahadevcollegeapp.CommonProgressBar
import com.testing.mahadevcollegeapp.CommonSpaceBar
import com.testing.mahadevcollegeapp.Navigation.Navigation
import com.testing.mahadevcollegeapp.data.NotificationModel
import com.testing.mahadevcollegeapp.data.TEACHER
import com.testing.mahadevcollegeapp.data.User_Model
import com.testing.mahadevcollegeapp.navigateTo

@Composable
fun ProfileScreen(vm: CollegeViewModel, navController: NavController) {
    val userdata = vm.user.value

    val isSigned = vm.signedIn.value

    val context = LocalContext.current

    val inProcess = vm.inProcess

    val isAddNotificationVisible = remember {
        mutableStateOf(false)
    }
    val notificationTitle = remember {
        mutableStateOf("")
    }
    val notificationDes = remember {
        mutableStateOf("")
    }
    val isDeleteFacultyVisible = remember {
        mutableStateOf(false)
    }
    if (!isSigned) {
        navigateTo(navController, Navigation.LoginScreen.route)
        return
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            if (inProcess) CommonProgressBar()
            if (isAddNotificationVisible.value) {
                AddNotification(
                    onDismissClick = { isAddNotificationVisible.value = false },
                    title = notificationTitle.value,
                    description = notificationDes.value,
                    onTitleChange = { notificationTitle.value = it },
                    onDescriptionChange = { notificationDes.value = it },
                    onAddClick = {
                        vm.addNotification(NotificationModel(title = notificationTitle.value, description = notificationDes.value))
                        notificationTitle.value = ""
                        notificationDes.value = ""
                    }
                )
            }

            if (isDeleteFacultyVisible.value) {
                DeleteFacultyAlert(
                    onDeleteClick = { vm.deleteFaculty(it, context) },
                    onDismissClick = { isDeleteFacultyVisible.value = false }
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                UserProfileSection(vm, userdata, navController)
                CommonSpaceBar(Padding = 10)
                if (userdata?.loginType == TEACHER) {
                    Log.d("USERTYPE", "ProfileScreen: ${userdata.loginType}")
                    TeacherOptions(navController, isAddNotificationVisible, isDeleteFacultyVisible)
                }
            }

            BottomNavigation(
                selectedItem = BottomNavigationItem.ProfileScreen,
                navController = navController
            )
        }
    }
}

@Composable
fun UserProfileSection(vm: CollegeViewModel, userdata: User_Model?, navController: NavController) {
    Column {
        Row(
            modifier = Modifier
                .background(Color(0xff9747FF))
                .fillMaxWidth()
        ) {
            if (userdata != null) {
                Text(
                    text = userdata.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    color = Color.White,
                    modifier = Modifier
                        .height(70.dp)
                        .padding(8.dp)
                )
                Text(
                    text = " (${userdata.loginType})",
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    modifier = Modifier.padding(20.dp)
                )
            }
        }
        userdata?.email?.let {
            Text(
                text = it,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xff9747FF))
                    .padding(start = 20.dp)
            )
        }
        ProfileOption(text = "Faculty") { navigateTo(navController, Navigation.FacultyScreen.route) }
        CommonSpaceBar(Padding = 8)
        ProfileOption(text = "Log out") { vm.logout() }
    }
}

@Composable
fun ProfileOption(text: String, onClick: () -> Unit) {
    Text(
        text = text,
        fontSize = 20.sp,
        textAlign = TextAlign.Center,
        textDecoration = TextDecoration.Underline,
        modifier = Modifier
            .padding(4.dp, top = 8.dp)
            .fillMaxWidth()
            .height(30.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(Color.LightGray)
            .clickable { onClick() }
    )
}

@Composable
fun TeacherOptions(
    navController: NavController,
    isAddNotificationVisible: MutableState<Boolean>,
    isDeleteOptionVisible: MutableState<Boolean>
) {
    Column {
        TeacherOption(text = "Create User") { navigateTo(navController, Navigation.SignUpScreen.route) }
        TeacherOption(text = "Create Notifications") { isAddNotificationVisible.value = true }
        TeacherOption(text = "Add/Edit Faculty") { navController.navigate(Navigation.AddFacultyScreen.route) }
        TeacherOption(text = "Delete Faculty") { isDeleteOptionVisible.value = true }
        TeacherOption(text = "Add PDF") { navigateTo(navController, Navigation.AddpdfScreen.route) }
    }
}

@Composable
fun TeacherOption(text: String, onClick: () -> Unit) {
    Text(
        text = text,
        fontSize = 20.sp,
        textAlign = TextAlign.Center,
        textDecoration = TextDecoration.Underline,
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .height(30.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(Color.LightGray)
            .clickable { onClick() }
    )
    CommonSpaceBar(Padding = 8)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNotification(
    onDismissClick: () -> Unit,
    title: String,
    description: String,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onAddClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismissClick() },
        modifier = Modifier.height(400.dp)
    ) {
        Card {
            Column {
                Text(
                    text = "Add Notification",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 70.dp)
                )
                OutlinedTextField(
                    value = title,
                    onValueChange = { onTitleChange(it) },
                    label = { Text("Title") },
                    modifier = Modifier
                        .padding(8.dp)
                        .width(270.dp)
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { onDescriptionChange(it) },
                    label = { Text("Description") },
                    modifier = Modifier
                        .padding(8.dp)
                        .width(270.dp),
                    minLines = 6
                )
                Button(
                    onClick = {
                        onAddClick()
                        onDismissClick()
                    },
                    modifier = Modifier.padding(start = 100.dp, top = 20.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xff9747FF))
                ) {
                    Text("Add")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteFacultyAlert(
    onDeleteClick: (String) -> Unit,
    onDismissClick: () -> Unit
) {
    val rank = remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = { onDismissClick() },
        modifier = Modifier.background(Color.LightGray)
    ) {
        Column {
            OutlinedTextField(
                value = rank.value,
                onValueChange = { rank.value = it },
                label = { Text("Rank") },
                modifier = Modifier.padding(8.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Button(
                onClick = { onDeleteClick(rank.value) },
                modifier = Modifier.padding(start = 100.dp),
                colors = ButtonDefaults.buttonColors()
            ) {
                Text("Delete")
            }
        }
    }
}