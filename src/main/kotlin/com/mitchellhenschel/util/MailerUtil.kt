package com.mitchellhenschel.util

import com.mailgun.api.v3.MailgunMessagesApi
import com.mailgun.client.MailgunClient
import com.mailgun.model.message.Message
import com.mailgun.util.EmailUtil
import com.mitchellhenschel.features.contact.ContactEntity
import mu.KotlinLogging
import java.util.*

class MailerUtil(){

    private val props = Properties()
    private val log = KotlinLogging.logger {}

    init {
       props.load(this.javaClass.classLoader.getResourceAsStream("mail.properties"))
    }

    fun sendMessageMail(message: ContactEntity, key: String?) {
        log.info("Preparing to send email")
        val mailgun: MailgunMessagesApi = MailgunClient.config(key).createApi(MailgunMessagesApi::class.java)
        val body = message.toHTML()
        val email = Message.builder()
            .from(EmailUtil.nameWithEmail(props.getProperty("mail.from.name"), props.getProperty("mail.from.email")))
            .to(props.getProperty("mail.to"))
            .subject(props.getProperty("mail.subject"))
            .html(body)
            .build()

        try {
            mailgun.sendMessage(props.getProperty("mail.domain"), email)
            log.info("Email sent")
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}
