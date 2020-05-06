package com.example.dbmasterspringboot.controller.controltable

import com.example.dbmasterspringboot.data.dto.ResponseDTO
import com.example.dbmasterspringboot.data.dto.TableColumnDTO
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Statement
import java.util.ArrayList
import java.util.HashMap

@RestController
class GetTableListController {

    @RequestMapping("/v1/table/get-info")
    fun getTableInfo(
            @RequestBody response : HashMap<String, String>
    ): ResponseDTO {
        //파라미터 받고
        val name = response["name"]
        val tableName = response["tableName"]

        if (name == null) {
            return ResponseDTO("E01","파라미터가 잘못 설정됬습니다. tableName, name","")
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
            con = DriverManager.getConnection(url, "dbmaster", "root")
            println("Connected to DB ............")
            stmt = con.createStatement()

            val columns = con.metaData.getColumns(name,null,tableName,null)

            var resultArrayList = ArrayList<TableColumnDTO>()

            while (columns.next()){
                val columnName = columns.getString("COLUMN_NAME")
                val datatype = columns.getString("DATA_TYPE")
                val columnsize = columns.getString("COLUMN_SIZE")
                val decimaldigits = columns.getString("DECIMAL_DIGITS")
                val isNullable = columns.getString("IS_NULLABLE")
                val is_autoIncrment = columns.getString("IS_AUTOINCREMENT")
                //Printing results
                val tableColumnDTO = TableColumnDTO(columnName,datatype,columnsize,decimaldigits,isNullable,is_autoIncrment)
                resultArrayList.add(tableColumnDTO)

                println("$columnName---$datatype---$columnsize---$decimaldigits---$isNullable---$is_autoIncrment")
            }
            stmt.close()
            con.close()
            println("Disconnected From DB ..........")
            return ResponseDTO("S01","",resultArrayList)

        } catch (e: SQLException) {
            System.err.print("SQLException : " + e.message)
            return ResponseDTO("E01",e.toString(),"")
        }
    }

    /* 사용자 데이터베이스 전체 테이블 목록 가져옴. */
    @RequestMapping("/v1/table/all-tables")
    fun selectTable(
            @RequestBody response: HashMap<String, String>
    ): ResponseDTO {
        //파라미터 받고
        val name = response["name"]
        val pw = response["pw"]

        if (name == null) {
            return ResponseDTO("E01","파라미터가 잘못 설정됬습니다. tableName, name","")
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
            con = DriverManager.getConnection(url, "dbmaster", "root")
            println("Connected to DB ............")
            stmt = con.createStatement()

            val resultSet = con.metaData.getTables(name,null,"%",null);
            val resultArrayList = ArrayList<String>()
            while (resultSet.next()){
                System.out.println(resultSet.getString(3))
                resultArrayList.add(resultSet.getString(3))
            }
            stmt.close()
            con.close()
            println("Disconnected From DB ..........")
            return ResponseDTO("S01","",resultArrayList)

        } catch (e: SQLException) {
            System.err.print("SQLException : " + e.message)
            return ResponseDTO("E01",e.toString(),"")
        }
    }
}