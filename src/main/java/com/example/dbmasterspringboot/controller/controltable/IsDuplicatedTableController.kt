package com.example.dbmasterspringboot.controller.controltable

import com.example.dbmasterspringboot.data.dto.ResponseDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Statement
import java.util.*


@RestController
class IsDuplicatedTableController(
        @Autowired val jdbcTemplate: JdbcTemplate
) {

    @RequestMapping("/v1/table/duplicate")
    fun insertTable(
            @RequestBody response: HashMap<String, String>
    ): ResponseDTO {
        //파라미터 받고
        val name = response["name"]
        val tableName = response["tableName"]

        if (name == null || tableName == null) {
            return ResponseDTO("E01", "파라미터가 잘못 설정됬습니다. tableName, name", "")
        }
        //디비 커넥션 준비
        var url: String? = "jdbc:mysql://54.180.95.198:5536/dbmaster_master?serverTimezone=UTC&useSSL=true"
        var con: Connection? = null
        var stmt: Statement? = null
        //디비안의 모든 테이블 디비 이름=name

        try {
            Class.forName("com.mysql.cj.jdbc.Driver")
        } catch (e: ClassNotFoundException) {
            System.err.print("ClassNotFoundException : " + e.message)
        }
        try {
            con = DriverManager.getConnection(url, "dbmaster", "dlfdlf11!!")
            println("Connected to DB ............")
            val dbm = con!!.metaData

            val tables = dbm.getTables(null, null, tableName, null)
            if (tables.next()) {
                // Table exists
                con.close()
                println("Disconnected From DB ..........")
                return ResponseDTO("E01", "같은 이름의 테이블이 존재합니다.", "")

            } else {
                // Table does not exist
                con.close()
                println("Disconnected From DB ..........")
            }
            return ResponseDTO("S01", "사용하실 수 있는 이름입니다.", "")

        } catch (e: SQLException) {
            System.err.print("SQLException : " + e.message)
            return ResponseDTO("E01", e.toString(), "")
        }
    }
}