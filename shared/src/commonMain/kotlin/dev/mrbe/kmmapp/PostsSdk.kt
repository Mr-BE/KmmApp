package dev.mrbe.kmmapp

import dev.mrbe.kmmapp.network.PostsApi
import sqldelight.DatabaseDriverFactory

class PostsSdk(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = Database(databaseDriverFactory)
    private val api = PostsApi()

    //load info from web or from cache (@throws needed for swift)
    @Throws(Exception::class) suspend fun getPosts (forceReload: Boolean)
    : List<Posts> {
       val cachedPosts = database.getAllPosts()
       //check if cache is empty
       return if (cachedPosts.isNotEmpty() && !forceReload){
           cachedPosts
       } else {
           api.getAllPosts().also {
               database.clearDatabase()
           }
       }
    }
}