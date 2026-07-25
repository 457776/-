package com.example.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.model.Product
import com.example.ui.ShopViewModel
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(viewModel: ShopViewModel, productId: String? = null, onBack: () -> Unit) {
    val existingProduct = remember(productId) {
        if (productId != null) viewModel.getProductById(productId) else null
    }

    var name by remember { mutableStateOf(existingProduct?.name ?: "") }
    var brand by remember { mutableStateOf(existingProduct?.brand ?: "") }
    var category by remember { mutableStateOf(existingProduct?.category ?: "") }
    var price by remember { mutableStateOf(existingProduct?.price?.toString() ?: "") }
    var discountPrice by remember { mutableStateOf(existingProduct?.discountPrice?.toString() ?: "") }
    var description by remember { mutableStateOf(existingProduct?.description ?: "") }
    var images by remember { mutableStateOf(existingProduct?.images ?: emptyList()) }
    var sizes by remember { mutableStateOf(existingProduct?.sizes?.joinToString(", ") ?: "") }
    var colors by remember { mutableStateOf(existingProduct?.colors?.joinToString(", ") ?: "") }

    val isEditing = existingProduct != null

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(maxItems = 4)
    ) { uris ->
        if (uris.isNotEmpty()) {
            images = uris.map { it.toString() }.take(4)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditing) "Edit Product" else "Upload Product") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Product Details", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Product Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    value = brand,
                    onValueChange = { brand = it },
                    label = { Text("Brand") },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    label = { Text("Category (e.g. Sneakers)") },
                    modifier = Modifier.weight(1f)
                )
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text("Price (₹)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = discountPrice,
                    onValueChange = { discountPrice = it },
                    label = { Text("Discount Price (₹)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Product Images (Max 4)", style = MaterialTheme.typography.titleMedium)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    images.forEachIndexed { index, imageUri ->
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                        ) {
                            AsyncImage(
                                model = imageUri,
                                contentDescription = "Product Image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                            IconButton(
                                onClick = { images = images.toMutableList().apply { removeAt(index) } },
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .size(24.dp)
                            ) {
                                Icon(Icons.Filled.Close, contentDescription = "Remove Image", tint = MaterialTheme.colorScheme.error)
                            }
                        }
                    }
                    if (images.size < 4) {
                        OutlinedButton(
                            onClick = {
                                photoPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            },
                            modifier = Modifier.size(80.dp),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Icon(Icons.Filled.AddPhotoAlternate, contentDescription = "Add Image")
                        }
                    }
                }
            }

            OutlinedTextField(
                value = sizes,
                onValueChange = { sizes = it },
                label = { Text("Sizes (comma separated)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = colors,
                onValueChange = { colors = it },
                label = { Text("Colors (comma separated)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val updatedProduct = Product(
                        id = existingProduct?.id ?: UUID.randomUUID().toString(),
                        name = name,
                        brand = brand,
                        category = category,
                        price = price.toDoubleOrNull() ?: 0.0,
                        discountPrice = discountPrice.toDoubleOrNull(),
                        rating = existingProduct?.rating ?: 5.0,
                        reviewCount = existingProduct?.reviewCount ?: 0,
                        description = description,
                        imageUrl = images.firstOrNull() ?: "https://images.unsplash.com/photo-1542291026-7eec264c27ff?q=80&w=1000&auto=format&fit=crop",
                        images = if (images.isNotEmpty()) images else listOf("https://images.unsplash.com/photo-1542291026-7eec264c27ff?q=80&w=1000&auto=format&fit=crop"),
                        sizes = sizes.split(",").map { it.trim() }.filter { it.isNotEmpty() },
                        colors = colors.split(",").map { it.trim() }.filter { it.isNotEmpty() },
                        stockStatus = true
                    )
                    
                    if (isEditing) {
                        viewModel.updateProduct(updatedProduct)
                    } else {
                        viewModel.addProduct(updatedProduct)
                    }
                    onBack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(if (isEditing) "Save Changes" else "Upload Product", fontWeight = FontWeight.Bold)
            }
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
