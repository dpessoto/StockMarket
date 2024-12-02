package com.pessoto.stockmarket.core.presentation.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import coil.ImageLoader
import coil.compose.SubcomposeAsyncImage
import com.pessoto.stockmarket.core.R
import com.pessoto.stockmarket.core.presentation.extension.shimmerEffect
import org.koin.compose.getKoin

@Composable
fun ImageShimmer(
    url: String,
    contentDescription: String,
    size: Dp,
    shape: Shape,
    contentScale: ContentScale,
    imageLoader: ImageLoader = getKoin().get(),
) {
    SubcomposeAsyncImage(
        model = url,
        contentDescription = contentDescription,
        imageLoader = imageLoader,
        modifier = Modifier
            .size(size)
            .clip(shape),
        contentScale = contentScale,
        loading = {
            Box(
                modifier = Modifier
                    .size(size)
                    .clip(shape)
                    .shimmerEffect()
            )
        },
        error = {
           Box(
                modifier = Modifier
                    .size(size)
                    .clip(shape)
            ) {
                Text(
                    text = stringResource(id = R.string.image_load_failed),
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    )
}