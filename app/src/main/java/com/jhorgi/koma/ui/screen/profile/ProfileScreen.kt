package com.jhorgi.koma.ui.screen.profile

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jhorgi.koma.R
import com.jhorgi.koma.data.remote.response.GetUserDetailResponse
import com.jhorgi.koma.di.Injection
import com.jhorgi.koma.ui.ViewModelFactory
import com.jhorgi.koma.ui.common.UiState
import com.jhorgi.koma.ui.components.LottieLoadingAuthItem

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository(context = LocalContext.current)
        )
    ),
    navigateToLogin: () -> Unit,
    navigateToChangePassword: () -> Unit,
    navigateToEditProfile: (name: String, height:String, weight: String, phoneNum: String) -> Unit,
) {
    val context = LocalContext.current
    val userDetails by viewModel.userDetailLiveData.observeAsState(initial = UiState.Loading)

    LaunchedEffect(Unit) {
        val token= viewModel.getToken(context)
        viewModel.getUserDetails("Bearer ${token.value.toString()}")
    }

    Column(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "User Profile",
            style = MaterialTheme.typography.h1,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        when(userDetails){
            is UiState.Loading->{
                Column (
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Box(
                        modifier = Modifier
                            .width(80.dp)
                            .height(80.dp)
                            .padding(15.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        LottieLoadingAuthItem(modifier = Modifier)
                    }
                }

            }
            is UiState.Success ->{
                val response = (userDetails as UiState.Success<GetUserDetailResponse>).data
                Spacer(modifier = Modifier.height(15.dp))
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_edit),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(end = 8.dp)
                        .size(20.dp)
                        .clickable {
                            navigateToEditProfile(
                                response.data?.fullName.toString(),
                                response.data?.height.toString(),
                                response.data?.weight.toString(),
                                response.data?.phoneNumber.toString()
                            )
                        }
                )
                Spacer(modifier = Modifier.height(15.dp))
                Label(text = "Name")
                Spacer(modifier = Modifier.height(5.dp))
                textIsi(icon = ImageVector.vectorResource(id = R.drawable.user), name = response.data?.fullName.toString())
                Spacer(modifier = Modifier.height(15.dp))

                Label(text = "Email")
                Spacer(modifier = Modifier.height(5.dp))
                textIsi(icon = ImageVector.vectorResource(id = R.drawable.atsign), name = response.data?.email.toString())
                Spacer(modifier = Modifier.height(15.dp))

                Label(text = "Phone")
                Spacer(modifier = Modifier.height(5.dp))
                textIsi(icon = ImageVector.vectorResource(id = R.drawable.phone), name = response.data?.phoneNumber.toString())
                Spacer(modifier = Modifier.height(15.dp))

                Label(text = "Height")
                Spacer(modifier = Modifier.height(5.dp))
                if(response.data?.height==null){
                    textIsi(icon = ImageVector.vectorResource(id = R.drawable.ic_height), name = "-")
                }else{
                    textIsi(icon = ImageVector.vectorResource(id = R.drawable.ic_height), name = response.data.height.toString())
                }

                Spacer(modifier = Modifier.height(15.dp))

                Label(text = "Weight")
                Spacer(modifier = Modifier.height(5.dp))
                if(response.data?.weight==null){
                    textIsi(icon = ImageVector.vectorResource(id = R.drawable.ic_weight), name = "-")
                }else{
                    textIsi(icon = ImageVector.vectorResource(id = R.drawable.ic_weight), name = response.data.weight.toString())
                }
                Spacer(modifier = Modifier.height(15.dp))

                Label(text = "Calories")
                Spacer(modifier = Modifier.height(5.dp))
                if(response.data?.calories==null){
                    textIsi(icon = ImageVector.vectorResource(id = R.drawable.ic_calories), name = "-")
                }else{
                    textIsi(icon = ImageVector.vectorResource(id = R.drawable.ic_calories), name = response.data.calories)
                }
                Spacer(modifier = Modifier.height(15.dp))
            }
            is UiState.Error -> {}
        }
        Divider(startIndent = 8.dp, thickness = 1.dp, color = Color.LightGray)
        Spacer(modifier = Modifier.height(15.dp))
        Label(text = "Account")
        Spacer(modifier = Modifier.height(5.dp))
        changePassword(navigateToChangePassword = navigateToChangePassword)
        Spacer(modifier = Modifier.height(15.dp))
        logoutBtn(navigateToLogin = navigateToLogin, viewModel = viewModel, context = context)
    }
}


@Composable
private fun Label(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.body2.copy(
            fontWeight = FontWeight.ExtraBold
        ),
        color = Color.DarkGray,
        modifier = Modifier.padding(bottom = 4.dp)
    )
}

@Composable
private fun textIsi(icon: ImageVector, name: String) {
    Row {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = name,
            style = MaterialTheme.typography.body2,
        )
    }
}
@Composable
private fun logoutBtn(navigateToLogin: () -> Unit, viewModel: ProfileViewModel, context: Context){
    Row(
        modifier = Modifier.clickable {
            viewModel.setToken("", context)
            navigateToLogin()
        }
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.logout),
            contentDescription = "Log out"
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = "Log Out",
            style = MaterialTheme.typography.body2,
        )
    }
}

@Composable
private fun changePassword(navigateToChangePassword: () -> Unit){
    Row(
        modifier = Modifier.clickable {
            navigateToChangePassword()
        }
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.lock),
            contentDescription = "Change Password",
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = "Change Password",
            style = MaterialTheme.typography.body2,
        )
    }
}
