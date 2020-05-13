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

        val primary_key_name = response["primary_key_name"]
        val primary_key_value = response["primary_key_value"]

        val name = response["name"]
        val tableName = response["tableName"]


        if (tableName == null || name == null) {
            return ResponseDTO("E01","파라미터가 잘못 설정됬습니다.","")
        }
        //fielddInfo = sno int(11) NOT NULL, name char(10) DEFAULT NULL, PRIMARY KEY (sno)

        return try {
            val DELET_COLUMN_QUERY = "DELETE FROM $name.$tableName WHERE $primary_key_name = $primary_key_value"
            println(DELET_COLUMN_QUERY)
            jdbcTemplate.execute(DELET_COLUMN_QUERY)
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