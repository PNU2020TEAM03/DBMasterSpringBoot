package com.example.dbmasterspringboot.controller.controlmail

import com.example.dbmasterspringboot.data.dto.ResponseDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.sql.*
import java.util.*
import kotlin.collections.HashMap

@RestController
class CustomMailSender(
        @Autowired val jdbcTemplate: JdbcTemplate
) {

    //인증받을 이메일 수신
    @RequestMapping("/v1/auth/request")
    fun insertTable(
            @RequestBody response: HashMap<String, String>
    ): ResponseDTO {
        //파라미터 받고
        val email = response["email"]
        if (email == null) {
            return ResponseDTO("E01", "파라미터가 잘못 설정됬습니다. email", "")
        }
        println(email)
        //random number
        val ranNum : String = (Random().nextInt(900000) + 100000).toString()

        return try {
            val SELECT_ALL_FROM_TABLE = "INSERT INTO dbmaster_master.dbmaster_authenticate VALUES('$email', '$ranNum');"
            println(SELECT_ALL_FROM_TABLE)
            jdbcTemplate.execute(SELECT_ALL_FROM_TABLE)
            val map = HashMap<String, String>()
            map.put("address", email)
            map.put("authNum",ranNum)
            sendMail(map)
            ResponseDTO("S01", "메일함을 확인해 주세요.", "")

        } catch (e: SQLException) {
            System.err.print("SQLException : " + e.message)
            ResponseDTO("E01", e.toString(), "")
        }

    }

    //인증받을 이메일에 랜덤 6자리 번호 만들어서 보내주고 인증 디비에 이메일과 번호 저장
    @RequestMapping("/v1/mail/request")
    fun sendMail(
            @RequestBody response: HashMap<String, String>
    ): ResponseDTO {
        val address = response["address"] ?: return ResponseDTO("E01", "파라미터가 잘못 설정됬습니다.", "")
        val authNum = response["authNum"] ?: return ResponseDTO("E01", "파라미터가 잘못 설정됬습니다. authNum", "")
        print(address)
        print(authNum)
        try {
            val sender = JavaMailSenderImpl()
            sender.host = "smtp.gmail.com"
            sender.port = 587
            sender.username = "haningyainformation@gmail.com"
            sender.password = "Helxo116!"
            sender.javaMailProperties.setProperty("mail.smtp.auth", "true")
            sender.javaMailProperties.setProperty("mail.smtp.starttls.enable", "true")

            val senderMail = "uuzaza@naver.com"
            val mail = MailHandler(sender)
            mail.setFrom("haningyainformation@gmail.com", "dbMaster")
            mail.setTo(address)
            mail.setSubject("디비 마스터 회원가입 인증번호 메일")


            mail.setText(StringBuffer().append("<h1>회원가입 인증메일입니다.</h1>")
                    .append("<p>3분 내로 인증번호를 입력해 주세요</p>")
                    .append(authNum)
                    .toString()
            )
            mail.send()
            return ResponseDTO("S01", "메일이 성공적으로 발송되었습니다.", "")
        } catch (e: Exception) {
            return ResponseDTO("E01", e.toString(), "")
        }

    }

    //사용자로부터 이메일과 인증번호를 받으면 인증 디비와 비교하여 일치하는지 판단
    @RequestMapping("/v1/auth/check")
    fun selectAllData(
            @RequestBody response: HashMap<String, String>
    ): ResponseDTO {
        val email = response["email"]
        val authNum = response["authNum"]

        if (email == null || authNum == null) {
            return ResponseDTO("E01", "파라미터가 잘못 설정됬습니다. email, authNum", "")
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

            val SELECT_ALL_QUERY = "SELECT * FROM dbmaster_master.dbmaster_authenticate;"
            var pstmt = con.prepareStatement(SELECT_ALL_QUERY)
            var authNumArray = ArrayList<String>()

            try {
                con.prepareStatement(
                        SELECT_ALL_QUERY, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).use { selectStmt ->
                    selectStmt.executeQuery().use { rs ->
                        if (!rs.isBeforeFirst()) {
                            println("no rows found")
                        } else {
                            while (rs.next()) {
                                for (i in 1 until rs.getMetaData().getColumnCount() + 1) {
                                    authNumArray.add(rs.getString(i))
                                } // 한컬럼 다 넣어
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

            val answerAuthNum = authNumArray.last()
            print(answerAuthNum)

            //answerAuthNum 랑 디비 받은 값이랑 비교한다.
            if(answerAuthNum == authNum){
                return ResponseDTO("S01", "인증되었습니다.", null)
            }else{
                return ResponseDTO("E01", "인증에 실패했습니다. 번호가 일치하지 않습니다.", null)
            }

        } catch (e: SQLException) {
            System.err.print("SQLException : " + e.message)
            return ResponseDTO("E01", e.toString(), "")
        }
    }
}