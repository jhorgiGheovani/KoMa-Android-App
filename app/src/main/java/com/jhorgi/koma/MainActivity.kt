

package com.jhorgi.koma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.jhorgi.koma.ui.theme.KomaTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalPermissionsApi::class)
class MainActivity : ComponentActivity() {

//    private val viewModel : SplashViewModel by viewModels()
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
//        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

//        splashScreen.setKeepOnScreenCondition(viewModel.isLoading.value)

        setContent {
            KomaTheme {
//                val systemUiController = rememberSystemUiController()
//                SideEffect {
//                    // set transparent color so that our image is visible
//                    // behind the status bar
//                    systemUiController.setStatusBarColor(color = Color.Transparent)
//                }

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    KomaApplication()
                }
            }
        }
    }
}
