package com.mitchellhenschel.features.blog

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.Json

class BlogRepo(url: String, token: String) {

    suspend fun getPost(index: String?): BlogEntity {
        var blog = BlogEntity()
        val client = HttpClient()
        try {
            blog = client.get{
                url {
                    protocol = URLProtocol.HTTP
                    host = "localhost"
                    port = 8080
                    path("api/collections/get/blog")
                    parameters.append("token", "f149a4f33c0fa89cd293ca1e9c4881")
                }
            }.body<BlogEntity>()
            println("FINISHED: $blog")
        } catch (e: NoTransformationFoundException) {
            println("No Transformation found")
            val blogString: String = client.get{
                url {
                    protocol = URLProtocol.HTTP
                    host = "localhost"
                    port = 8080
                    path("api/collections/get/blog")
                    parameters.append("token", "f149a4f33c0fa89cd293ca1e9c4881")
                }
            }.body<String>()
            val json = Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            }
            blog = json.decodeFromString(BlogEntity.serializer(), blogString)
            println(blog)
        } catch (cause: Throwable) {
            println("ERROR $cause ${cause.message} ")
        } finally {
            client.close()
        }
        return blog
    }

    suspend fun getAllPosts(): BlogList {
        var blogs = BlogList()
        val client = HttpClient()
        try {
            blogs = client.get{
                url {
                    protocol = URLProtocol.HTTP
                    host = "localhost"
                    port = 8080
                    path("api/collections/get/blog")
                    parameters.append("token", "f149a4f33c0fa89cd293ca1e9c4881")
                }
        }.body<BlogList>()
            println("FINISHED: $blogs")
        } catch (e: NoTransformationFoundException) {
            println("No Transformation found")
            val blogString: String = client.get{
                url {
                    protocol = URLProtocol.HTTP
                    host = "localhost"
                    port = 8080
                    path("api/collections/get/blog")
                    parameters.append("token", "f149a4f33c0fa89cd293ca1e9c4881")
                }
            }.body<String>()
            val json = Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            }
            blogs = json.decodeFromString(BlogList.serializer(), blogString)
            println(blogs)
        } catch (cause: Throwable) {
            println("ERROR $cause ${cause.message} ")
        } finally {
            client.close()
        }
        return blogs
    }

    // Save new or update depending on if id is present
/*    fun savePost(newPost: BlogEntity): BlogEntity = if (getPost(newPost.id) == null) {
        // postCollection.insertOne(newPost)
        newPost
    } else {
        // val updateFields = BasicDBObject()
        updateFields.append("title", newPost.title)
        updateFields.append("tags", newPost.tags)
        updateFields.append("imageUrl", newPost.imageUrl)
        updateFields.append("user", newPost.user)
        updateFields.append("content", newPost.content)
        // postCollection.findOneAndUpdate(eq("_id", newPost.id), BasicDBObject("\$set", updateFields)) ?: BlogEntity()
    }*/

/*    fun deletePost(id: String?): BlogEntity? {
        if (id == null) {
            return null
        }
        return null
    }

    fun filterPosts(tag: List<String>): List<BlogEntity> {
        val inQuery = BasicDBObject()
        inQuery.put("tags", BasicDBObject("\$in", tag))
        return postCollection.find(inQuery).toList()
    }

    fun getTags(): Tags {
        val result = postCollection.distinct("tags", String::class.java).toList()
        return Tags(result)
    }*/
}
