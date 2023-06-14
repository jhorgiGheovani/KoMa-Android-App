package com.jhorgi.koma.ui.screen.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jhorgi.koma.R
import com.jhorgi.koma.di.Injection
import com.jhorgi.koma.ui.ViewModelFactory

@Composable
fun ProfileScreen (
    viewModel: ProfileViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository(context = LocalContext.current)
        )
    ),
    navigateToLogin: () -> Unit,
){
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "User Profile",
            style = MaterialTheme.typography.h1,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "Name",
            style = MaterialTheme.typography.h1,
        )
        Spacer(modifier = Modifier.height(5.dp))
        Row {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.user) ,
                contentDescription = "user"
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "Name",
                style = MaterialTheme.typography.body2,
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Account",
            style = MaterialTheme.typography.h1,
        )
        Spacer(modifier = Modifier.height(5.dp))

        Row(
            modifier = Modifier.clickable {
                viewModel.setToken("", context)
                navigateToLogin()
            }
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.logout) ,
                contentDescription = "Log out"
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "Log Out",
                style = MaterialTheme.typography.body2,
            )
        }

    }
}