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
        if (tableName == null || tableName == "") {
            return ResponseDTO("E01","테이블을 입력하지 않았습니다.","")
        }
        if (name == null || name == "") {
            return ResponseDTO("E02","데이터베이스 이름을 입력하지 않았습니다.","")
        }
        if (insert == null || insert == "") {
            return ResponseDTO("E03","입력할 데이터가 비어있습니다.","")
        }

        return try {
            val SELECT_ALL_FROM_TABLE = "INSERT INTO $name.$tableName VALUES($insert);"
            println(SELECT_ALL_FROM_TABLE)
            jdbcTemplate.execute(SELECT_ALL_FROM_TABLE)
            ResponseDTO("S01","insert 성공했습니다.","")

        } catch (e: Exception) {

            if(e.message!!.contains("SQLSyntaxErrorException")){
                return ResponseDTO("E05","입력된 데이터 타입이 칼럼 타입과 다릅니다.","");
            }
            if(e.message!!.contains("SQLIntegrityConstraintViolationException")){
                return ResponseDTO("E06","이미 입력된 데이터 입니다.","");
            }
            return ResponseDTO("E04","SQL 문법 오류입니다.",e.toString())
        }
    }
}