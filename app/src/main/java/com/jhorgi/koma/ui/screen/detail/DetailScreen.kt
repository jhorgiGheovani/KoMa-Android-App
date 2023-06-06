package com.jhorgi.koma.ui.screen.detail


import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jhorgi.koma.di.Injection
import com.jhorgi.koma.ui.ViewModelFactory
import com.jhorgi.koma.ui.common.UiState
import com.jhorgi.koma.R
import com.jhorgi.koma.data.local.BookmarkList
import com.jhorgi.koma.data.remote.response.RecipeByIdResponse


@Composable
fun DetailScreen(
    id: Int,
    viewModel: DetailViewModel= viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository(context = LocalContext.current)
        )
    ),
    navigateBack: () -> Unit,
) {
    val context = LocalContext.current

    val recipe by viewModel.recipeLiveData.observeAsState(initial = UiState.Loading)
    when (recipe) {
        is UiState.Loading -> {
            Toast.makeText(context, "Loding", Toast.LENGTH_SHORT).show()
            // Show loading state
        }
        is UiState.Success -> {
            val recipe = (recipe as UiState.Success<RecipeByIdResponse>).data
            DetailContent(viewModel = viewModel, name = recipe.data.title , id = recipe.data.id, onBackClick = { /*TODO*/ })
            // Use the recipe data in your UI
        }
        is UiState.Error -> {
            // Show error state or handle the error
        }
    }
    LaunchedEffect(Unit) {
        viewModel.getRecipeById(id)
    }

}


@Composable
fun DetailContent(
    viewModel: DetailViewModel,
    name:String,
    id: Int,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
){

    Column {
        Text(text = name,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h5.copy(
                fontWeight = FontWeight.ExtraBold
            ),)
        Text(text = id.toString())
        val isBookmared = viewModel.isBookmarked(id)
        BookmarkButton(viewModel,id, isBookmared)
    }
}

@Composable
fun BookmarkButton(
    viewModel: DetailViewModel,
    id: Int,
    isBookmarked :Boolean,
    modifier: Modifier = Modifier,
    color: Color = Color(0xFFCBDB2B),
    borderColor: Color = Color(0xFF6B7070)
){

    var isBookmark by remember { mutableStateOf(isBookmarked) }
    IconToggleButton(
        checked = isBookmark ,
        onCheckedChange = {
            isBookmark = !isBookmark
        }
    ) {

        if(isBookmark){
            viewModel.addBookmark(BookmarkList(id))
            Icon(
                tint = color,
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_bookmark_fullfill),
                contentDescription = "Bookmarked"
            )

        }else{
            viewModel.deleteBookmark(BookmarkList(id))
            Icon(
                tint = borderColor,
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_bookmark_border),
                contentDescription = "Not bookmarked")
        }

//        Icon(
//            tint = color,
//            modifier = modifier.graphicsLayer {
//                scaleX = 1.3f
//                scaleY = 1.3f
//            },
//            imageVector = if (isBookmark) {
//                ImageVector.vectorResource(id = R.drawable.ic_bookmark_fullfill)
//            } else {
//                ImageVector.vectorResource(id = R.drawable.ic_bookmark_border)
//            },
//            contentDescription = "Bookmark Button" ,
//        )

    }
}