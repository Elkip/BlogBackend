package com.mitchellhenschel

import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.routing.*
import io.ktor.server.testing.*
import io.ktor.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {

    @Test
    fun testRoot() {
        testApplication {
            environment {
                config = ApplicationConfig("application.conf")
            }
            routing {
                get("/") {
                    this.application.log.info(this.context.response.toString())
                    assertEquals(HttpStatusCode.OK, this.context.response.status())
                }
            }
        }
    }

    @OptIn(InternalAPI::class)
    @Test
    fun testBlog() {
        testApplication {
            val response = client.get("/blog/getAll")
            print("TEST")
            print(response.content.toString())
        }
    }
}
