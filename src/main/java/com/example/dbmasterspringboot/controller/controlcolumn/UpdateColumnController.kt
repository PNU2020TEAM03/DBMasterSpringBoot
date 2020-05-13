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
class UpdateColumnController(
        @Autowired val jdbcTemplate: JdbcTemplate
) {

    @RequestMapping("/v1/column/update")
    fun createTable(
            @RequestBody response: HashMap<String, String>
    ): ResponseDTO {
        var resultStr = "failed"
        val currentTime = LocalDate.now()
        val primary_key_name = response["primary_key_name"]
        val primary_key_value = response["primary_key_value"]
        val update_column_name = response["update_column_name"]
        val update_value = response["update_value"]

        val name = response["name"]
        val tableName = response["tableName"]


        if (tableName == null || name == null || primary_key_name == null || primary_key_value == null) {
            return ResponseDTO("E01","파라미터가 잘못 설정됬습니다.","")
        }

        return try {
            val UPDATE_TABLE_QUERY = "UPDATE $name.$tableName SET $update_column_name = $update_value WHERE $primary_key_name = $primary_key_value;"
            println(UPDATE_TABLE_QUERY)
            jdbcTemplate.execute(UPDATE_TABLE_QUERY)
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