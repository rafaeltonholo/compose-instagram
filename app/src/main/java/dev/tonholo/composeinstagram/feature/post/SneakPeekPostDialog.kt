package dev.tonholo.composeinstagram.feature.post

import android.graphics.Bitmap
import android.graphics.Rect
import android.os.Handler
import android.os.Looper
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.view.PixelCopy
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import dev.tonholo.composeinstagram.LocalWindow
import dev.tonholo.composeinstagram.domain.Post
import dev.tonholo.composeinstagram.domain.UserTag
import dev.tonholo.composeinstagram.ui.theme.Theme
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@Composable
fun SneakPeekPostDialog(
    post: Post,
    currentUserTag: UserTag,
    onDismiss: () -> Unit = {},
) {
    val window = LocalWindow.current
    val view = LocalView.current
    val context = LocalContext.current
    var background by remember { mutableStateOf<ImageBitmap?>(null) }
    LaunchedEffect(Unit) {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val location = IntArray(2)
        view.getLocationInWindow(location)
        val screenshot = suspendCoroutine<Bitmap> { continuation ->
            PixelCopy.request(
                window,
                Rect(
                    location[0],
                    location[1],
                    location[0] + view.width,
                    location[1] + view.height,
                ),
                bitmap,
                {
                    if (it == PixelCopy.SUCCESS) {
                        continuation.resume(bitmap)
                    } else {
                        continuation.resumeWithException(
                            Exception(
                                "Error trying to tak a screenshot from the screen. Error = $it"
                            )
                        )
                    }
                },
                Handler(Looper.getMainLooper())
            )
        }

        // TODO: apply Box Blur effect.
        val blurBitmap = RenderScript
            .create(context)
            .let { renderScript ->
                val input = Allocation.createFromBitmap(renderScript, screenshot)
                val output = Allocation.createTyped(renderScript, input.type)
                ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript)).apply {
                    setRadius(25f)
                    setInput(input)
                    forEach(output)
                }
                output.copyTo(screenshot)
                screenshot
            }

        background = blurBitmap.asImageBitmap()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Theme.colors.background.copy(alpha = 0.8f))
            .drawWithContent {
                background?.let { background ->
                    drawImage(background)
                    drawContent()
                }
            }
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onDismiss,
            ),
        contentAlignment = Alignment.Center,
    ) {
        SneakPeekPost(
            userTag = post.owner.userTag,
            profileImageUrl = post.owner.profileImage,
            image = post.images.first(),
            isPostLiked = post.likes.any { it.userTag == currentUserTag },
            modifier = Modifier
                .fillMaxWidth(0.8f),
        )
    }
}
