package com.example.dbmasterspringboot.controller.controltable


import com.example.dbmasterspringboot.data.dto.ResponseDTO
import com.example.dbmasterspringboot.data.dto.TableColumnDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Statement
import java.time.LocalDate
import java.util.*


@RestController
class SetForeignKeyController(
        @Autowired val jdbcTemplate: JdbcTemplate
) {

    /* Table 만드는 함수 */
    @RequestMapping("/v1/table/set-foreign")
    fun setForeignKey(
            @RequestBody response: HashMap<String, String>
    ): ResponseDTO {

        val tableName = response["tableName"]
        val baseColumn = response["baseColumn"]
        val name = response["name"]

        val targetTable = response["targetTable"]
        val targetColumn = response["targetColumn"]

        if (tableName == null || baseColumn == null || name == null || targetTable == null || targetColumn == null) {
            return ResponseDTO("E01","파라미터가 잘못 설정됬습니다.","")
        }

        return try {
            val SET_FOREIGN_KEY_QUREY = "ALTER TABLE $name.$tableName ADD FOREIGN KEY ($baseColumn) REFERENCES $name.$targetTable($targetColumn)"
            println(SET_FOREIGN_KEY_QUREY)
            jdbcTemplate.execute(SET_FOREIGN_KEY_QUREY)
            ResponseDTO("S01","","")

        } catch (e: Exception) {
            ResponseDTO("E01",e.cause.toString(),"")
        }
    }


}