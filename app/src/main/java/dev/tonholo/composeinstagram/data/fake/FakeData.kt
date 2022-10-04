package dev.tonholo.composeinstagram.data.fake

import dev.tonholo.composeinstagram.domain.Comment
import dev.tonholo.composeinstagram.domain.Post
import dev.tonholo.composeinstagram.domain.PostLike
import dev.tonholo.composeinstagram.domain.Story
import dev.tonholo.composeinstagram.domain.User
import dev.tonholo.composeinstagram.domain.UserStory
import dev.tonholo.composeinstagram.domain.UserTag
import io.bloco.faker.Faker
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import kotlin.random.Random
import kotlin.time.DurationUnit
import kotlin.time.toDuration

object FakeData {
    val currentUser by lazy { users.first() }

    private val users: List<User> by lazy {
        val faker = Faker()
        List(100) {
            User(
                profileImage = if (it == 0) {
                    "https://i.picsum.photos/id/582/1920/1080.jpg?hmac=VrdLg-rz2_YPYMHM4CgQbfjyn4swqF-25M4CK8B2F5o"
                } else {
                    generateRandomImage()
                },
                name = faker.name.name(),
                userTag = UserTag(faker.internet.userName()),
            )
        }
    }

    val userStories: List<UserStory> by lazy {
        val faker = Faker()
        listOf(
            UserStory(
                owner = users[Random.nextInt(0, users.size)],
                stories = listOf(),
            )
        ) + List(10) {
            val user = users[Random.nextInt(0, users.size)]
            UserStory(
                owner = user,
                stories = List(Random.nextInt(0, 5)) {
                    Story(
                        mediaUrl = faker.placeholdit.image(),
                        postDate = faker.date
                            .backward(3)
                            .toInstant()
                            .atZone(ZoneId.systemDefault()).toLocalDate(),
                    )
                }
            )
        }.let { userStories ->
            if (userStories.none { it.owner == currentUser }) {
                listOf(
                    UserStory(
                        owner = currentUser,
                        stories = List(Random.nextInt(0, 2)) {
                            Story(
                                mediaUrl = faker.placeholdit.image(),
                                postDate = faker.date
                                    .backward(3)
                                    .toInstant()
                                    .atZone(ZoneId.systemDefault()).toLocalDate(),
                            )
                        }
                    )
                ) + userStories
            } else {
                userStories
            }
        }
    }

    val posts: List<Post> by lazy {
        val faker = Faker()
        val posts = List(1000) {

            val post = Post(
                owner = users[Random.nextInt(0, users.size)],
                images = List(Random.nextInt(1, 11)) { generateRandomImage(1920, 1080) },
                likes = List(Random.nextInt(0, 1000)) {
                    val user = users[Random.nextInt(0, users.size)]
                    PostLike(
                        userTag = user.userTag,
                        profileImageUrl = user.profileImage,
                    )
                }.toSet(),
                comments = null,
                postDate = generateRandomDate(),
                ownerComment = faker.lorem.sentence(),
            )

            val comments = List(Random.nextInt(0, 10)) {
                Comment(
                    owner = users[Random.nextInt(0, users.size)],
                    post = post,
                    text = faker.internet.slug(),
                    likes = Random.nextInt(0, 1000),
                )
            }
            post.copy(comments = comments)
        }
        posts
    }

    private fun generateRandomImage(
        width: Int = 300,
        height: Int = 300,
    ): String = "https://picsum.photos/$width/$height.jpg?random=${Random.nextInt()}"

    private fun generateRandomDate(): LocalDateTime {
        val now = System.currentTimeMillis()
        val thirtyDaysMilliseconds = 30.toDuration(DurationUnit.DAYS).inWholeMilliseconds
        val delta = now - thirtyDaysMilliseconds
        val randomMilliseconds = (delta + Random.nextLong(0, thirtyDaysMilliseconds))
            .let {
                if (it >= now) now
                else it
            }
        return Instant.ofEpochMilli(randomMilliseconds).atZone(ZoneId.systemDefault()).toLocalDateTime()
    }
}
