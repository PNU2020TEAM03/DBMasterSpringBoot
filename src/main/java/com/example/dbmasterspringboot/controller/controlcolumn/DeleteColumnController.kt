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


        if (tableName == null || name == null || primary_key_name == null || primary_key_value == null) {
            return ResponseDTO("E01","파라미터가 잘못 설정됬습니다.","")
        }

        if (tableName == null || tableName == "") {
            return ResponseDTO("E01","tableName 값이 입력되지 않았습니다.","")
        }
        if (name == null || name == "") {
            return ResponseDTO("E02","name 값이 입력되지 않았습니다.","")
        }
        if (primary_key_name == null || primary_key_name == "") {
            return ResponseDTO("E03","primary_key_name 값이 입력되지 않았습니다.","")
        }
        if (primary_key_value == null || primary_key_value == "") {
            return ResponseDTO("E04","primary_key_value 값이 입력되지 않았습니다.","")
        }
        return try {
            val DELET_COLUMN_QUERY = "DELETE FROM $name.$tableName WHERE $primary_key_name = $primary_key_value"
            println(DELET_COLUMN_QUERY)
            jdbcTemplate.execute(DELET_COLUMN_QUERY)

            ResponseDTO("S01","삭제되었습니다.","")

        } catch (e: Exception) {
            if(e.message!!.contains("doesn't exist")){
                return ResponseDTO("E05","테이블 또는 데이터베이스가 존재하지 않습니다.","");
            }
            if(e.message!!.contains("Unknown column")){
                return ResponseDTO("E06","칼럼이 존재하지 않습니다.","");
            }
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