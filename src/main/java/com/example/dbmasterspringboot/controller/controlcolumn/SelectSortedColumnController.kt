package com.example.dbmasterspringboot.controller.controlcolumn

import com.example.dbmasterspringboot.data.dto.ColumnDTO
import com.example.dbmasterspringboot.data.dto.ResponseDTO
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.sql.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


@RestController
class SelectSortedColumnController(
        @Autowired val jdbcTemplate: JdbcTemplate
) {

    @RequestMapping("/v1/table/sort")
    fun selectAllData(
            @RequestBody response: HashMap<String, String>
    ): ResponseDTO {
        val name = response["name"]
        val tableName = response["tableName"]
        val sortColumn = response["sortColumn"]
        val direction = response["direction"]

        if (name == null || tableName == null || sortColumn == null || direction == null) {
            return ResponseDTO("E01", "파라미터가 잘못 설정됬습니다. tableName, name, sortColumn, direction ", "")
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

            val SELECT_ALL_QUERY = "SELECT * FROM $name.$tableName ORDER BY $sortColumn $direction;"
            var pstmt = con.prepareStatement(SELECT_ALL_QUERY)
            var jsonArray = JSONArray()
            try {
                con.prepareStatement(
                        SELECT_ALL_QUERY, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).use { selectStmt ->
                    selectStmt.executeQuery().use { rs ->
                        if (!rs.isBeforeFirst()) {
                            println("no rows found")
                        } else {
                            while (rs.next()) {
                                var obj = JSONObject()
                                for (i in 1 until rs.getMetaData().getColumnCount() + 1) {
                                    val name = rs.metaData.getColumnName(i)
                                    val value = rs.getObject(i)
                                    obj.put(name, value)
                                    print(obj)
                                } // 한컬럼 다 넣어짐
                                jsonArray.add(obj)
                            }//end of while
                        }
                    }
                }
            } catch (e: SQLException) {
                return ResponseDTO("E02", e.toString(), null)
            }
            pstmt.close()
            con.close()
            println("Disconnected From DB ..........")


            return ResponseDTO("S01", "", jsonArray)

        } catch (e: SQLException) {
            System.err.print("SQLException : " + e.message)
            return ResponseDTO("E01", e.toString(), "")
        }
    }


}