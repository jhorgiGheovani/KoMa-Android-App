package com.jhorgi.koma.ui.screen.bookmark

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jhorgi.koma.R
import com.jhorgi.koma.di.Injection
import com.jhorgi.koma.ui.ViewModelFactory
import com.jhorgi.koma.ui.components.BookmarkItem
import com.jhorgi.koma.ui.components.LottieEmptyItem
import com.jhorgi.koma.ui.theme.poppins


@Composable
fun BookmarkScreen(
    viewModel: BookmarkViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(LocalContext.current))
    ),
    navigateToDetail: (String) -> Unit,
) {

    Column(modifier = Modifier.padding(top = 15.dp)) {
        BookmarkTittle()
        Spacer(modifier = Modifier.height(15.dp))
        val result = viewModel.getAllBookmark()
        if (result.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LottieEmptyItem(modifier = Modifier)
                Text(
                    modifier = Modifier.padding(top = 300.dp),
                    text = "No data",
                    style = MaterialTheme.typography.body2,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )

            }
        } else {
            result.map {
                val data = viewModel.getRecipeById(it.id)
                BookmarkItem(
                    name = data.data.title,
                    desc = data.data.body.toString(),
                    photoUrl = data.data.images[0],
                    navigateToDetail = navigateToDetail,
                    id = data.data.id
                )

            }
        }
    }
}

@Composable
private fun BookmarkTittle() {
    Text(
        text = "Bookmarks",
        fontFamily = poppins,
        color = colorResource(id = R.color.grey_tittle_color),
        style = MaterialTheme.typography.h5.copy(
            fontWeight = FontWeight.Bold
        ),
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}
