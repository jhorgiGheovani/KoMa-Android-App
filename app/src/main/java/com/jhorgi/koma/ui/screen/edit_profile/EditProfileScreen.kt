package com.jhorgi.koma.ui.screen.edit_profile

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jhorgi.koma.R
import com.jhorgi.koma.data.remote.response.GenericResponse
import com.jhorgi.koma.data.remote.response.UpdateProfileRequestBody
import com.jhorgi.koma.di.Injection
import com.jhorgi.koma.ui.ViewModelFactory
import com.jhorgi.koma.ui.common.UiState
import com.jhorgi.koma.ui.components.LottieLoadingAuthItem
import com.jhorgi.koma.ui.components.TextFieldLabel


@Composable
fun EditProfileScreen(
    name: String,
    height: String,
    weight: String,
    phonenum: String,
    viewModel: EditProfileViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository(context = LocalContext.current)
        )
    ),
    navigateToProfile : () -> Unit

) {



    val editProfileLiveData by viewModel.editProfileLiveData.observeAsState(initial = UiState.Loading)
    var isButtonClicked by remember { mutableStateOf(false) }
    var nameM by rememberSaveable { mutableStateOf(name) }
    var weightM by rememberSaveable { mutableStateOf(weight) }
    var heightM by rememberSaveable { mutableStateOf(height) }
    var phoneNumberM by rememberSaveable { mutableStateOf(phonenum) }
    val context = LocalContext.current


    val token = viewModel.getToken(context)

    if(heightM=="null"){
        heightM="0"
    }


    if(weightM=="null"){
        weightM="0"
    }

    if(phoneNumberM=="null"){
        phoneNumberM="-"
    }


    Column(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth()
    ) {

        Text(
            text = "Edit Profile Info",
            style = MaterialTheme.typography.h1,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(15.dp))
        TextFieldLabel(text = "Full Name")
        EditInfoField(
            nameM,
            { newValue -> nameM = newValue },
            "Full Name",
            ImageVector.vectorResource(id = R.drawable.user)
        )
        Spacer(modifier = Modifier.height(15.dp))
        TextFieldLabel(text = "Height")
        EditInfoField(
            heightM,
            { newValue -> heightM = newValue },
            "Height",
            ImageVector.vectorResource(id = R.drawable.ic_height)
        )
        Spacer(modifier = Modifier.height(15.dp))
        TextFieldLabel(text = "Weight")
        EditInfoField(
            weightM,
            { newValue -> weightM = newValue },
            "Weight",
            ImageVector.vectorResource(id = R.drawable.ic_weight)
        )
        Spacer(modifier = Modifier.height(15.dp))
        TextFieldLabel(text = "Phone Number")
        EditInfoField(
            phoneNumberM,
            { newValue -> phoneNumberM = newValue },
            "Phone Number",
            ImageVector.vectorResource(id = R.drawable.lock)
        )
        Spacer(modifier = Modifier.height(25.dp))
        Button(
            onClick = {

                if(nameM==""){
                    nameM="-"
                }
                if(weightM==""){
                    weightM="0"
                }
                if(heightM==""){
                    heightM="0"
                }
                if(phoneNumberM == ""){
                    phoneNumberM = "-"
                }
                isButtonClicked = true
            },
            content = {
                Text(
                    text = "Update Profile",
                    style = MaterialTheme.typography.body1.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color.White
                )
            },
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.primary_color)),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
        )
        if (isButtonClicked) {
            when (editProfileLiveData) {
                is UiState.Loading -> {
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
                is UiState.Success -> {
                    val response = (editProfileLiveData as UiState.Success<GenericResponse>).data
                    navigateToProfile()
                    Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
                    isButtonClicked = false
                }
                is UiState.Error -> {
                    isButtonClicked = false
                    Toast.makeText(LocalContext.current, "Error", Toast.LENGTH_SHORT).show()
                }
            }


            LaunchedEffect(Unit) {
                viewModel.upadateProfile(
                    UpdateProfileRequestBody(
                        phoneNumber = phoneNumberM,
                        fullName = nameM,
                        weight = weightM.toInt(),
                        height = heightM.toInt()
                    ), "Bearer ${token.value.toString()}"
                )
            }
        }
    }

}

@Composable
fun EditInfoField(
    value: String,
    onTextInputChange: (String) -> Unit,
    placeholder: String,
    icon: ImageVector
) {
    TextField(
        value = value,
        onValueChange = onTextInputChange,
        placeholder = { Text(text = placeholder, style = MaterialTheme.typography.caption) },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = null
            )
        },
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.LightGray, RoundedCornerShape(7.dp)),

        )
}

