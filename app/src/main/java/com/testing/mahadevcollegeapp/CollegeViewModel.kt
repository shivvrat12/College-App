package com.testing.mahadevcollegeapp

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.FirebaseStorage
import com.testing.mahadevcollegeapp.data.FACULTY
import com.testing.mahadevcollegeapp.data.FacultyModel
import com.testing.mahadevcollegeapp.data.NOTIFICATION
import com.testing.mahadevcollegeapp.data.NotificationModel
import com.testing.mahadevcollegeapp.data.PDF
import com.testing.mahadevcollegeapp.data.USER
import com.testing.mahadevcollegeapp.data.User_Model
import com.testing.mahadevcollegeapp.data.pdfModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CollegeViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
    private val storage: FirebaseStorage
) : ViewModel() {

    private val _inRefresh = MutableStateFlow(false)
    val inRefresh: StateFlow<Boolean> = _inRefresh

    val signedIn = mutableStateOf(false)
    var inProcess by mutableStateOf(false)

    private val _user = mutableStateOf<User_Model?>(null)
    val user: State<User_Model?> get() = _user

    private val _faculty = MutableStateFlow<List<FacultyModel>>(emptyList())
    val faculty: StateFlow<List<FacultyModel>> = _faculty

    private val _notifications = MutableStateFlow<List<NotificationModel>>(emptyList())
    val notifications: StateFlow<List<NotificationModel>> = _notifications

    private val _pdf = MutableStateFlow<List<pdfModel>>(emptyList())
    val pdf: StateFlow<List<pdfModel>> = _pdf

    init {
        auth.currentUser?.let { user ->
            signedIn.value = true
            getUserData(user.uid)
        }
        getAllNotifications()
        getFacultyDetails()
        getPdfs()
    }

    fun signUp(context: Context, fullname: String, email: String, password: String, userType: String) {
        inProcess = true
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener { authResult ->
            authResult.user?.let { user ->
                createOrUpdateUser(context, fullname, email, password, userType, user.uid)
            }
            Log.d("SignUp", "Sign Up Successful")
            inProcess = false
        }.addOnFailureListener { exception ->
            inProcess = false
            showToast(context, "SignUp Failed: ${exception.message}")
            handleException("SignUp Failed", exception)
        }
    }

    fun logIn(email: String, password: String) {
        inProcess = true
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            inProcess = false
            signedIn.value = true
            Log.d("LogIn", "Sign In Successful")
        }.addOnFailureListener { exception ->
            inProcess = false
            Log.e("LogIn", "Log In Failed: ${exception.message}")
        }
    }

    private fun getUserData(uid: String) {
        inProcess = true
        db.collection(USER).document(uid).addSnapshotListener { value, error ->
            error?.let {
                handleException("Unable to retrieve user data", it)
                return@addSnapshotListener
            }
            value?.toObject<User_Model>()?.let {
                _user.value = it
                inProcess = false
            }
        }
    }

    private fun createOrUpdateUser(context: Context, fullname: String, email: String, password: String, userType: String, uid: String) {
        inProcess = true
        val user = User_Model(fullname, email, password, userType)
        db.collection(USER).document(uid).set(user).addOnSuccessListener {
            showToast(context, "User Data Stored Successfully ($userType)")
            inProcess = false
        }.addOnFailureListener {
            inProcess = false
            showToast(context, "Failed to store user data")
            handleException("Failed to store user data", it)
        }
    }

    fun addNotification(notification: NotificationModel) {
        inProcess = true
        val newNotification = notification.copy(timestamp = System.currentTimeMillis())
        db.collection(NOTIFICATION).add(newNotification).addOnSuccessListener { documentReference ->
            documentReference.set(newNotification.copy(id = documentReference.id)).addOnSuccessListener {
                inProcess = false
            }
        }
    }

    fun addFacultyDetails(uri: Uri?, context: Context, facultyModel: FacultyModel) {
        uri?.let {
            inProcess = true
            val imageReference = storage.reference.child("images/${uri.lastPathSegment}")
            val uploadTask = imageReference.putFile(uri)
            uploadTask.addOnSuccessListener {
                imageReference.downloadUrl.addOnSuccessListener { downloadUri ->
                    createFaculty(context, facultyModel.copy(imageUrl = downloadUri.toString()))
                    inProcess = false
                }
            }.addOnFailureListener {
                inProcess = false
                showToast(context, "Image Upload Failed")
            }
        }
    }

    private fun createFaculty(context: Context, facultyModel: FacultyModel) {
        inProcess = true
        db.collection(FACULTY).document(facultyModel.Rank!!).get().addOnSuccessListener { documentSnapshot ->
            val message = if (documentSnapshot.exists()) {
                "Faculty Already Exists, Overwriting the Data"
            } else {
                "Faculty Added Successfully"
            }
            db.collection(FACULTY).document(facultyModel.Rank).set(facultyModel).addOnSuccessListener {
                showToast(context, message)
                inProcess = false
            }.addOnFailureListener {
                inProcess = false
                showToast(context, "Failed to add/update faculty")
            }
        }
    }

    fun getFacultyDetails() {
        inProcess = true
        db.collection(FACULTY).orderBy("id", Query.Direction.ASCENDING).get().addOnSuccessListener { result ->
            _faculty.value = result.mapNotNull { it.toObject(FacultyModel::class.java) }
            inProcess = false
        }
    }

    fun deleteFaculty(id: String, context: Context) {
        inProcess = true
        db.collection(FACULTY).document(id).delete().addOnSuccessListener {
            showToast(context, "Faculty Deleted Successfully")
            inProcess = false
        }.addOnFailureListener {
            showToast(context, "Faculty Delete Failed")
            inProcess = false
        }
    }

    fun addPdf(uri: Uri, context: Context, name: String) {
        inProcess = true
        val storageReference = storage.reference.child("pdfs/${uri.lastPathSegment}")
        val uploadTask = storageReference.putFile(uri)
        uploadTask.addOnSuccessListener {
            storageReference.downloadUrl.addOnSuccessListener { downloadUri ->
                val pdfData = pdfModel(downloadUri.toString(), name)
                db.collection(PDF).add(pdfData).addOnSuccessListener {
                    showToast(context, "PDF upload successful!")
                    inProcess = false
                }.addOnFailureListener {
                    showToast(context, "Failed to add PDF URL to Firestore: ${it.message}")
                    inProcess = false
                }
            }.addOnFailureListener {
                showToast(context, "Failed to get download URL: ${it.message}")
                inProcess = false
            }
        }.addOnFailureListener {
            showToast(context, "PDF upload failed: ${it.message}")
            inProcess = false
        }
    }

    private fun getPdfs() {
        db.collection(PDF).get().addOnSuccessListener { result ->
            _pdf.value = result.mapNotNull { it.toObject(pdfModel::class.java) }
        }
    }

    fun downloadPdf(url: String, fileName: String, context: Context) {
        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle(fileName)
            .setDescription("Downloading PDF")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "$fileName.pdf")
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)

        (context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager).enqueue(request)
        showToast(context, "Download started")
    }

    fun getAllNotifications() {
        _inRefresh.value = true
        db.collection(NOTIFICATION).orderBy("timestamp", Query.Direction.DESCENDING).get().addOnSuccessListener { result ->
            _notifications.value = result.mapNotNull { it.toObject(NotificationModel::class.java) }
            _inRefresh.value = false
        }
    }

    fun deleteNotification(notificationId: String) {
        db.collection(NOTIFICATION).document(notificationId).delete()
        getAllNotifications()
    }

    private fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun handleException(customMessage: String, exception: Exception? = null) {
        Log.e("Exception", customMessage, exception)
        exception?.printStackTrace()
        inProcess = false
    }

    fun logout() {
        auth.signOut()
        signedIn.value = false
        _user.value = null
    }
}