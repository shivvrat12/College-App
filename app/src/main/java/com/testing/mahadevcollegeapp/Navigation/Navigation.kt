package com.testing.mahadevcollegeapp.Navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.testing.mahadevcollegeapp.CollegeViewModel
import com.testing.mahadevcollegeapp.Screens.AddFacultyScreen
import com.testing.mahadevcollegeapp.Screens.AddpdfScreen
import com.testing.mahadevcollegeapp.Screens.ChechSignedinScreen
import com.testing.mahadevcollegeapp.Screens.FacultyScreen
import com.testing.mahadevcollegeapp.Screens.FirstScreen
import com.testing.mahadevcollegeapp.Screens.HomePage
import com.testing.mahadevcollegeapp.Screens.LoginPage
import com.testing.mahadevcollegeapp.Screens.ProfileScreen
import com.testing.mahadevcollegeapp.Screens.SignUp

enum class Navigation(val route: String) {
    CheckSignedinScreen("CheckSignedinScreen"),
    StartScreen("StartScreen"),
    SignUpScreen("SignUP Screen"),
    LoginScreen("LoginScreen"),
    HomeScreen("HomeScreen"),
    ProfileScreen("ProfileScreen"),
    AddFacultyScreen("AddFaculty"),
    FacultyScreen("FacultyScreen"),
    AddpdfScreen("AddpdfScreen")
}

@Composable
fun CollegeNav() {
    val vm :CollegeViewModel = viewModel()
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Navigation.CheckSignedinScreen.route) {
        composable(route = Navigation.CheckSignedinScreen.route){
            ChechSignedinScreen(vm = vm, navController = navController)
        }
        composable(route = Navigation.StartScreen.route) {
            FirstScreen(vm, navController)
        }
        composable(route = Navigation.LoginScreen.route) {
            LoginPage(vm, navController)
        }
        composable(route = Navigation.SignUpScreen.route) {
            SignUp(vm, navController)
        }
        composable(route = Navigation.HomeScreen.route){
            HomePage(vm, navController)
        }
        composable(route = Navigation.ProfileScreen.route){
            ProfileScreen(vm, navController)
        }
        composable(route = Navigation.AddFacultyScreen.route){
            AddFacultyScreen(vm)
        }
        composable(route = Navigation.FacultyScreen.route){
            FacultyScreen(vm = vm)
        }
        composable(route = Navigation.AddpdfScreen.route){
            AddpdfScreen(vm = vm)
        }
    }
}