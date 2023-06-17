package com.jhorgi.koma.ui.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.jhorgi.koma.R


@Composable
fun BackButtonItem(

) {
    IconButton(
        onClick = { /*TODO*/ }
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.arrow_left),
            contentDescription = "button back"
        )
    }
}