package dev.tonholo.composeinstagram.data.fake

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
                profileImage = faker.avatar.image(),
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
}
