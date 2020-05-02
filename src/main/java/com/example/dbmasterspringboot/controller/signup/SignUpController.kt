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

    private var duplicationResult: String? = null

    /* TODO 회원가입 시 중복확인에 대한 함수 중복이면 duplicate 리턴, 없으면 null 리턴 */
    @RequestMapping("/sign-up/check")
    fun checkDBName(): ResponseDTO {
        val dbName = httpServletRequest.getParameter("name")
        val DB_NAME_CHECK_QUERY = "SELECT check_duplication FROM dbmaster_users WHERE dbname = \'$dbName\'"

        System.out.println(dbName);
        if(dbName == null) {
            return ResponseDTO("E01 : 파라미터 입력이 잘못 되었거나 입력하지 않았습니다.")
        }
        return try{
            duplicationResult = jdbcTemplate.queryForObject(DB_NAME_CHECK_QUERY)
            ResponseDTO(duplicationResult.toString())
        } catch (e: Exception) {
            duplicationResult = "available"
            ResponseDTO(duplicationResult.toString())
        }
    }

    @RequestMapping("/sign-up/request")
    fun requestSignUp(): ResponseDTO {
        var resultStr = "failed"
        val currentTime = LocalDate.now()
        val dbName = httpServletRequest.getParameter("name")
        val password = httpServletRequest.getParameter("pw")
        System.out.print(dbName)
        System.out.print(password)
        if(dbName == null && password == null){
            return ResponseDTO("name, pw 파라미터가 입력되지 않았거나 값이 없습니다.")
        }
        if(dbName == null){
            return ResponseDTO("name 파라미터가 입력되지 않았거나 값이 없습니다.")
        }
        if(password == null){
            return ResponseDTO("pw 파라미터가 입력되지 않았거나 값이 없습니다.")
        }
        try {
            val USER_INSERT_TO_MASTER_QUERY =
                    "INSERT INTO dbmaster_users (dbname, pw, latest_visit_date)\n" +
                            "VALUES (\'$dbName\', \'$password\', \'$currentTime\');"
            val USER_CREATE_QUERY = "CREATE USER $dbName@\'%\' IDENTIFIED BY \'$password\';"
            val DB_CREATE_QUERY = "CREATE SCHEMA $dbName;"
            val USER_GRANT_QUERY = "GRANT ALL PRIVILEGES ON $dbName.* TO $dbName@\'%\';"
            val FLUSH_GRANT_QUERY = "FLUSH PRIVILEGES"

            jdbcTemplate.execute(USER_INSERT_TO_MASTER_QUERY)
            jdbcTemplate.execute(USER_CREATE_QUERY)
            jdbcTemplate.execute(DB_CREATE_QUERY)
            jdbcTemplate.execute(USER_GRANT_QUERY)
            jdbcTemplate.execute(FLUSH_GRANT_QUERY)

            return ResponseDTO("S01")
        } catch (e: Exception) {
            resultStr = e.cause.toString()
            return ResponseDTO(resultStr)
        }
    }
}