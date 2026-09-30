package com.example.bdskills_app.ui.components

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage

@Composable
fun ImagePicker(
    modifier: Modifier = Modifier,
    galleryButtonText: String = "Gallery",
    cameraButtonText: String = "Camera",
    // Backward compat: old buttonText param still works as gallery label
    buttonText: String? = null,
    onImageSelected: (Uri?) -> Unit = {},
    onPhotoCaptured: (Bitmap?) -> Unit = {}
) {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var photoBitmap by remember { mutableStateOf<Bitmap?>(null) }

    val galleryLabel = buttonText ?: galleryButtonText

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            imageUri = uri
            if (uri != null) photoBitmap = null
            onImageSelected(uri)
        }
    )

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = { bitmap: Bitmap? ->
            if (bitmap != null) {
                photoBitmap = bitmap
                imageUri = null
            }
            onPhotoCaptured(bitmap)
        }
    )

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            if (granted) cameraLauncher.launch(null)
        }
    )

    fun launchCamera() {
        val granted = ContextCompat.checkSelfPermission(
            context, Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
        if (granted) {
            cameraLauncher.launch(null)
        } else {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    Column(modifier = modifier) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = { galleryLauncher.launch("image/*") }) {
                Text(galleryLabel)
            }
            OutlinedButton(onClick = ::launchCamera) {
                Text(cameraButtonText)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        when {
            photoBitmap != null -> {
                Image(
                    bitmap = photoBitmap!!.asImageBitmap(),
                    contentDescription = "Camera photo",
                    modifier = Modifier.size(200.dp)
                )
            }
            imageUri != null -> {
                AsyncImage(
                    model = imageUri,
                    contentDescription = "Selected Image",
                    modifier = Modifier.size(200.dp)
                )
            }
        }
    }
}
