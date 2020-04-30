package com.example.dbmasterspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.tags.Param;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class jdbcController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    //test code
    @RequestMapping(value = "/test")
    public List<Map<String, Object>> test(){
        String tableName = "blogs";
        List<Map<String, Object>> data = new ArrayList<>();
        data = jdbcTemplate.queryForList("SELECT * FROM "+tableName);

        return data;
    }
    //아이디 비밀번호 받는 함수
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest httpServletRequest){
        try{
            System.out.println("RequestMethod.post");
            String id = httpServletRequest.getParameter("id");
            System.out.println("id : " + id);
            String pw = httpServletRequest.getParameter("pw");
            System.out.println("pw : " + pw);
            return "S01";
        }catch (Exception e) {
            return "E01";
        }
    }

    //테이블 만들기
    @RequestMapping(value = "/createTable")
    public String createTable(String tableName,String column){
        //example
        tableName = "testTable";
        column = "id bigint(20) unsigned NOT NULL AUTO_INCREMENT,\n" +
                "  subject varchar(255) NOT NULL,\n" +
                "  content mediumtext,\n" +
                "  created datetime,\n" +
                "  user_id int(10) unsigned NOT NULL,\n" +
                "  user_name varchar(32) NOT NULL,\n" +
                "  hit int(10) unsigned NOT NULL default '0',  \n" +
                "  PRIMARY KEY (id)";
        try{
            jdbcTemplate.execute("CREATE TABLE "+tableName+" ("+column+");");
            return "S01";
        }catch (Exception e){
            e.printStackTrace();
            return String.valueOf(e);
        }
    }
    //테이블 정보 받기
    @RequestMapping(value = "/desc")
    public void getTablesMetadata(String tableName) throws SQLException {
        new DBMetaData().metadataTest();
    }

}
