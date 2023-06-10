package com.jhorgi.koma.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.jhorgi.koma.ui.theme.poppins

@Composable
fun BookmarkItem(
    id: Int,
    name: String,
    desc: String,
    photoUrl: String,
    navigateToDetail: (String) -> Unit,
    modifier: Modifier = Modifier

) {

    Card(elevation = 5.dp, modifier = modifier
        .padding(10.dp)
        .fillMaxWidth()
        .clickable { navigateToDetail(id.toString()) }) {

        Row(modifier = modifier.padding(5.dp)) {
            SubcomposeAsyncImage(
                model = photoUrl,
                contentDescription = null,
                modifier = modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(1.dp, Color.LightGray, RoundedCornerShape(10.dp)),
                contentScale = ContentScale.FillHeight,

                ) {
                val state = painter.state
                if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                    CircularProgressIndicator(color = Color.Green, modifier = Modifier.size(20.dp))
                } else {
                    SubcomposeAsyncImageContent()
                }
            }

            Spacer(modifier = modifier.width(5.dp))

            Column {
                Text(
                    text = name,
                    fontFamily = poppins,
                    style = MaterialTheme.typography.caption.copy(
                        fontWeight = FontWeight.ExtraBold,
                    ),

                )
                Spacer(modifier = modifier.height(1.dp))
                Text(
                    text = desc,
                    fontFamily = poppins,
                    style = MaterialTheme.typography.caption.copy(
                        fontWeight = FontWeight.Light
                    ),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

            }

        }
    }

}