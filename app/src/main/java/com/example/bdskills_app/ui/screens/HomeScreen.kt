package com.example.bdskills_app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bdskills_app.R
import com.example.bdskills_app.data.sampleProducts
import com.example.bdskills_app.ui.components.ImagePicker
import com.example.bdskills_app.ui.components.ProductCard
import com.example.bdskills_app.ui.components.SectionTitle

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Image(
            modifier = Modifier.fillMaxWidth().height(300.dp),
            painter = painterResource(R.drawable.category),
            contentDescription = "Category",
        )
        ImagePicker()
        SectionTitle(text = "Products")

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(sampleProducts) { product ->
                ProductCard(
                    product = product,
                    onClick = {
                        navController.navigate("details/${product.name}/${product.price}/${product.description}")
                    }
                )
            }
        }
    }
}
