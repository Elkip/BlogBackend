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
    private val key: String?
    private val keyword: String?
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
        this.key = appConfig.propertyOrNull("ktor.deployment.key")?.getString()
        this.keyword = appConfig.propertyOrNull("ktor.security.keyword")?.getString()
        this.mailKey = appConfig.propertyOrNull("ktor.email.mailKey")?.getString()
    }

    fun getPassword(): String {
        if (this.key == null)
            throw NotFoundException()
        val passHelper = PasswordUtil(this.key)
        val encryptedPass = properties.getProperty("MARIA_PASS")
        return passHelper.decrypt(encryptedPass)
    }

    fun checkPassword(inputPass: String, realPassHash: String): Boolean {
        if (this.key == null)
            throw NotFoundException()
        val passHelper = PasswordUtil(this.key)
        return inputPass == passHelper.decrypt(realPassHash)
    }

    fun getEmailKey(): String {
        if (this.key == null || this.mailKey == null)
            throw NotFoundException()
        val passHelper = PasswordUtil(this.key)
        return passHelper.decrypt(this.mailKey)
    }

}
