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
class DropTableController(
        @Autowired val jdbcTemplate: JdbcTemplate
) {

    /* Table 만드는 함수 */
    @RequestMapping("/v1/table/drop")
    fun createTable(
            @RequestBody response: HashMap<String, String>
    ): ResponseDTO {
        var resultStr = "failed"
        val tableName = response["tableName"]
        val name = response["name"]

        if (tableName == null || tableName == "") {
            return ResponseDTO("E01","tableName 값이 입력되지 않았습니다.","")
        }
        if (name == null || name == "") {
            return ResponseDTO("E02","name 값이 입력되지 않았습니다.","")
        }
        //fielddInfo = sno int(11) NOT NULL, name char(10) DEFAULT NULL, PRIMARY KEY (sno)

        return try {
            val DROP_TABLE_QUERY = "DROP TABLE $name.$tableName;"
            println(DROP_TABLE_QUERY)
            jdbcTemplate.execute(DROP_TABLE_QUERY)
            ResponseDTO("S01","테이블이 삭제되었습니다.","")

        } catch (e: Exception) {
            if(e.message!!.contains("Unknown table")){
                return ResponseDTO("E03","테이블 또는 데이터베이스가 존재하지 않습니다.","");
            }
            resultStr = e.cause.toString()
            ResponseDTO("E04",resultStr,"")
        }
    }


}