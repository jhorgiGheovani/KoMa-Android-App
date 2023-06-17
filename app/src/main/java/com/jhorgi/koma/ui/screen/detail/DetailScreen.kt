package com.jhorgi.koma.ui.screen.detail


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jhorgi.koma.R
import com.jhorgi.koma.data.local.BookmarkList
import com.jhorgi.koma.data.remote.response.ListIngredientsItem
import com.jhorgi.koma.data.remote.response.RecipeByIdResponse
import com.jhorgi.koma.di.Injection
import com.jhorgi.koma.ui.ViewModelFactory
import com.jhorgi.koma.ui.common.UiState
import com.jhorgi.koma.ui.components.LottieLoadingItem
import com.jhorgi.koma.ui.theme.poppins
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
    val recipe by viewModel.recipeLiveData.observeAsState(initial = UiState.Loading)
    when (recipe) {
        is UiState.Loading -> {
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .fillMaxHeight()
//                        .padding(8.dp)
//                        .shimmerEffect(),
//
//                )
//                Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LottieLoadingItem(
                    modifier = Modifier
                )
                Text(
                    text = "Please wait...",
                    style = MaterialTheme.typography.body2,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }
        }
        is UiState.Success -> {
            val recipeResult = (recipe as UiState.Success<RecipeByIdResponse>).data
            DetailContent(
                viewModel = viewModel,
                imageUrl = recipeResult.data.images,
                name = recipeResult.data.title ,
                id = recipeResult.data.id,
                body = recipeResult.data.body.toString(),
                ingredients = recipeResult.data.listIngredients,
                instructions = recipeResult.data.instructions.toString(),
                navigateBack = navigateBack
            )
        }
        is UiState.Error -> {
            // Show error state or handle the error
        }
    }
    LaunchedEffect(Unit) {
        viewModel.getRecipeById(id)
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
    navigateBack: () -> Unit,

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
                        fontFamily = poppins,
                        fontWeight = FontWeight.ExtraBold,
                    ),
                    modifier = Modifier.padding(bottom = 15.dp)
                )
                Text(
                    text = body,
                    fontFamily = poppins,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.body2
                )
                Row ( modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)) {
                    Text(
                        text = "Ingredients",
                        fontFamily = poppins,
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
                                    fontFamily = poppins,
                                    text = data.ingredient,
                                    style = MaterialTheme.typography.body2
                                )
                                Spacer(modifier = Modifier.height(5.dp))
                                Text(
                                    fontFamily = poppins,
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
                            fontFamily = poppins,
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
                        fontFamily = poppins,
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val isBookmared = viewModel.isBookmarked(id)
                val transparentColor = Color.Transparent.copy(alpha = 0.25f)
                Box(
                    modifier = Modifier
                        .padding(3.dp)
                        .background(transparentColor, shape = CircleShape),
                ) {
                    IconButton(onClick = {
                        navigateBack()
                    }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.arrow_left),
                            contentDescription = "arrow_left",
                            tint = Color.White
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .padding(3.dp)
                        .background(transparentColor, shape = CircleShape),
                ) {
                    BookmarkButton(viewModel = viewModel, id = id, isBookmarked = isBookmared)
                }

            }

    }

//        val isBookmared = viewModel.isBookmarked(id)
//
//        BookmarkButton(viewModel = viewModel, id = id, isBookmarked = isBookmared)
    }
}




@Composable
fun BookmarkButton(
    viewModel: DetailViewModel,
    id: Int,
    isBookmarked :Boolean,
    color: Color = colorResource(id = R.color.primary_color),
    borderColor: Color = colorResource(id = R.color.white)
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
