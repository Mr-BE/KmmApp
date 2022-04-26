package dev.mrbe.kmmapp

import dev.mrbe.kmmapp.cache.AppDatabase
import sqldelight.DatabaseDriverFactory

internal class Database (databaseDriverFactory: DatabaseDriverFactory){
    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.appDatabaseQueries

    //concerned with data handling operations
    internal fun clearDatabase() {
        dbQuery.transaction {
            dbQuery.deleteAllPosts()
        }
    }

    //delete specific post
    internal fun deletePostById(id: Int) {
        dbQuery.transaction {
            dbQuery.deletePostById(id)
        }
    }

    internal fun getAllPosts(): List<Posts> {
        return dbQuery.selectAllPosts(::mapPostSelecting).executeAsList()
    }
    private fun mapPostSelecting (
        userId: Int,
        id: Int,
        title: String,
        body: String
    ): Posts {
        return Posts(
            userId = userId,
            id = id,
            title =  title,
            body = body
        )
    }

    //insert data to db
    internal fun insertPost(post: Posts) {
        dbQuery.insertPost(
            userId = post.userId,
            id = post.id,
            title = post.title,
            body = post.body
        )
    }
}

