package com.jhorgi.koma.ui.screen.bookmark

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jhorgi.koma.R
import com.jhorgi.koma.di.Injection
import com.jhorgi.koma.ui.ViewModelFactory
import com.jhorgi.koma.ui.components.BookmarkItem
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
        viewModel.getAllBookmark().map {
            val data = viewModel.getRecipeById(it.id)
            BookmarkItem(
                name = data.data.title,
                desc = data.data.body.toString(),
                photoUrl = data.data.images?.get(0).toString(),
                navigateToDetail = navigateToDetail,
                id = data.data.id
            )
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
