package com.mitchellhenschel.config

import io.ktor.server.config.*
import java.util.*

class BlogConfig(appConfig: ApplicationConfig) {

    val blogUrl: String
    val blogToken: String
    private val properties = Properties()

    init {
        this.blogToken = appConfig.property("ktor.blog.token").getString()
        val env = appConfig.propertyOrNull("ktor.environment")?.getString()?.lowercase()
        val configFile = "default_$env.properties"
        val inStream = this.javaClass.classLoader.getResourceAsStream(configFile)
        properties.load(inStream)
        inStream?.close()
        this.blogUrl = properties.getProperty("BLOG_IP")
    }

}
