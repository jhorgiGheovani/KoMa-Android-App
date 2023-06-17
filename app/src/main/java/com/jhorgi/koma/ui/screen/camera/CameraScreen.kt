package com.jhorgi.koma.ui.screen.camera

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.core.UseCase
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.jhorgi.koma.EMPTY_IMAGE_URI
import com.jhorgi.koma.R
import com.jhorgi.koma.data.remote.response.PostPhoto
import com.jhorgi.koma.di.Injection
import com.jhorgi.koma.ui.ViewModelFactory
import com.jhorgi.koma.ui.common.UiState
import com.jhorgi.koma.ui.components.LottieErrorItem
import com.jhorgi.koma.ui.components.LottieLoadingItem
import com.jhorgi.koma.ui.screen.gallery.GalleryScreen
import com.jhorgi.koma.ui.screen.result.ResultViewModel
import com.jhorgi.koma.ui.utils.Permission
import com.jhorgi.koma.ui.utils.reduceFileImage
import com.jhorgi.koma.ui.utils.uriToFile
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


@ExperimentalPermissionsApi
@ExperimentalCoroutinesApi
@Composable
fun CameraScreen(
    modifier: Modifier = Modifier,
    cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
    onImageFile: (File) -> Unit = { },
    navController: NavHostController = rememberNavController(),
) {
    val context = LocalContext.current
    Permission(
        permission = Manifest.permission.CAMERA,
        rationale = "You said you wanted a picture, so I'm going to have to ask for permission.",
        permissionNotAvailableContent = {
            Column {
                Text("O noes! No Camera!")
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        context.startActivity(
                            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                data = Uri.fromParts("package", context.packageName, null)
                            }
                        )
                    }
                ) {
                    Text("Open Settings")
                }
            }
        }
    ) {
        Box(modifier = modifier) {
            val lifecycleOwner = LocalLifecycleOwner.current
            val coroutineScope = rememberCoroutineScope()
            var previewUseCase by remember { mutableStateOf<UseCase>(Preview.Builder().build()) }
            val imageCaptureUseCase by remember {
                mutableStateOf(
                    ImageCapture.Builder()
                        .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
                        .build()
                )
            }
            Box {
                CameraPreview(
                    modifier = Modifier.fillMaxSize(),
                    onUseCase = {
                        previewUseCase = it
                    },
                )
                CapturePictureButton(
                    modifier = Modifier
                        .size(100.dp)
                        .padding(16.dp)
                        .align(Alignment.BottomCenter),
                    onClick = {
                        coroutineScope.launch {
                            onImageFile(imageCaptureUseCase.takePicture(context.executor))

                        }
                    }
                )
            }
            LaunchedEffect(previewUseCase) {
                val cameraProvider = context.getCameraProvider()
                try {
                    // Must unbind the use-cases before rebinding them.
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner, cameraSelector, previewUseCase, imageCaptureUseCase
                    )
                } catch (ex: Exception) {
                    Log.e("CameraCapture", "Failed to bind camera use cases", ex)
                }
            }
        }

    }
}

@ExperimentalCoilApi
@ExperimentalCoroutinesApi
@ExperimentalPermissionsApi
@Composable
fun CameraContent(
    modifier: Modifier = Modifier
        .background(Color.Black),
    viewModel: ResultViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(LocalContext.current))
    ),
    navigationToResult: (String) -> Unit,
    navigateBack: () -> Unit,

) {
//    val openDialog by viewModel.open.observeAsState(false)

    val isLoading = remember { mutableStateOf(false) }
    var getFile: File?
    val context1 = LocalContext.current
    var imageUri by remember { mutableStateOf(EMPTY_IMAGE_URI) }

    if (imageUri != EMPTY_IMAGE_URI) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
            contentAlignment = Alignment.Center) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = rememberAsyncImagePainter(imageUri),
                contentDescription = "Captured image"
            )
            if(isLoading.value) {
                val rslt by viewModel.postPhotoLiveData.observeAsState(initial = UiState.Loading)
                when(rslt) {
                    is UiState.Loading -> {
                        Column {
                            LottieLoadingItem(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(),
                            )
                            Text(
                                modifier = Modifier.padding(start = 10.dp),
                                text = "Please wait...",
                                style = MaterialTheme.typography.body2,
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    is UiState.Error -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(15.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            LottieErrorItem(modifier = Modifier)
                            Text(
                                modifier = Modifier.padding(top = 5.dp),
                                text = "Page Not Found",
                                style = MaterialTheme.typography.body2,
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    is UiState.Success -> {
                        isLoading.value  = false
                        val resultPhoto = (rslt as UiState.Success<PostPhoto>).data
                        navigationToResult(resultPhoto.result)
                    }
                }
                LaunchedEffect(Unit) {
                    withContext(Dispatchers.IO) {
                        val myFile = uriToFile(imageUri, context1)
                        getFile = myFile
                        val file = reduceFileImage(getFile as File)
                        val requestImageFile = file.asRequestBody("image/jpeg".toMediaType())
                        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                            "file",
                            file.name,
                            requestImageFile
                        )
                        viewModel.postPhoto(imageMultipart)
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 10.dp,
                        top = 0.dp,
                        end = 10.dp,
                        bottom = 60.dp
                    )
                    .align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Button(
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White
                    ),
                    onClick = {
                        imageUri = EMPTY_IMAGE_URI
                    },
                    enabled = !isLoading.value
                ) {
                    Text(
                        color = colorResource(id = R.color.primary_color),
                        text = "Retake",
                        style = TextStyle(
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
                Button(
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = colorResource(id = R.color.primary_color)
                    ),
                    onClick = {
                        isLoading.value  = true
//                        viewModel.open.value = true
//                        Log.d("tettrvtv", result)
                    },
                    enabled = !isLoading.value
                ) {
                    Text(
                        color = Color.White,
                        text = "Use photo",
                        style = TextStyle(
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

            }
        }
    } else {
        var showGallerySelect by remember { mutableStateOf(false) }
        if (showGallerySelect) {
            GalleryScreen(
                modifier = modifier,
                onImageUri = { uri ->
                    showGallerySelect = false
                    imageUri = uri
                }
            )
        } else {
            Box(modifier = modifier) {
                val transparentColor = Color.Transparent.copy(alpha = 0.25f)
                CameraScreen(
                    modifier = modifier,
                    onImageFile = { file ->
                        imageUri = file.toUri()
                    }
                )
                Box(
                    modifier = Modifier
                        .padding(15.dp)
                        .background(transparentColor, shape = CircleShape),
                ) {
                    IconButton(onClick = {
                        navigateBack()
                    }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.arrow_left),
                            contentDescription = "arrow_left",
                            tint = Color.White
                        )
                    }
                }
                Button(
                    shape = CircleShape,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(
                            start = 20.dp,
                            top = 0.dp,
                            end = 0.dp,
                            bottom = 30.dp
                        )
                        .size(50.dp),
                    onClick = {
                        showGallerySelect = true
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Black
                    )
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.gallery),
                        contentDescription = "Gallery",
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }
    }
}



