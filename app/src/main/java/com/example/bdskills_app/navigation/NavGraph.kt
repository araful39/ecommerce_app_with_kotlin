package com.example.bdskills_app.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bdskills_app.ui.components.ConfirmDialog
import com.example.bdskills_app.ui.screens.DetailsScreen
import com.example.bdskills_app.ui.screens.HomeScreen
import com.example.bdskills_app.ui.screens.LoginScreen
import com.example.bdskills_app.ui.screens.RegisterScreen
import com.example.bdskills_app.ui.screens.SplashScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp() {
    val navController = rememberNavController()
    var showLogoutDialog by remember { mutableStateOf(false) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    if (showLogoutDialog) {
        ConfirmDialog(
            title = "Logout",
            message = "Are you sure you want to log out?",
            confirmText = "Logout",
            onConfirm = {
                showLogoutDialog = false
                navController.navigate("login") {
                    popUpTo(0) { inclusive = true }
                }
            },
            onDismiss = { showLogoutDialog = false }
        )
    }

    MaterialTheme {
        Scaffold(
            topBar = {
                if (currentRoute != "splash") {
                    TopAppBar(
                        title = { Text("দোকান App") },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        ),
                        actions = {
                            if (currentRoute == "home") {
                                TextButton(onClick = { showLogoutDialog = true }) {
                                    Text(
                                        text = "Logout",
                                        color = MaterialTheme.colorScheme.error,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    )
                }
            }
        ) { padding ->
            NavHost(
                navController = navController,
                startDestination = "splash",
                modifier = Modifier.padding(padding)
            ) {
                composable("splash") {
                    SplashScreen(navController = navController)
                }
                composable("login") {
                    LoginScreen(navController = navController)
                }
                composable("register") {
                    RegisterScreen(navController = navController)
                }
                composable("home") {
                    HomeScreen(navController = navController)
                }
                composable(
                    route = "details/{productName}/{price}/{description}",
                    arguments = listOf(
                        navArgument("productName") { type = NavType.StringType },
                        navArgument("price") { type = NavType.IntType },
                        navArgument("description") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val name = backStackEntry.arguments?.getString("productName") ?: ""
                    val price = backStackEntry.arguments?.getInt("price") ?: 0
                    val desc = backStackEntry.arguments?.getString("description") ?: ""
                    DetailsScreen(
                        navController = navController,
                        productName = name,
                        price = price,
                        description = desc
                    )
                }
            }
        }
    }
}
