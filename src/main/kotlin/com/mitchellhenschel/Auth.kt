package com.mitchellhenschel

import com.mitchellhenschel.config.LoginConfig
import com.mitchellhenschel.features.contact.contactRoutes
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.forwardedheaders.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

fun Application.authModule() {
    val appConfig = this.environment.config
    val loginConfig = LoginConfig(appConfig)

    install(ForwardedHeaders) // WARNING: for security, do not include this if not behind a reverse proxy
    install(XForwardedHeaders) // WARNING: for security, do not include this if not behind a reverse proxy

    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Delete)
        allowHeader(HttpHeaders.XForwardedProto)
        allowHeader(HttpHeaders.AccessControlAllowHeaders)
        allowHeader(HttpHeaders.AccessControlAllowOrigin)
        allowHeader("Authorization")
        allowHeader("X-Requested-With")
        allowHeader(HttpHeaders.ContentType)
        allowHost(loginConfig.hostname, schemes = listOf("https"))
        maxAgeInSeconds = 7200
        allowNonSimpleContentTypes = true
        allowCredentials = true
    }

    contactRoutes(loginConfig)
}

