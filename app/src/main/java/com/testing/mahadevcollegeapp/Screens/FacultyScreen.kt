package com.testing.mahadevcollegeapp.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.testing.mahadevcollegeapp.CollegeViewModel

@Composable
fun FacultyScreen(vm:CollegeViewModel) {
    vm.getFacultyDetails()

    val facultyList = vm.faculty.collectAsState()
    val isImageShowing = remember { mutableStateOf(false) }

    val imageIndex = remember {
        mutableStateOf<Int?>(null)
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)) {
        LazyColumn {
            items(facultyList.value) { faculty ->
                FacultyView(image = faculty.imageUrl, name = faculty.name, Designation = faculty.designation, Description = faculty.description,
                    onImageClick = {isImageShowing.value = true
                        imageIndex.value = facultyList.value.indexOf(faculty)})
            }
        }
    }
    if (isImageShowing.value) {
        ImageShow(isImageShowing = isImageShowing.value, onDismissRequest = { isImageShowing.value = false }, image = facultyList.value[imageIndex.value!!].imageUrl)
    }
}
@Composable
fun FacultyView(image: String?,name: String?, Designation: String?, Description: String?, onImageClick: () -> Unit){
    Card(modifier = Modifier
        .padding(8.dp)
        .height(160.dp)
        .fillMaxWidth()) {
        Row(modifier = Modifier.padding(4.dp)) {
            AsyncImage(model = image, contentDescription = null, modifier = Modifier
                .clip(
                    RoundedCornerShape(4.dp)
                ).size(130.dp)
                .clickable { onImageClick() })
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.padding(4.dp)) {
                Row {
                    Text(text = name.toString(), fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = Designation.toString(), fontStyle = FontStyle.Italic)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = Description.toString())
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageShow(isImageShowing: Boolean, onDismissRequest: () -> Unit, image: String?){
    AlertDialog(onDismissRequest = { onDismissRequest() }){
        AsyncImage(model =image , contentDescription = null, modifier = Modifier.clip(
            RoundedCornerShape(12.dp)
        ))
    }
}