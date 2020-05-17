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
        val currentTime = LocalDate.now()
        val tableName = response["tableName"]
        val name = response["name"]

        if (tableName == null || name == null) {
            return ResponseDTO("E01","파라미터가 잘못 설정됬습니다.","")
        }
        //fielddInfo = sno int(11) NOT NULL, name char(10) DEFAULT NULL, PRIMARY KEY (sno)

        return try {
            val DROP_TABLE_QUERY = "DROP TABLE $name.$tableName;"
            println(DROP_TABLE_QUERY)
            jdbcTemplate.execute(DROP_TABLE_QUERY)
            ResponseDTO("S01","","")

        } catch (e: Exception) {
            resultStr = e.cause.toString()
            ResponseDTO("E01",resultStr,"")
        }
    }


}