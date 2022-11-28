package dev.tonholo.composeinstagram.domain

@JvmInline
value class UserTag(
    val tag: String,
) {
    companion object {
        val EMPTY = UserTag("")
    }
}

fun UserTag?.orEmpty() = this ?: UserTag.EMPTY
