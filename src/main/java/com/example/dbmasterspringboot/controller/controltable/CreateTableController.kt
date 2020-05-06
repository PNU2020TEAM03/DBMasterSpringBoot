package com.example.dbmasterspringboot.controller.controltable

import com.example.dbmasterspringboot.data.dto.ResponseDTO
import com.example.dbmasterspringboot.data.dto.TableColumnDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Statement
import java.time.LocalDate
import java.util.*


@RestController
class CreateTableController(
        @Autowired val jdbcTemplate: JdbcTemplate
) {

    /* Table 만드는 함수 */
    @RequestMapping("/v1/table/create")
    fun createTable(
            @RequestBody response: HashMap<String, String>
    ): ResponseDTO {
        var resultStr = "failed"
        val currentTime = LocalDate.now()
        val tableName = response["tableName"]
        val fieldInfo = response["fieldInfo"]
        val name = response["name"]

        if (tableName == null || fieldInfo == null || name == null) {
            return ResponseDTO("E01","파라미터가 잘못 설정됬습니다.","")
        }
        //fielddInfo = sno int(11) NOT NULL, name char(10) DEFAULT NULL, PRIMARY KEY (sno)

        return try {
            val CREATE_TABLE_QUERY = "CREATE TABLE $name.$tableName ($fieldInfo) default character set utf8 collate utf8_general_ci;"
            println(CREATE_TABLE_QUERY)
            jdbcTemplate.execute(CREATE_TABLE_QUERY)
            ResponseDTO("S01","","")

        } catch (e: Exception) {
            resultStr = e.cause.toString()
            ResponseDTO("E01",resultStr,"")
        }
    }

//    {
//        "tableName" : "test1",
//        "name" : "uuzaza",
//        "insert" : "8, '테스트'"
//    }
}