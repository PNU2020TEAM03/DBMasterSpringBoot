package com.example.dbmasterspringboot.controller.signup

import com.example.dbmasterspringboot.data.dto.ResponseDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.queryForObject
import org.springframework.web.bind.annotation.*
import java.sql.SQLIntegrityConstraintViolationException
import java.time.LocalDate
import javax.servlet.http.HttpServletRequest

@RestController
class SignUpController(
        @Autowired val jdbcTemplate: JdbcTemplate
) {

    private var duplicationResult: String? = null

    @PostMapping("/test")
    fun test(
            @RequestBody name: HashMap<String, String>
    ): ResponseDTO {
        return ResponseDTO("success", "success", "asdf")
    }

    /* TODO 회원가입 시 중복확인에 대한 함수 중복이면 duplicate 리턴, 없으면 null 리턴 */
    @PostMapping("/v1/sign-up/check-name")
    fun checkDBName(
            @RequestBody name: HashMap<String, String>
    ): ResponseDTO {
        val dbName: String? = name["name"]
        val DB_NAME_CHECK_QUERY = "SELECT available FROM dbmaster_users WHERE dbname = \'$dbName\'"

        println(dbName)
        if(dbName == null) {
            /* 파라미터 입력이 잘못 되었거나 입력하지 않았습니다. */
            return ResponseDTO("E01","","")
        }
        return try{
            duplicationResult = jdbcTemplate.queryForObject(DB_NAME_CHECK_QUERY)
            ResponseDTO("E01",duplicationResult.toString(),"")
        } catch (e: Exception) {
            duplicationResult = "available"
            print(e.toString())

            ResponseDTO("S01",duplicationResult.toString(),"")
        }
    }

    @PostMapping("/v1/sign-up/request")
    fun requestSignUp(
            @RequestBody user: Map<String, String>
    ): ResponseDTO {
        var resultStr = "failed"
        val currentTime = LocalDate.now()

        val dbName = user["name"]
        val password = user["pw"]

        println("$dbName, $password")


        if(dbName == null || dbName == ""){
            /* name 파라미터가 입력되지 않았거나 값이 없습니다. */
            return ResponseDTO("E02","아이디가 입력되지 않았습니다.","")
        }
        if(password == null || password == ""){
            /* pw 파라미터가 입력되지 않았거나 값이 없습니다. */
            return ResponseDTO("E03","비밀번호가 입력되지 않았습니다.","")
        }
        try {
            /* master DB에 유저 등록 및 DB 생성 */
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

            return ResponseDTO("S01","회원가입에 성공했습니다.","")

        } catch (e : Exception){
            print(e.message)
            if(e.message!!.contains("SQLIntegrityConstraintViolationException")){
                return ResponseDTO("E01","$dbName 은 아이디로 사용하실 수 없습니다.","");
            }
            if(e.message!!.contains("SQLSyntaxErrorException")){
                return ResponseDTO("E04","SQL 문법 오류입니다. 특수문자를 사용하지 마세요.","");
            }
            if(e.message!!.contains("SQLException")){
                return ResponseDTO("E05","확인되지 않은 SQL 오류입니다.","");
            }

            return ResponseDTO("E01","${e.message}","");

        }
    }
}