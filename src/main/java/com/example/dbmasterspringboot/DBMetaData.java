package com.example.dbmasterspringboot;

import java.sql.*;

public class DBMetaData {
    String url = "jdbc:mysql://localhost:3306/masterdb?serverTimezone=UTC&useSSL=true";
    String query = "SELECT * FROM masterdb.testtable";
    Connection con = null;
    Statement stmt = null;

    public void metadataTest() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (java.lang.ClassNotFoundException e) {
            System.err.print("ClassNotFoundException : " + e.getMessage());
        }

        try {
            con = DriverManager.getConnection(url, "root", "Helxo116!");
            System.out.println("Connected to DB ............");
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            // 테이블의 메타데이타 정보를 구성하여 ResultSetMetaData 에 저장
            ResultSetMetaData rsmd = rs.getMetaData();

            int numberOfColumn = rsmd.getColumnCount();

            System.out.println("============================================================");
            System.out.println("Number of Columns : " + numberOfColumn);
            System.out.println("============================================================");
            System.out.println("Column Name  |  JDBC type  |  Null Allowed  |  Read Only    ");
            System.out.println("============================================================");

            for (int i = 1; i <= numberOfColumn; i++) {
                String columnName = rsmd.getColumnName(i);// 필드명
                String jdbcType = rsmd.getColumnTypeName(i);// 필드 속성
                int isNull = rsmd.isNullable(i); // 필드 Null 허용여부
                int jdbcLength = rsmd.getPrecision(i); // 컬럼 length
                boolean rOnly = rsmd.isReadOnly(i); // 읽기 전용 여부

                System.out.print(columnName);// 필드 명을 출력

                for (int j = 0; (columnName.length() + j) < 12; j++) {
                    System.out.print(" ");
                }
                System.out.print("     " + jdbcType + "(" + jdbcLength + ")"); // 필드의 타입을 출력
                for (int j = 0; (jdbcType.length() + j) < 12; j++) {
                    System.out.print(" ");
                }
                if (isNull == 0)// 필드의 Null 허용 여부 출력
                {
                    System.out.print("  Not Null ");
                } else if (isNull == 1) {
                    System.out.print("   Null    ");
                } else {
                    System.out.print("   Unknown ");
                }

                System.out.println("         " + rOnly);// 읽기 전용 여부 출력
            }
            stmt.close();
            con.close();
            System.out.println("Disconnected From DB ..........");

        } catch (SQLException e) {
            System.err.print("SQLException : " + e.getMessage());
        }
    }
}