package com.example.dbmasterspringboot.testing

import com.example.dbmasterspringboot.domain.ResponseDTO
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class Test {

    @PostMapping("/v1/test")
    fun test(): ResponseDTO = ResponseDTO( "200")

}