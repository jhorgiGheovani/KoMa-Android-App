package com.jhorgi.koma.ui.screen.detail


import android.widget.Toast
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jhorgi.koma.di.Injection
import com.jhorgi.koma.ui.ViewModelFactory
import com.jhorgi.koma.ui.common.UiState
import com.jhorgi.koma.R
import com.jhorgi.koma.data.local.BookmarkList
import com.jhorgi.koma.data.remote.response.ListIngredientsItem
import com.jhorgi.koma.data.remote.response.RecipeByIdResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
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
    var isLoading by remember {
        mutableStateOf(true)
    }
    val recipe by viewModel.recipeLiveData.observeAsState(initial = UiState.Loading)
    when (recipe) {
        is UiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(8.dp)
                        .shimmerEffect(),

                )
                Spacer(modifier = Modifier.width(16.dp))
        }
        is UiState.Success -> {
            val recipe = (recipe as UiState.Success<RecipeByIdResponse>).data
            DetailContent(
                viewModel = viewModel,
                imageUrl = recipe.data.images,
                name = recipe.data.title ,
                id = recipe.data.id,
                body = recipe.data.body.toString(),
                ingredients = recipe.data.listIngredients,
                instructions = recipe.data.instructions.toString(),
            )
        }
        is UiState.Error -> {
            // Show error state or handle the error
        }
    }
    LaunchedEffect(Unit) {
        viewModel.getRecipeById(id)
        delay(10000)
    }

}


@OptIn(ExperimentalMaterialApi::class)
@Composable
@ExperimentalMaterial3Api
fun DetailContent(
    viewModel : DetailViewModel,
    imageUrl : List<String>,
    name :String,
    id : Int,
    body : String,
    ingredients : List<ListIngredientsItem>,
    instructions : String,

){

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()
    val state = rememberScrollState()
    LaunchedEffect(Unit) { state.animateScrollTo(0)}

    BottomSheetScaffold(
        sheetElevation = 50.dp,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        scaffoldState = scaffoldState,
        sheetPeekHeight = 130.dp,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 25.dp, end = 25.dp)
                    .verticalScroll(state)) {
                IconButton(
                    onClick = {
                        scope.launch {
                            if(scaffoldState.bottomSheetState.isCollapsed) {
                                scaffoldState.bottomSheetState.expand()
                            } else {
                                scaffoldState.bottomSheetState.collapse()
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon (
                        imageVector = ImageVector.vectorResource(id = R.drawable.vector),
                        contentDescription = "drag_handle",
                        tint = Color.Gray,

                    )
                    Alignment.CenterHorizontally
                }
                Text(
                    text = name,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body1.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                    modifier = Modifier.padding(bottom = 15.dp)
                )
                Text(
                    text = body,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.body2
                )
                Row ( modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)) {
                    Text(
                        text = "Ingredients",
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.body1
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.shopping_cart) ,
                        contentDescription = "ingredients",
                        tint = Color.Black
                    )
                }

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    var i = 1
                    ingredients.forEach { data ->
                        Row {
                            Text(
                                text = "$i.",
                                style = MaterialTheme.typography.body2
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Column {
                                Text(
                                    text = data.ingredient,
                                    style = MaterialTheme.typography.body2
                                )
                                Spacer(modifier = Modifier.height(5.dp))
                                Text(
                                    text = data.desc,
                                    color = Color.Gray,
                                    style = MaterialTheme.typography.body2
                                )
                            }
                        }
                        i++
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                    Row ( modifier = Modifier.padding(top = 15.dp, bottom = 15.dp)) {
                        Text(
                            text = "Instructions",
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.body1
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.layers) ,
                            contentDescription = "instructions",
                            tint = Color.Black
                        )
                    }

                    Text(
                        text = instructions,
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.body2
                    )
                }
                Spacer(modifier = Modifier.height(15.dp))
            }
    }) {
        Box {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl[0])
                    .crossfade(true)
                    .build(),
                contentDescription = "Recipe Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            )
    }

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
fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition()
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1000)
        )
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color(0xFFB8B5B5),
                Color(0xFF8F8B8B),
                Color(0xFFB8B5B5),
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    )
        .onGloballyPositioned {
            size = it.size
        }
}
