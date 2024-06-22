package com.example.todolist.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.todolist.ui.theme.TodoListTheme

@Composable
fun RoundedTopBar(
    modifier: Modifier = Modifier,
    height: Dp = 50.dp,
    topPartColor: Color = MaterialTheme.colorScheme.primary,
    bottomPartColor: Color = MaterialTheme.colorScheme.background,
    title: String = ""
) {
    val cornerRadius = with(LocalDensity.current) { 20.dp.toPx() }
    Box(
        modifier = modifier
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .drawWithCache {
                val path = Path().apply {
                    addRoundRect(
                        roundRect = RoundRect(
                            left = 0f,
                            right = size.width,
                            top = 0f,
                            bottom = size.height,
                            cornerRadius = CornerRadius(cornerRadius, cornerRadius)
                        )
                    )
                    close()
                }

                onDrawBehind {
                    drawPath(
                        path = path,
                        color = topPartColor
                    )
                }
            }
        )
        //        Box(modifier = Modifier
        //            .fillMaxWidth()
        //            .height(20.dp)
        //            .align(Alignment.BottomCenter)
        //            .drawWithCache {
        //                val path = Path().apply {
        //                    addRoundRect(
        //                        roundRect = RoundRect(
        //                            left = 0f,
        //                            right = size.width,
        //                            top = 0f,
        //                            bottom = size.height,
        //                            topRightCornerRadius = CornerRadius(x = cornerRadius, y = cornerRadius),
        //                            topLeftCornerRadius = CornerRadius(x = cornerRadius, y = cornerRadius)
        //                        )
        //                    )
        //                    close()
        //                }
        //                onDrawBehind {
        //                    drawPath(
        //                        path = path,
        //                        color = bottomPartColor
        //                    )
        //                }
        //
        //            }
        //        )

        Text(
            modifier = Modifier.align(Alignment.Center),
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onPrimary),
        )
    }
}

@Preview
@Composable
fun RoundedTopBarPreview() {
    TodoListTheme {
        RoundedTopBar(
            title = "Title"
        )
    }
}
