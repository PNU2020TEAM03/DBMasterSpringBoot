package com.example.dbmasterspringboot.controller.controltable

import com.example.dbmasterspringboot.data.dto.ResponseDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.util.HashMap

@RestController
class RenameTableController(
        @Autowired val jdbcTemplate: JdbcTemplate
) {

    /* Table 만드는 함수 */
    @RequestMapping("/v1/table/rename")
    fun createTable(
            @RequestBody response: HashMap<String, String>
    ): ResponseDTO {
        var resultStr = "failed"
        val tableName = response["tableName"]
        val name = response["name"]
        val newName = response["newName"]

        if (tableName == null  || name == null) {
            return ResponseDTO("E01","파라미터가 잘못 설정됬습니다.","")
        }
        return try {
            val RENAME_TABLE_QUERY = "ALTER TABLE $name.$tableName RENAME $name.$newName;"
            println(RENAME_TABLE_QUERY)
            jdbcTemplate.execute(RENAME_TABLE_QUERY)
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