package com.hxiloj.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal


@RestController
@RequestMapping("/rest/hello")
class DefaultRestController {
    @GetMapping("/principal")
    fun user(principal: Principal): Principal {
        return principal
    }

    @GetMapping
    fun hello(principal: Principal): String? {
        return "Hello " + principal.name
    }

}