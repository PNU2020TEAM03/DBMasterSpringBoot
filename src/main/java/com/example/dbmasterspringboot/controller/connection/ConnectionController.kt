package com.example.dbmasterspringboot.controller.connection

import com.example.dbmasterspringboot.data.dto.ResponseDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.queryForObject
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
class ConnectionController(
        @Autowired val jdbcTemplate: JdbcTemplate,
        @Autowired val httpServletRequest: HttpServletRequest
) {

    /* Database Connection 가능 한 지 Check */
    @RequestMapping("/v1/connection/check")
    fun checkConnectionAvailable(): String {
        val name = httpServletRequest.getParameter("name")
        val pw = httpServletRequest.getParameter("pw")

        val DB_CONNECTION_CHECK_QUERY = "SELECT connection\n " +
                "FROM dbmaster_users\n " +
                "WHERE dbname = \'$name\' AND pw = \'$pw\';"

        return try{
            jdbcTemplate.queryForObject(DB_CONNECTION_CHECK_QUERY)
        } catch (e: Exception) {
            e.printStackTrace()
            "unavailable"
        }
    }

}