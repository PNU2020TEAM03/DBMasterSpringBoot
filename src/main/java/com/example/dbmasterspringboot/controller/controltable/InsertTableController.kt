package com.example.dbmasterspringboot.controller.controltable

import com.example.dbmasterspringboot.data.dto.ResponseDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.sql.SQLException
import java.util.HashMap

@RestController
class InsertTableController(
        @Autowired val jdbcTemplate: JdbcTemplate
) {

    @RequestMapping("/v1/table/insert")
    fun insertTable(
            @RequestBody response: HashMap<String, String>
    ): ResponseDTO {
        //파라미터 받고
        val tableName = response["tableName"]
        val name = response["name"]
        val insert = response["insert"]
        if (tableName == null || name == null || insert == null) {
            return ResponseDTO("E01","파라미터가 잘못 설정됬습니다. tableName, name","")
        }
        println(tableName)
        println(name)
        println(insert)

        return try {
            val SELECT_ALL_FROM_TABLE = "INSERT INTO $name.$tableName VALUES($insert);"
            println(SELECT_ALL_FROM_TABLE)
            jdbcTemplate.execute(SELECT_ALL_FROM_TABLE)
            ResponseDTO("S01","insert 성공했습니다.","")

        } catch (e: SQLException) {
            System.err.print("SQLException : " + e.message)
            ResponseDTO("E01",e.toString(),"")
        }

    }
}