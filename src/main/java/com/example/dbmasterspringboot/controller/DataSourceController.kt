package com.example.dbmasterspringboot.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.web.bind.annotation.*
import java.lang.Exception
import javax.servlet.http.HttpServletRequest

@RestController
class DataSourceController(
        @Autowired val jdbcTemplate: JdbcTemplate
) {

    private val BASE_URL = "jdbc:mysql://54.180.95.198:5536/"
    private val BASE_QUERY = "?serverTimezone=UTC&useSSL=true"

    @RequestMapping("/create-database")
    fun testCreateDB(httpServletRequest: HttpServletRequest): String {
        val name =  httpServletRequest.getParameter("name")
        val fullURL = BASE_URL + name + BASE_QUERY
        val id = httpServletRequest.getParameter("id")
        val pw = httpServletRequest.getParameter("pw")

        val CREATE_DB_QUERY = "CREATE SCHEMA $name;"
        val CREATE_USER_QUERY = "CREATE USER \'$id\'@\'%\' IDENTIFIED BY \'$pw\';"

        return try{
            jdbcTemplate.execute(CREATE_DB_QUERY)
            jdbcTemplate.execute(CREATE_USER_QUERY)

            "S01"
        }catch (e: Exception){
            e.printStackTrace()
            "이미 같은 이름의 데이터베이스가 생성되어 있어요!"
        }
    }


}