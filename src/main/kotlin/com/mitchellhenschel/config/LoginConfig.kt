package com.mitchellhenschel.config

import com.mitchellhenschel.util.PasswordUtil
import io.ktor.server.config.*
import io.ktor.server.plugins.*
import java.util.*

class LoginConfig(appConfig: ApplicationConfig) {

    val hostname: String
    val mariaDbUri: String
    val mariaDbUser: String
    private val mariaDbName: String
    private val mariaDbPassEnc: String
    private val key: String?
    private val keyword: String
    private val mailKey: String?
    private val properties = Properties()

    init {
        this.hostname = appConfig.propertyOrNull("ktor.deployment.allowedHost")?.getString() ?: ""
        val env = appConfig.propertyOrNull("ktor.environment")?.getString()?.lowercase()
        val configFile = "default_$env.properties"
        val inStream = this.javaClass.classLoader.getResourceAsStream(configFile)
        properties.load(inStream)
        inStream?.close()
        val url = properties.getProperty("MARIA_IP")
        this.mariaDbName = properties.getProperty("MARIA_DB")
        this.mariaDbUser = properties.getProperty("MARIA_USER")
        this.mariaDbUri = "jdbc:mariadb://$url/$mariaDbName"
        this.mariaDbPassEnc = appConfig.property("ktor.db.pass").getString()
        this.key = appConfig.propertyOrNull("ktor.deployment.key")?.getString()
        this.keyword = appConfig.property("ktor.security.keyword").getString()
        this.mailKey = appConfig.propertyOrNull("ktor.email.mailKey")?.getString()
    }

    fun getPassword(): String {
        if (this.key == null)
            throw NotFoundException()
        val passHelper = PasswordUtil(this.key, this.keyword)
        return passHelper.decrypt(this.mariaDbPassEnc)
    }

    fun checkPassword(inputPass: String, realPassHash: String): Boolean {
        if (this.key == null)
            throw NotFoundException()
        val passHelper = PasswordUtil(this.key, this.keyword)
        return inputPass == passHelper.decrypt(realPassHash)
    }

    fun getEmailKey(): String {
        if (this.key == null || this.mailKey == null)
            throw NotFoundException()
        val passHelper = PasswordUtil(this.key, this.keyword)
        val mailKeyDec = passHelper.decrypt(this.mailKey)
        return mailKeyDec
    }

}
