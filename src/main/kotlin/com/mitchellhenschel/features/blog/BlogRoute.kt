package com.mitchellhenschel.features.blog

import com.mitchellhenschel.config.BlogConfig
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.blogRoutes(blogConfig: BlogConfig) = routing {

    get("/blog/getAll") {
        call.application.environment.log.info("Call blog getAll")
        val dataHandler = BlogRepo(blogConfig.blogUrl, blogConfig.blogToken)
        call.respond(dataHandler.getAllPosts())
    }

/*     get("/blog/getFiltered") {
        val dataHandler = BlogRepo(uri, dbName)
        val filter = call.receive<Tags>()
        call.application.environment.log.info("Call getFiltered with: $filter")
        call.respond(dataHandler.filterPosts(filter.tags))
    }

    get("/blog/getPost/{id}") {
        val dataHandler = BlogRepo(uri, dbName)
        val id = call.parameters["id"]
        call.application.environment.log.info("Call blog getPost id: $id")
        val post = dataHandler.getPost(id)
        if (post == null) {
            call.respondText("Post not found")
        } else {
            call.respond(post)
        }
    }

    get("/blog/getTags") {
        val dataHandler = BlogRepo(uri, dbName)
        call.application.environment.log.info("Call to get Tags")
        val filters = dataHandler.getTags()
        call.respond(filters.tags)
    }

   authenticate("auth-jwt") {

        post("/blog/savePost") {
            val dataHandler = BlogRepo(uri, dbName)
            val newPost = call.receive<BlogEntity>()
            call.application.environment.log.info("Received message: $newPost")
            val savedPost = dataHandler.savePost(newPost)
            if (savedPost == newPost)
                application.log.info("Post saved")
            else
                application.log.info("Post updated")
            call.respond(savedPost)
        }

        delete("/blog/deletePost/{id}") {
            val dataHandler = BlogRepo(uri, dbName)
            val id = call.parameters["id"]
            call.application.environment.log.info("Call blog getPost")
            val post = dataHandler.deletePost(id)
            if (post == null) {
                call.respondText("Post not found")
            } else {
                call.respond(post)
            }
        }
    }*/
}
