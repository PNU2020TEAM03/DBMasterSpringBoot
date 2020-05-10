package com.example.dbmasterspringboot.controller.controlcolumn

import com.example.dbmasterspringboot.data.dto.ResponseDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.util.HashMap


@RestController
class DeleteColumnController(
        @Autowired val jdbcTemplate: JdbcTemplate
) {

    @RequestMapping("/v1/column/delete")
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