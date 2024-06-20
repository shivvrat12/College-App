package com.testing.mahadevcollegeapp.Screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.rajat.pdfviewer.PdfViewerActivity
import com.rajat.pdfviewer.compose.PdfRendererViewCompose
import com.rajat.pdfviewer.util.saveTo
import com.testing.mahadevcollegeapp.CollegeViewModel
import com.testing.mahadevcollegeapp.Navigation.Navigation
import com.testing.mahadevcollegeapp.data.TEACHER
import com.testing.mahadevcollegeapp.data.User_Model
import com.testing.mahadevcollegeapp.navigateTo
import java.net.URL


@Composable
fun HomePage(vm: CollegeViewModel, navController: NavController) {
    val userdata = vm.user.value
    val facultyList = vm.faculty.collectAsState()
    val notification = vm.notifications.collectAsState()
    val pdf = vm.pdf.collectAsState()
    val isLoading by vm.inRefresh.collectAsState()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)
    val context = LocalContext.current

    SwipeRefresh(state = swipeRefreshState, onRefresh = { vm.getAllNotifications() }) {
        Column {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(65.dp)
                    .background(Color(0xff9777FF))
            ) {
                Text(
                    text = "Mahadev PG College",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.Black,
                    fontSize = 35.sp,
                    modifier = Modifier.padding(10.dp)
                )
            }

            Text(
                text = "Notifications ->", modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            )
            LazyRow() {
                items(notification.value) { item ->
                    NotificationCardView(
                        user = userdata,
                        Title = item.title,
                        Description = item.description,
                        onDeleteClick = { vm.deleteNotification(item.id) })
                }
            }
            Text(
                text = "Faculties  -> (Click to view)", modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
                    .clickable { navigateTo(navController, Navigation.FacultyScreen.route) }
            )
            LazyRow() {
                items(facultyList.value) { item ->
                    FacultyView(
                        image = item.imageUrl.toString(),
                        name = item.name.toString(),
                        designation = item.designation.toString()
                    )
                }
            }

            Text(
                text = "Important PDFs", modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            )
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(pdf.value) { item ->
                    pdfView(uri = item.uri, name = item.name, context = context, onDownloadClicked = {vm.downloadPdf(it,"",context)})
                }
            }
            BottomNavigation(
                selectedItem = BottomNavigationItem.HomeScreen,
                navController = navController
            )
        }
    }
}

@Composable
fun pdfView(uri: String, name : String, context : Context, onDownloadClicked:(String)->Unit){
    Box(
        modifier = Modifier
            .padding(8.dp)

    ) {
        Column {
            Text(
                text = name,
                modifier = Modifier.padding(16.dp),
                color = Color.Blue,
                fontSize = 16.sp
            )
            PdfRendererViewCompose(
                url = uri.toString(),
                lifecycleOwner = LocalLifecycleOwner.current,
            )
            Button(onClick = { onDownloadClicked(uri) },
                modifier = Modifier.padding(4.dp)) {
                Text(text = "Download PDF")
            }
        }
    }
}

@Composable
fun NotificationCardView(
    user: User_Model?,
    Title: String,
    Description: String,
    onDeleteClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(200.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp, pressedElevation = 20.dp),
        colors = CardDefaults.cardColors(Color(0xff9747FF))
    ) {
        Row() {
            Text(
                text = Title,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.padding(4.dp)
            )
            if (user?.loginType == TEACHER)
                Icon(Icons.Filled.Delete, contentDescription = null, modifier = Modifier.clickable {
                    onDeleteClick()
                })
        }
        Text(
            text = Description,
            fontWeight = FontWeight.SemiBold,
            fontStyle = FontStyle.Italic,
            modifier = Modifier.padding(4.dp)
        )
    }
}

@Composable
fun FacultyView(image: String, name: String, designation: String) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .wrapContentSize()
    ) {
        Row {
            AsyncImage(model = image, contentDescription = null,modifier = Modifier
                .clip(
                    RoundedCornerShape(4.dp)
                )
                .padding(4.dp)
                .size(100.dp))
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = name,textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                Text(text = designation, fontStyle = FontStyle.Italic, modifier = Modifier.padding(10.dp))
            }
        }
    }
}