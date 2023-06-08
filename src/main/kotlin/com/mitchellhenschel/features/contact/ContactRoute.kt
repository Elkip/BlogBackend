package com.mitchellhenschel.features.contact

import com.mitchellhenschel.Response
import com.mitchellhenschel.config.LoginConfig
import com.mitchellhenschel.util.CommonRoutes
import com.mitchellhenschel.util.MailerUtil
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.contactRoutes(loginConfig: LoginConfig) = routing {

    get("/") {
        call.respond(Response(status = "OK"))
    }

    post(CommonRoutes.SEND) {
        val dataHandler = ContactRepo(loginConfig)
        call.application.environment.log.info("Receiving message...")
        var newMessage = call.receive<ContactEntity>()
        call.application.environment.log.debug("Received message: $newMessage")
        newMessage = dataHandler.insertMessage(newMessage, call.request.origin.remoteHost)

        val emailHelper = MailerUtil()
        emailHelper.sendMessageMail(newMessage, loginConfig.getEmailKey())
        call.respond(newMessage)
    }

}
