package com.hxiloj

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open  class SpringBoot2Oauth2LdapApplication

fun main(args: Array<String>) {
    runApplication<SpringBoot2Oauth2LdapApplication>(*args)
}
