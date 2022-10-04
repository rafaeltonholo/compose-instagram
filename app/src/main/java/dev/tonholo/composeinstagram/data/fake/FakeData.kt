package dev.tonholo.composeinstagram.data.fake

import dev.tonholo.composeinstagram.domain.Comment
import dev.tonholo.composeinstagram.domain.Post
import dev.tonholo.composeinstagram.domain.PostLike
import dev.tonholo.composeinstagram.domain.Story
import dev.tonholo.composeinstagram.domain.User
import dev.tonholo.composeinstagram.domain.UserStory
import dev.tonholo.composeinstagram.domain.UserTag
import io.bloco.faker.Faker
import java.time.ZoneId
import kotlin.random.Random

object FakeData {
    val currentUser by lazy { users.first() }

    private val users: List<User> by lazy {
        val faker = Faker()
        List(100) {
            User(
                profileImage = generateRandomImage(),
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
        }
    }

    val posts: List<Post> by lazy {
        val faker = Faker()
        List(1000) {

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
                postDate = faker.date
                    .backward(10)
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime(),
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
    }

    private fun generateRandomImage(
        width: Int = 300,
        height: Int = 300,
    ): String = "https://picsum.photos/$width/$height.jpg?random=${Random.nextInt()}"
}
