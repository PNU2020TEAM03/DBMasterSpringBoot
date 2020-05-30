package com.example.dbmasterspringboot.controller.signup

import com.example.dbmasterspringboot.data.dto.ResponseDTO
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.queryForObject
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.sql.*
import java.time.LocalDate


@RestController
class ChangePasswordController(
        @Autowired val jdbcTemplate: JdbcTemplate
) {

    private var duplicationResult: String? = null

    /* TODO 회원가입 시 중복확인에 대한 함수 중복이면 duplicate 리턴, 없으면 null 리턴 */
    @PostMapping("/v1/pw/change")
    fun checkDBName(
            @RequestBody name: HashMap<String, String>
    ): ResponseDTO {
        val id: String? = name["name"]
        val oldPw : String? = name["oldPw"]
        val newPw : String? = name["newPw"]

        if(id == null || oldPw == null || newPw == null){
            return ResponseDTO("E01","파라미터가 잘못되었습니다. id, oldPw, newPw","")
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

            val SELECT_OLD_PW_QUERY = "SELECT pw FROM dbmaster_users WHERE dbname = '$id'"
            var pstmt = con.prepareStatement(SELECT_OLD_PW_QUERY)
            var jsonArray = JSONArray()
            try {
                con.prepareStatement(
                        SELECT_OLD_PW_QUERY, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).use { selectStmt ->
                    selectStmt.executeQuery().use { rs ->
                        if (!rs.isBeforeFirst()) {
                            println("no rows found")
                        } else {
                            while (rs.next()) {
                                var obj = JSONObject()
                                for (i in 1 until rs.getMetaData().getColumnCount() + 1) {
                                    val name = rs.metaData.getColumnName(i)
                                    val value = rs.getObject(i)
                                    println(name)
                                    println(value)
                                    print(oldPw)
                                    if(name == "pw"){
                                        if(oldPw != value) {
                                            return ResponseDTO("E01", "이전 비밀번호가 일치하지 않습니다.", "")
                                        }
                                    }

                                } // 한컬럼 다 넣어짐
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

            /* 새로운 비밀번호 설정 */
            return try {
                val UPDATE_TABLE_QUERY = "UPDATE dbmaster_users SET pw = '$newPw' WHERE dbname = '$id';"
                println(UPDATE_TABLE_QUERY)
                jdbcTemplate.execute(UPDATE_TABLE_QUERY)
                ResponseDTO("S01","비밀번호가 수정되었습니다..","")

            } catch (e: Exception) {
                val resultStr = e.cause.toString()
                ResponseDTO("E01",resultStr,"")
            }

        } catch (e: SQLException) {
            System.err.print("SQLException : " + e.message)
            return ResponseDTO("E01", e.toString(), "")
        }
    }


}
