package com.example.dbmasterspringboot.controller.controlmail

import com.example.dbmasterspringboot.data.dto.ResponseDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.sql.SQLException
import java.util.*
import kotlin.collections.HashMap

@RestController
class CustomMailSender(
        @Autowired val jdbcTemplate: JdbcTemplate
) {
    @RequestMapping("/v1/auth/request")
    fun insertTable(
            @RequestBody response: HashMap<String, String>
    ): ResponseDTO {
        //파라미터 받고
        val email = response["email"]
        if (email == null) {
            return ResponseDTO("E01", "파라미터가 잘못 설정됬습니다. email", "")
        }
        println(email)
        //random number
        val ranNum : String = (Random().nextInt(900000) + 100000).toString()

        return try {
            val SELECT_ALL_FROM_TABLE = "INSERT INTO dbmaster_master.dbmaster_authenticate VALUES('$email', '$ranNum');"
            println(SELECT_ALL_FROM_TABLE)
            jdbcTemplate.execute(SELECT_ALL_FROM_TABLE)
            val map = HashMap<String, String>()
            map.put("address", email)
            map.put("authNum",ranNum)
            sendMail(map)
            ResponseDTO("S01", "메일함을 확인해 주세요.", "")

        } catch (e: SQLException) {
            System.err.print("SQLException : " + e.message)
            ResponseDTO("E01", e.toString(), "")
        }

    }

    @RequestMapping("/v1/mail/request")
    fun sendMail(
            @RequestBody response: HashMap<String, String>
    ): ResponseDTO {
        val address = response["address"] ?: return ResponseDTO("E01", "파라미터가 잘못 설정됬습니다.", "")
        val authNum = response["authNum"] ?: return ResponseDTO("E01", "파라미터가 잘못 설정됬습니다. authNum", "")
        print(address)
        print(authNum)
        try {
            val sender = JavaMailSenderImpl()
            sender.host = "smtp.gmail.com"
            sender.port = 587
            sender.username = "haningyainformation@gmail.com"
            sender.password = "Helxo116!"
            sender.javaMailProperties.setProperty("mail.smtp.auth", "true")
            sender.javaMailProperties.setProperty("mail.smtp.starttls.enable", "true")

            val senderMail = "uuzaza@naver.com"
            val mail = MailHandler(sender)
            mail.setFrom("haningyainformation@gmail.com", "dbMaster")
            mail.setTo(address)
            mail.setSubject("디비 마스터 회원가입 인증번호 메일")


            mail.setText(StringBuffer().append("<h1>회원가입 인증메일입니다.</h1>")
                    .append("<p>3분 내로 인증번호를 입력해 주세요</p>")
                    .append(authNum)
                    .toString()
            )
            mail.send()
            return ResponseDTO("S01", "메일이 성공적으로 발송되었습니다.", "")
        } catch (e: Exception) {
            return ResponseDTO("E01", e.toString(), "")
        }

    }
}