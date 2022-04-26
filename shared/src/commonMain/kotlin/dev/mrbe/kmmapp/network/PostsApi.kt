package dev.mrbe.kmmapp.network

import dev.mrbe.kmmapp.Posts
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.*
import kotlinx.serialization.json.Json

class PostsApi {
    private val httpClient = HttpClient {
        install(JsonFeature) {
            val json = Json { ignoreUnknownKeys = true }
            serializer = KotlinxSerializer(json)
        }
    }

    //url
    companion object {
        private const val POSTS_ENDPOINT = "https://jsonplaceholder.typicode.com/posts"
    }

    //retrieve posts
    suspend fun getAllPosts(): List<Posts> {
        return httpClient.get(POSTS_ENDPOINT)
    }
}