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


        if (name == null || name == "") {
            return ResponseDTO("E01","데이터 베이스 이름을 입력하지 않았습니다.","")
        }
        if (tableName == null || tableName == "") {
            return ResponseDTO("E02","테이블 이름을 입력하지 않았습니다.","")
        }
        if (primary_key_name == null || primary_key_name == "") {
            return ResponseDTO("E03","Primary Key를 입력하지 않았습니다.","")
        }
        if (primary_key_value == null || primary_key_value == "") {
            return ResponseDTO("E04","Primary Key 값이 입력되지 않았습니다.","")
        }
        if (update_column_name == null || update_column_name == "") {
            return ResponseDTO("E05","업데이트할 column 이름이 입력되지 않았습니다.","")
        }
        if (update_value == null || update_value == "") {
            return ResponseDTO("E06","업데이트할 column의 값이 입력되지 않았습니다.","")
        }

        return try {
            val UPDATE_TABLE_QUERY = "UPDATE $name.$tableName SET $update_column_name = $update_value WHERE $primary_key_name = $primary_key_value;"
            println(UPDATE_TABLE_QUERY)
            jdbcTemplate.execute(UPDATE_TABLE_QUERY)
            ResponseDTO("S01","","")

        } catch (e: Exception) {
            if(e.message!!.contains("doesn't exist")){
                return ResponseDTO("E07","테이블이 존재하지 않습니다.","");
            }
            if(e.message!!.contains("SQLSyntaxErrorException")){
                return ResponseDTO("E08","SQL 문법 오류입니다.",e.toString());
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