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
import javax.xml.ws.Response

@RestController
class GetTableListController {

    //table 정보 받기
    @RequestMapping("/v1/table/get-info")
    fun getTableInfo(
            @RequestBody response : HashMap<String, String>
    ): ResponseDTO {
        //파라미터 받고
        val name = response["name"]
        val tableName = response["tableName"]

        if (name == null || tableName == null) {
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
            con = DriverManager.getConnection(url, "dbmaster", "dlfdlf11!!")
            println("Connected to DB ............")
            stmt = con.createStatement()

            val columns = con.metaData.getColumns(name,null,tableName,null)
            val resultSetForPK = con.metaData.getPrimaryKeys(name,"",tableName)
            var primayKey = ""


            var resultArrayList = ArrayList<TableColumnDTO>()

            while (resultSetForPK.next()) {
                println(resultSetForPK.getString("COLUMN_NAME") + ":" + resultSetForPK.getString("KEY_SEQ"));
                primayKey = resultSetForPK.getString("COLUMN_NAME")
                println("primary key is $primayKey")
            }

            while (columns.next()){
                val columnName = columns.getString("COLUMN_NAME")
                var datatype = columns.getString("DATA_TYPE")
                val columnsize = columns.getString("COLUMN_SIZE")
                val decimaldigits = columns.getString("DECIMAL_DIGITS")

                val isNullable = columns.getString("IS_NULLABLE")
                val is_autoIncrment = columns.getString("IS_AUTOINCREMENT")



                when (datatype) {
                    "1" -> datatype = "VARCHAR"
                    "4" -> datatype = "INTEGER"
                    "12" -> datatype = "VARCHAR"
                    "3" -> datatype = "DECIMAL"
                    "93" -> datatype = "DATETIME"
                    "-4" -> datatype = "BLOB"
                    "-2" -> datatype = "BINARY"
                    "-3" -> datatype = "BLOB"
                    "255" -> datatype = "TEXT"
                    "12" -> datatype = "VARCHAR"

                    else -> { // Note the block
                        print("UNKOWN")
                    }
                }



                if(columnName.equals(primayKey)){
                    val tableColumnDTO = TableColumnDTO("Y",columnName,datatype,columnsize)
                    resultArrayList.add(tableColumnDTO)
                    println(tableColumnDTO.ispk)

                }else{
                    val tableColumnDTO = TableColumnDTO("N",columnName,datatype,columnsize)
                    resultArrayList.add(tableColumnDTO)
                    println(tableColumnDTO.ispk)
                }

                //Printing results
            }
            stmt.close()
            con.close()
            println("Disconnected From DB ..........")
            println(resultArrayList)
            if(resultArrayList.size == 0){
                return ResponseDTO("E01","정보가 없습니다.",resultArrayList)
            }
            print(ResponseDTO("S01","",resultArrayList))
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
            con = DriverManager.getConnection(url, "dbmaster", "dlfdlf11!!")
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