package com.testing.mahadevcollegeapp.Screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.testing.mahadevcollegeapp.CollegeViewModel
import com.testing.mahadevcollegeapp.CommonProgressBar
import com.testing.mahadevcollegeapp.R
import com.testing.mahadevcollegeapp.data.FacultyModel

@Composable
fun AddFacultyScreen(vm: CollegeViewModel) {

    val inprogress = vm.inProcess
    val name = remember {
        mutableStateOf("")
    }
    val rank = remember {
        mutableStateOf("")
    }
    val designation = remember {
        mutableStateOf("")
    }
    val description = remember {
        mutableStateOf("")
    }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri
        }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        // Add your UI components here
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                imageUri?.let {
                    val painter = rememberAsyncImagePainter(it)
                    Image(
                        painter = painter,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(250.dp)
                            .border(
                                width = 2.dp,
                                color = Color.Black,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable {
                                launcher.launch("image/*")
                            }
                    )
                } ?: run {
                    Image(
                        painter = rememberAsyncImagePainter(R.drawable.photo),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(250.dp)
                            .border(
                                width = 2.dp,
                                color = Color.Black,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable {
                                launcher.launch("image/*")
                            }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(value = name.value, onValueChange = { name.value = it }, label = {
                    Text(text = "Name")
                })
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = rank.value,
                    onValueChange = { rank.value = it },
                    label = {
                        Text(text = "Rank")
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = designation.value,
                    onValueChange = { designation.value = it },
                    label = {
                        Text(text = "Designation")
                    })
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = description.value,
                    onValueChange = { description.value = it },
                    label = {
                        Text(text = "Description")
                    })
                Spacer(modifier = Modifier.height(32.dp))

                Button(onClick = {
                    if (imageUri != null) {
                        vm.addFacultyDetails(
                            imageUri,
                            context,
                            FacultyModel(
                                name = name.value,
                                Rank = rank.value,
                                description = description.value,
                                designation = designation.value
                            )
                        )
                    } else {
                        Toast.makeText(context, "Enter The Details Properly", Toast.LENGTH_SHORT)
                            .show()
                    }
                    name.value = ""
                    rank.value = ""
                    designation.value = ""
                    description.value = ""
                    imageUri = null
                }) {
                    Text(text = "Add Faculty")
                }
            }
            if (inprogress){
                CommonProgressBar()
            }
        }
    }
}