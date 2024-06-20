package com.testing.mahadevcollegeapp.Screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.testing.mahadevcollegeapp.CollegeViewModel
import com.testing.mahadevcollegeapp.CommonProgressBar

@Composable
fun AddpdfScreen(vm: CollegeViewModel) {
    val isLoading = vm.inProcess
    Box(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier.fillMaxSize()) {
            if (isLoading){
                CommonProgressBar()
            }
            val pdfName = remember {
                mutableStateOf("")
            }
            val pdfuri = remember {
                mutableStateOf<Uri?>(null)
            }
            val context = LocalContext.current
            val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                uri?.let {
                    pdfuri.value = it
                }
            }

            Text(text = "Choose the pdf")

            OutlinedTextField(value = pdfName.value, onValueChange = {pdfName.value = it})

            Button(onClick = { launcher.launch("application/pdf") }) {
                Text(text = "Pick PDF")
            }
            Button(onClick = { if (pdfuri.value != null && pdfName.value != ""){
                vm.addPdf(pdfuri.value!!,context,pdfName.value)
            }
            }) {
                Text(text = "Upload pdf with name")
            }
        }
    }
}