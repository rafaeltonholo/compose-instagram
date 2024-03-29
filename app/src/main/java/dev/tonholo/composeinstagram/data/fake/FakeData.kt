package dev.tonholo.composeinstagram.data.fake

import dev.tonholo.composeinstagram.domain.Comment
import dev.tonholo.composeinstagram.domain.Message
import dev.tonholo.composeinstagram.domain.Post
import dev.tonholo.composeinstagram.domain.PostLike
import dev.tonholo.composeinstagram.domain.Story
import dev.tonholo.composeinstagram.domain.User
import dev.tonholo.composeinstagram.domain.UserFollow
import dev.tonholo.composeinstagram.domain.UserStory
import dev.tonholo.composeinstagram.domain.UserTag
import io.bloco.faker.Faker
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.UUID
import kotlin.random.Random
import kotlin.time.DurationUnit
import kotlin.time.toDuration

object FakeData {
    val currentUser by lazy { users.first() }

    val users: List<User> by lazy {
        val faker = Faker()
        List(1_000) {
            User(
                profileImage = if (it == 0) {
                    "https://i.picsum.photos/id/582/1920/1080.jpg?hmac=VrdLg-rz2_YPYMHM4CgQbfjyn4swqF-25M4CK8B2F5o"
                } else {
                    generateRandomImage()
                },
                name = faker.name.name(),
                userTag = UserTag(faker.internet.userName()),
                bio = when {
                    Random.nextBoolean() -> faker.lorem.paragraph()
                    Random.nextBoolean() -> faker.lorem.question()
                    Random.nextBoolean() -> faker.lorem.sentence()
                    Random.nextBoolean() -> listOf(
                        "😅",
                        "🛫",
                        "🙌",
                        "😊",
                        "🔥",
                        "❤️",
                    ).random()
                    else -> faker.book.title()
                },
                isPrivate = Random.nextBoolean(),
            )
        }
    }

    val userStories: List<UserStory> by lazy {
        val faker = Faker()
        listOf(
            UserStory(
                owner = users.random(),
                stories = listOf(),
            )
        ) + List(10) {
            val user = users.random()
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

    val posts: MutableList<Post> by lazy {
        val faker = Faker()
        fun post(user: User): Post {
            val post = Post(
                id = UUID.randomUUID().toString(),
                owner = user,
                images = List(Random.nextInt(1, 11)) { generateRandomImage(1920, 1080) },
                likes = List(Random.nextInt(0, 1000)) {
                    val likeUser = users.random()
                    PostLike(
                        userTag = likeUser.userTag,
                        profileImageUrl = user.profileImage,
                    )
                }.toSet(),
                comments = null,
                postDate = generateRandomDate(),
                ownerComment = faker.lorem.sentence(),
            )

            val comments = List(Random.nextInt(0, 10)) {
                Comment(
                    owner = users.random(),
                    post = post,
                    text = faker.internet.slug(),
                    likes = Random.nextInt(0, 1000),
                )
            }
            return post.copy(comments = comments)
        }

        val posts = List(100) {
            post(currentUser)
        } + List(1_000) {
            post(users.filterNot { it == currentUser }.random())
        }
        posts.toMutableList()
    }

    val messages: List<Message> by lazy {
        val faker = Faker()
        List(10000) {
            val from = users.random()
            Message(
                from = from,
                to = users.filterNot { it == from }[Random.nextInt(0, users.size - 1)],
                content = when {
                    Random.nextBoolean() -> faker.lorem.paragraph()
                    Random.nextBoolean() -> faker.lorem.question()
                    Random.nextBoolean() -> faker.lorem.sentence()
                    Random.nextBoolean() -> faker.lorem.supplemental()
                    else -> faker.book.title()
                },
                date = generateRandomDate(),
                hasRead = Random(System.currentTimeMillis()).nextBoolean(),
            )
        }
    }

    val userFollows: Set<UserFollow> by lazy {
        buildSet {
            users.forEach { user ->
                repeat(Random.nextInt(1, users.size)) {
                    val random = users.random()
                    if (random != user) {
                        add(UserFollow(user = user, follow = random))
                    }
                }
            }
        }
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
