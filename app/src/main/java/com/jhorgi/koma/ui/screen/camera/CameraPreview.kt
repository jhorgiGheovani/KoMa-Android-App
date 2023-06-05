package com.jhorgi.koma.ui.screen.camera

import android.content.ContentValues.TAG
import android.util.Log
import android.view.OrientationEventListener
import android.view.Surface
import android.view.ViewGroup
import androidx.camera.core.Preview
import androidx.camera.core.UseCase
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.runtime.getValue
// for a 'val' variable
import androidx.compose.runtime.getValue

// for a `var` variable also add
import androidx.compose.runtime.setValue

// or just
import androidx.compose.runtime.*
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Observer


@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    scaleType: PreviewView.ScaleType = PreviewView.ScaleType.FILL_CENTER,
    onUseCase: (UseCase) -> Unit = { },
    onTapToFocus: (Offset) -> Unit = { }
) {

    AndroidView(
        modifier = modifier,
        factory = { context ->
            val previewView = PreviewView(context).apply {
                this.scaleType = scaleType
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )

            }
            onUseCase(

                Preview.Builder()
                    .build()
                    .also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }
            )
            previewView

        }
    )
}
