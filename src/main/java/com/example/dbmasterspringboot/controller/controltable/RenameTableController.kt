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

        if (tableName == null || tableName == "") {
            return ResponseDTO("E01","tableName 값이 입력되지 않았습니다.","")
        }
        if (name == null || name == "") {
            return ResponseDTO("E02","name 값이 입력되지 않았습니다.","")
        }
        if (newName == null || newName == "") {
            return ResponseDTO("E03","newName 값이 입력되지 않았습니다.","")
        }
        return try {
            val RENAME_TABLE_QUERY = "ALTER TABLE $name.$tableName RENAME $name.$newName;"
            println(RENAME_TABLE_QUERY)
            jdbcTemplate.execute(RENAME_TABLE_QUERY)
            ResponseDTO("S01","","")

        } catch (e: Exception) {
            if(e.message!!.contains("doesn't exist")){
                return ResponseDTO("E04","테이블 또는 데이터베이스가 존재하지 않습니다.","");
            }
            resultStr = e.cause.toString()
            ResponseDTO("E05",resultStr,"")
        }
    }

//    {
//        "tableName" : "test1",
//        "name" : "uuzaza",
//        "insert" : "8, '테스트'"
//    }
}