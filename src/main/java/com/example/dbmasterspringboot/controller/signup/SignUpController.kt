package com.example.dbmasterspringboot.controller.signup

import com.example.dbmasterspringboot.data.dto.ResponseDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.queryForObject
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import javax.servlet.http.HttpServletRequest

@RestController
class SignUpController(
        @Autowired val jdbcTemplate: JdbcTemplate,
        @Autowired val httpServletRequest: HttpServletRequest
) {

    /* TODO 회원가입 시 중복확인에 대한 함수 중복이면 duplicate 리턴, 없으면 null 리턴 */
    @RequestMapping("/sign-up/check")
    fun checkDBName(): ResponseDTO {
        var duplicationResult: String
        val dbName = httpServletRequest.getParameter("name")
        val DB_NAME_CHECK_QUERY = "SELECT check_duplication FROM dbmaster_users WHERE dbname = \'$dbName\'"

        return try{
            duplicationResult = jdbcTemplate.queryForObject(DB_NAME_CHECK_QUERY)
            ResponseDTO(duplicationResult)
        } catch (e: Exception) {
            duplicationResult = "available"
            ResponseDTO(duplicationResult)
        }
    }

    @RequestMapping("/sign-up/request")
    fun requestSignUp(): ResponseDTO {
        val currentTime = LocalDate.now()
        val dbName = httpServletRequest.getParameter("name")
        val password = httpServletRequest.getParameter("pw")
        val DB_CREATE_QUERY = "CREATE SCHEMA $dbName;"
        val USER_CREATE_QUERY = "CREATE USER \'$dbName\'@\'%\' IDENTIFIED BY \'$password\';"
        val USER_INSERT_TO_MASTER_QUERY =
                "INSERT INTO dbmaster_users (dbname, pw, latest_visit_date)\n" +
                "VALUES (\'$dbName\', \'$password\', \'$currentTime\');"

        return try {
            jdbcTemplate.execute(DB_CREATE_QUERY)
            jdbcTemplate.execute(USER_CREATE_QUERY)
            jdbcTemplate.execute(USER_INSERT_TO_MASTER_QUERY)

            ResponseDTO("success")
        } catch (e: Exception) {
            ResponseDTO("failed")
        }
    }
}