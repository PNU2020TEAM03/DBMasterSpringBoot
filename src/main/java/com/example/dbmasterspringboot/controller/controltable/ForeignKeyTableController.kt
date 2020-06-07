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
import java.util.*

@RestController
class ForeignKeyTableController {

    //table 정보 받기
    @RequestMapping("/v1/table/get-foreign")
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

            val resultSetForFK = con.metaData.getExportedKeys(name,"",tableName)

            var fkTableName = ""
            var fkColumnName = ""

            while (resultSetForFK.next()) {
                fkTableName = resultSetForFK.getString("FKTABLE_NAME")
                fkColumnName = resultSetForFK.getString("FKCOLUMN_NAME")
                val fkSequence: Int = resultSetForFK.getInt("KEY_SEQ")
                println("getExportedKeys(): fkTableName=$fkTableName")
                println("getExportedKeys(): fkColumnName=$fkColumnName")


            }


            stmt.close()
            con.close()
            println("Disconnected From DB ..........")
            if(fkTableName.length == 0){
                return ResponseDTO("E01","외래키가 없습니다.","")
            }
            print(ResponseDTO("S01","$fkTableName 의 $fkColumnName 칼럼과 외래키 관계임니다.",""))
            return ResponseDTO("S01","$fkTableName 의 $fkColumnName 칼럼과 외래키 관계임니다.","")


        } catch (e: SQLException) {
            System.err.print("SQLException : " + e.message)
            return ResponseDTO("E01",e.toString(),"")
        }
    }


}