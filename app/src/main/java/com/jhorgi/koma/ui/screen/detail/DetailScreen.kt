package com.jhorgi.koma.ui.screen.detail

import android.app.Application
import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jhorgi.koma.di.Injection
import com.jhorgi.koma.ui.ViewModelFactory
import com.jhorgi.koma.ui.common.UiState
import com.jhorgi.koma.R
import com.jhorgi.koma.data.local.BookmarkList

@Composable
fun DetailScreen(
    id: String,
    viewModel: DetailViewModel= viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository(context = LocalContext.current)
        )
    ),
    navigateBack: () -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getItemById(id)
            }
            is UiState.Success -> {
                val data = uiState.data
                DetailContent(name = data.item.name, id = data.item.id, onBackClick = navigateBack, viewModel = viewModel)
            }
            is UiState.Error -> {}
        }
    }
}


@Composable
fun DetailContent(
    viewModel: DetailViewModel,
    name:String,
    id: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column {
        Text(text = name)
        Text(text = id)

        val isBookmared = viewModel.isBookmarked(id)
        BookmarkButton(viewModel,id, isBookmared)
    }
}

@Composable
fun BookmarkButton(
    viewModel: DetailViewModel,
    id: String,
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