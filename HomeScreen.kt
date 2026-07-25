package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.MockData
import com.example.ui.ShopViewModel
import com.example.ui.components.ProductCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: ShopViewModel,
    onProductClick: (String) -> Unit,
    onNavigateToProfile: () -> Unit = {}
) {
    val products by viewModel.products.collectAsState()
    val wishlist by viewModel.wishlist.collectAsState()
    val cartItems by viewModel.cartItems.collectAsState()
    val cartCount = cartItems.sumOf { it.quantity }

    var searchQuery by remember { mutableStateOf("") }
    
    val displayProducts = remember(products, searchQuery) {
        if (searchQuery.isBlank() || searchQuery == "All Categories") {
            products
        } else {
            products.filter {
                it.name.contains(searchQuery, ignoreCase = true) || 
                it.category.contains(searchQuery, ignoreCase = true) ||
                it.brand.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(bottom = 80.dp) // padding for bottom nav
    ) {
        item(span = { GridItemSpan(2) }) {
            Column {
                // Top App Bar Area
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { onNavigateToProfile() }
                    ) {
                        // User Avatar
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surfaceVariant),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Filled.Person, contentDescription = "Profile", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        
                        Spacer(modifier = Modifier.width(8.dp))
                        
                        // Profile Chip
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = MaterialTheme.colorScheme.primaryContainer,
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Profile", color = MaterialTheme.colorScheme.onPrimaryContainer, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                    
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { /* Wishlist Action */ }) {
                            Icon(Icons.Filled.FavoriteBorder, contentDescription = "Wishlist")
                        }
                        IconButton(onClick = { /* Cart Action */ }) {
                            BadgedBox(badge = { if (cartCount > 0) Badge { Text(cartCount.toString()) } }) {
                                Icon(Icons.Filled.ShoppingCart, contentDescription = "Cart")
                            }
                        }
                    }
                }
                
                // Search Bar
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        placeholder = { Text("Search by Keyword or Product ID", fontSize = 14.sp) },
                        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search", tint = MaterialTheme.colorScheme.onSurfaceVariant) },
                        trailingIcon = {
                            Row {
                                Icon(Icons.Filled.Mic, contentDescription = "Mic", tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(end = 8.dp))
                                Icon(Icons.Filled.CameraAlt, contentDescription = "Camera", tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(end = 8.dp))
                            }
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    )
                }
                
                // Location Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Filled.LocationOn, contentDescription = "Location", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Delivering to Khargram - 742201", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Icon(Icons.Filled.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(18.dp))
                }
                
                // Categories
                val categoryImages = listOf(
                    "https://images.unsplash.com/photo-1515886657613-9f3515b0c78f?w=150&h=150&fit=crop", // All Categories
                    "https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?w=150&h=150&fit=crop", // Offers
                    "https://images.unsplash.com/photo-1610030469983-98e550d6193c?w=150&h=150&fit=crop", // Saree
                    "https://images.unsplash.com/photo-1550639525-c97d455acf70?w=150&h=150&fit=crop", // Western Wear
                    "https://images.unsplash.com/photo-1535632066927-ab7c9ab60908?w=150&h=150&fit=crop"  // Jewellery
                )
                val categoryNames = listOf("All Categories", "8 Pm Offers", "Saree", "Western Wear", "Jewellery")
                
                LazyRow(
                    modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface).padding(vertical = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(categoryNames.size) { index ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.clickable {
                                searchQuery = if (index == 0) "" else categoryNames[index]
                            }
                        ) {
                            AsyncImage(
                                model = categoryImages[index],
                                contentDescription = categoryNames[index],
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(64.dp)
                                    .clip(CircleShape)
                                    .border(1.dp, MaterialTheme.colorScheme.outlineVariant, CircleShape)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(categoryNames[index], fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface)
                        }
                    }
                }
                
                Divider(color = MaterialTheme.colorScheme.outlineVariant, thickness = 1.dp)
                
                // Filters Row
                Row(
                    modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface).padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FilterButton(icon = Icons.Filled.SwapVert, text = "Sort")
                    FilterButton(icon = Icons.Filled.KeyboardArrowDown, text = "Category", iconRight = true)
                    FilterButton(icon = Icons.Filled.KeyboardArrowDown, text = "Gender", iconRight = true)
                    FilterButton(icon = Icons.Filled.FilterList, text = "Filters")
                }
                
                Divider(color = MaterialTheme.colorScheme.outlineVariant, thickness = 1.dp)
            }
        }

        // Product Grid
        items(displayProducts) { product ->
            ProductCard(
                product = product,
                isWishlisted = wishlist.contains(product.id),
                onWishlistClick = { viewModel.toggleWishlist(product.id) },
                onClick = { onProductClick(product.id) },
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}

@Composable
fun FilterButton(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String, iconRight: Boolean = false) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { }.padding(8.dp)
    ) {
        if (!iconRight) {
            Icon(icon, contentDescription = null, modifier = Modifier.size(18.dp), tint = MaterialTheme.colorScheme.onSurface)
            Spacer(modifier = Modifier.width(4.dp))
        }
        Text(text, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Medium)
        if (iconRight) {
            Spacer(modifier = Modifier.width(4.dp))
            Icon(icon, contentDescription = null, modifier = Modifier.size(18.dp), tint = MaterialTheme.colorScheme.onSurface)
        }
    }
}
