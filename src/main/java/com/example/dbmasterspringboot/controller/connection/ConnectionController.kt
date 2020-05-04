package com.example.dbmasterspringboot.controller.connection

import com.example.dbmasterspringboot.data.dto.ConnectionDTO
import com.example.dbmasterspringboot.data.dto.ResponseDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.queryForObject
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ConnectionController(
        @Autowired val jdbcTemplate: JdbcTemplate
) {

    /* Database Connection 가능 한 지 Check */
    @PostMapping("/v1/connection/check")
    fun checkConnectionAvailable(
            @RequestBody user: HashMap<String, String>
    ): ConnectionDTO {
        val resultId: String
        val resultConnection: String

        val name = user["name"]
        val pw = user["pw"]

        val DB_CONNECTION_ID_CHECK_QUERY =
                "SELECT \'available\'\n " +
                        "FROM dbmaster_users\n " +
                        "WHERE dbname = \'$name\';"

        val DB_CONNECTION_ID_PW_CHECK_QUERY =
                "SELECT connection\n" +
                        "FROM dbmaster_users\n" +
                        "WHERE dbname = \'$name\' AND pw = \'$pw\';"

        resultId = try {
            jdbcTemplate.queryForObject(DB_CONNECTION_ID_CHECK_QUERY)
        } catch (e: Exception) {
            "unavailable"
        }

        resultConnection = try {
            jdbcTemplate.queryForObject(DB_CONNECTION_ID_PW_CHECK_QUERY)
        }   catch (e: Exception) {
            "unavailable"
        }

        return ConnectionDTO(resultId, resultConnection)
    }

    @GetMapping("/v1/connection/update")
    fun updateUserInfo(
            @RequestBody updateInfo: HashMap<String, String>
    ) {
        val name = ""
    }

}