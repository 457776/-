package com.example.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ui.screens.AddProductScreen
import com.example.ui.screens.CartScreen
import com.example.ui.screens.CategoriesScreen
import com.example.ui.screens.DetailsScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.PaymentMethodScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.ShippingAddressScreen
import com.example.ui.screens.WishlistScreen
import com.example.ui.screens.OrderHistoryScreen
import com.example.ui.screens.AdminLoginScreen
import com.example.ui.screens.AdminDashboardScreen
import com.example.ui.screens.ManageOrdersScreen
import com.example.ui.screens.ManageProductsScreen

@Composable
fun MainScreen(viewModel: ShopViewModel) {
    val navController = rememberNavController()
    
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController, viewModel = viewModel)
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(padding)
        ) {
            composable("home") {
                HomeScreen(
                    viewModel = viewModel,
                    onProductClick = { productId ->
                        navController.navigate("details/$productId")
                    },
                    onNavigateToProfile = { navController.navigate("profile") }
                )
            }
            composable("details/{productId}") { backStackEntry ->
                val productId = backStackEntry.arguments?.getString("productId") ?: return@composable
                DetailsScreen(
                    productId = productId,
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() },
                    onNavigateToOrders = { 
                        navController.navigate("order_history") {
                            popUpTo("home") { inclusive = false }
                        }
                    }
                )
            }
            composable("categories") {
                CategoriesScreen(
                    onCategoryClick = { category ->
                        navController.navigate("home") {
                            popUpTo("home") { inclusive = false }
                        }
                    }
                )
            }
            composable("cart") {
                CartScreen(
                    viewModel = viewModel,
                    onNavigateToOrders = { 
                        navController.navigate("order_history") {
                            popUpTo("home") { inclusive = false }
                        }
                    }
                )
            }
            composable("profile") {
                ProfileScreen(
                    onUploadProduct = { navController.navigate("admin_login") },
                    onNavigateToWishlist = { navController.navigate("wishlist") },
                    onNavigateToShipping = { navController.navigate("shipping") },
                    onNavigateToPayment = { navController.navigate("payment") },
                    onNavigateToSettings = { navController.navigate("settings") },
                    onNavigateToOrders = { navController.navigate("order_history") }
                )
            }
            composable("order_history") {
                OrderHistoryScreen(
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() }
                )
            }
            composable("wishlist") {
                WishlistScreen(
                    viewModel = viewModel,
                    onProductClick = { productId -> navController.navigate("details/$productId") },
                    onBack = { navController.popBackStack() }
                )
            }
            composable("shipping") {
                ShippingAddressScreen(
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() }
                )
            }
            composable("payment") {
                PaymentMethodScreen(onBack = { navController.popBackStack() })
            }
            composable("settings") {
                SettingsScreen(
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() }
                )
            }
            composable("add_product") {
                AddProductScreen(
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() }
                )
            }
            composable("edit_product/{productId}") { backStackEntry ->
                val productId = backStackEntry.arguments?.getString("productId")
                AddProductScreen(
                    viewModel = viewModel,
                    productId = productId,
                    onBack = { navController.popBackStack() }
                )
            }
            composable("admin_login") {
                AdminLoginScreen(
                    onLoginSuccess = { 
                        navController.navigate("admin_dashboard") {
                            popUpTo("admin_login") { inclusive = true }
                        }
                    },
                    onBack = { navController.popBackStack() }
                )
            }
            composable("admin_dashboard") {
                AdminDashboardScreen(
                    onNavigateToManageOrders = { navController.navigate("manage_orders") },
                    onNavigateToAddProduct = { navController.navigate("add_product") },
                    onNavigateToManageProducts = { navController.navigate("manage_products") },
                    onBack = { navController.popBackStack() }
                )
            }
            composable("manage_orders") {
                ManageOrdersScreen(
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() }
                )
            }
            composable("manage_products") {
                ManageProductsScreen(
                    viewModel = viewModel,
                    onNavigateToEditProduct = { productId -> navController.navigate("edit_product/$productId") },
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController, viewModel: ShopViewModel) {
    val items = listOf("home", "categories", "cart", "wishlist", "profile")
    val labels = listOf("Home", "Categories", "Cart", "Wishlist", "Profile")
    val icons = listOf(
        Icons.Filled.Home,
        Icons.Filled.Category,
        Icons.Filled.ShoppingCart,
        Icons.Filled.Favorite,
        Icons.Filled.Person
    )
    
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route?.substringBefore("/")
    
    // Do not show bottom bar on details screen
    if (currentRoute == "details" || currentRoute == "admin_login" || currentRoute == "admin_dashboard" || currentRoute == "manage_orders" || currentRoute == "manage_products" || currentRoute == "add_product" || currentRoute == "edit_product") return

    val cartItems by viewModel.cartItems.collectAsState()
    val cartCount = cartItems.sumOf { it.quantity }

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        items.forEachIndexed { index, route ->
            NavigationBarItem(
                icon = {
                    if (route == "cart" && cartCount > 0) {
                        BadgedBox(badge = { Badge { Text(cartCount.toString()) } }) {
                            Icon(icons[index], contentDescription = labels[index])
                        }
                    } else {
                        Icon(icons[index], contentDescription = labels[index])
                    }
                },
                label = { Text(labels[index]) },
                selected = currentRoute == route,
                onClick = {
                    if (currentRoute != route) {
                        navController.navigate(route) {
                            popUpTo("home") { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    }
}
