package com.example.dbmasterspringboot.controller.exportcontroller

import com.example.dbmasterspringboot.controller.controlmail.MailHandler
import com.example.dbmasterspringboot.data.dto.ResponseDTO
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.File
import java.io.FileWriter
import java.sql.*
import java.util.*
import java.util.regex.Pattern
import javax.activation.FileDataSource
import javax.mail.internet.MimeMessage


@RestController
class ExportTableController(
        @Autowired val jdbcTemplate: JdbcTemplate
) {
    private val resultSetArray = ArrayList<String>()

    @RequestMapping("/v1/table/export")
    fun createTable(
            @RequestBody response: HashMap<String, String>
    ): ResponseDTO {

        val name = response["name"]
        val tableName = response["tableName"]
        val email = response["email"]

        if (name == null || tableName == null || email == null) {
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

            val SELECT_QUERY = "SELECT * FROM $name.$tableName"
            try {
                val stmt = con.createStatement()
                val rs = stmt.executeQuery(SELECT_QUERY)
                val numCols = rs.metaData.columnCount
                while (rs.next()) {
                    val sb = StringBuilder()
                    for (i in 1..numCols) {
                        sb.append(String.format(rs.getString(i).toString()) + " ")
                    }
                    resultSetArray.add(sb.toString())
                }
            } catch (e: SQLException) {
                return ResponseDTO("E01",e.message,"");
            }

            val outputFile = printToCsv(resultSetArray)

            con.close()
            println("Disconnected From DB ..........")

            if(checkEmail(email) == false){
                return ResponseDTO("E01", "이메일 형식이 잘못되었습니다.", "")
            }


            return try {
                val sender = JavaMailSenderImpl()
                sender.host = "smtp.gmail.com"
                sender.port = 587
                sender.username = "haningyainformation@gmail.com"
                sender.password = "Helxo116!"
                sender.javaMailProperties.setProperty("mail.smtp.auth", "true")
                sender.javaMailProperties.setProperty("mail.smtp.starttls.enable", "true")

                val message = sender.createMimeMessage()
                val helper = MimeMessageHelper(message,true)
                helper.setTo(email)
                helper.setText("table 데이터 첨부파일")
                helper.addAttachment("$tableName.csv",outputFile)
                helper.setSubject("디비 마스터 테이블 export 파일")
                helper.setFrom("haningyainformation@gmail.com", "dbMaster")
                helper.setText(StringBuffer().append("테이블 데이터 csv 파일")
                        .append("")
                        .toString()
                )

                sender.send(message)

                return ResponseDTO("S01", "파일이 이메일로 전송되었습니다.", outputFile)

            }catch (e: Exception) {

                return ResponseDTO("E01", e.toString(), "")
            }

        } catch (e: SQLException) {
            System.err.print("SQLException : " + e.message)
            return ResponseDTO("E01", e.toString(), "")
        }
    }




    @Throws(Exception::class)
    private fun fetchDataFromDatabase(selectQuery: String, connection: Connection) {
        try {
            val stmt = connection.createStatement()
            val rs = stmt.executeQuery(selectQuery)
            val numCols = rs.metaData.columnCount
            while (rs.next()) {
                val sb = StringBuilder()
                for (i in 1..numCols) {
                    sb.append(String.format(rs.getString(i).toString()) + " ")
                }
                resultSetArray.add(sb.toString())
            }
        } catch (e: SQLException) {
            print("Sql exception " + e.message)
//            .error("Sql exception " + e.message)
        }
    }

    @Throws(java.lang.Exception::class)
    fun printToCsv(resultArray: List<String>) : File {
        val csvOutputFile = File("exportedSCSV")
        val fileWriter = FileWriter(csvOutputFile, false)
        for (mapping in resultArray) {
            fileWriter.write("""
    $mapping
    
    """.trimIndent())
        }
        fileWriter.close()
        return csvOutputFile
    }

    val EMAIL_ADDRESS_PATTERN: Pattern = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    )
    private fun checkEmail(email: String): Boolean {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches()
    }

}