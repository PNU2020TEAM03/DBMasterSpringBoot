# DBMasterSpringBoot
https://markdownlivepreview.com/
# 디비 마스터 api 문서

----
## 디비 마스터란?
see [postman link](https://documenter.getpostman.com/view/5249380/Szmcaz3f?version=latest#db69b269-bcc0-4e06-84e7-8105c07ad8b9)

> Markdown is a lightweight markup language, originally created by John Gruber and Aaron Swartz allowing people "to write using an easy-to-read, easy-to-write plain text format, then convert it to structurally valid XHTML (or HTML)".

----
## postman 으로 테스트하려면
1. Write markdown text in this textarea.
2. Click 'HTML Preview' button.
----
## changelog
* 2020.05.10 첫 등록
* 2020.05.13 테이블 정보찾기 api 리스폰스 수정 // 테이블 데이터 Update 항목 추가 // 테이블 데이터 Delete 항목 추가
* 2020.05.17 테이블 DROP api 추가 // 테이블 RENAME api 추가
* 2020 05.20 테이블 이름 중복검사 api 추가
* 2020.05.24 사용자 이메일 인증 요청 api 추가, 사용자 이메일 인증 확인 api 추가
* 2020.05.25 테이블 내 데이터 검색 api 추가, 사용자 지정 SELECT 쿼리문 처리 api 추가
* 2020.05.28 테이블 정보 받아오기 api 수정, 테이블 데이터 csv 파일 export api 추가
* 2020.05.30 비밀번호 수정 api 추가, 테이블 Join api 추가
* 2020.06.01 특정 칼럼 기준 정렬 api 추가
* 2020.06.07 특정 테이블 외래키 추가 api 추가
* 2020.06.13 회원가입 api 에러코드 수정, INSERT api 에러코드 수정, 테이블 데이터 전부 불러오기 에러코드 수정, 칼럼 업데이트 에러코드 수정
* 2020.06.20 테이블 데이터 delete 응답코드 추가, 테이블 DROP 응답코드 추가, Rename API 응답코드 추가
----
## 목차
* [회원가입 api](#회원가입-api)
* [아이디 중복검사 api](#아이디-중복검사-api)
* [로그인 api](#로그인-api)
* [테이블 생성 api](#테이블-생성-api)
* [테이블 모든 이름 조회 api](#테이블-모든-이름-조회-api)
* [테이블에 칼럼 insert api](#테이블에-칼럼-insert-api)
* [테이블 정보 받기 api](#테이블-정보-받기-api)
* [테이블 데이터 전부 불러오기 api](#테이블-데이터-전부-불러오기-api)
* [테이블 데이터 Update api](#테이블-데이터-Update-api)
* [테이블 데이터 Delete api](#테이블-데이터-Delete-api)
* [테이블 DROP api](#테이블-DROP-api)
* [테이블 RENAME api](#테이블-RENAME-api)
* [테이블 이름 중복검사 api](#테이블-이름-중복검사-api)
* [사용자 이메일 인증 api ](#사용자-이메일-인증-요청-api)
* [사용자 이메일 인증 확인 API](#사용자-이메일-인증-확인-api)
* [테이블 내 데이터 검색 API](#테이블-내-데이터-검색-api)
* [사용자 지정 SELECT 쿼리문 API](#사용자-지정-select-쿼리문-api)
* [테이블 데이터 csv 파일로 export API](#테이블-데이터-csv-파일로-export-api)
* [비밀번호 수정 API](#비밀번호-수정-api)
* [테이블 JOIN API](#테이블-join-api)
* [특정 칼럼 기준 정렬 API](#특정-칼럼-기준-정렬-api)
* [특정 테이블 외래키 추가 API](#특정-테이블-외래키-추가-api)

----
## 회원가입 api

*유저의 회원가입을 해주는 api*

* api 종류 : post
* 주소 : /v1/sign-up/request

**input data**

* name : String ( 필수)
* pw : String (필수)

>response

    {
    "result": "S01",
    "message": "회원가입에 성공했습니다.",
    "value": ""
    }

>error E01 아이디 중복

    {
        "result": "E01",
        "message": "test 은 아이디로 사용하실 수 없습니다.",
        "value": ""
    }
    
>error E02 아이디 미입력

    {
    "result": "E02",
    "message": "아이디가 입력되지 않았습니다.",
    "value": ""
    }
    
>error E03 비밀번호 미입력

    {
    "result": "E03",
    "message": "비밀번호가 입력되지 않았습니다.",
    "value": ""
    }
    
>error E04 SQL 문법 오류
    
    {
        "result": "E04",
        "message": "SQL 문법 오류입니다. 특수문자를 사용하지 마세요.",
        "value": ""
    }
    
>error E05 확인되지 않는 SQL 오류
    
    {
        "result": "E05",
        "message": "확인되지 않은 SQL 오류입니다.",
        "value": ""
    }

>error E03 비밀번호 미입력

----
## 아이디 중복검사 api
* api 종류 : post
* 주소 : /v1/sign-up/check-name

*회원가입 전 아이디 중복 검사를 해주는 api*

**input data**

* name : String ( 필수)

>response

    {
    "result": "S01",
    "message": "available",
    "value": ""
    }

>error

    {
    "result": "E01",
    "message": "duplicate",
    "value": ""
    }
----
## 로그인 api

* api 종류 : post
* 주소 : /v1/connection/check

*디비 커넥션이 가능한지 확인하는 api*

**input data**

* name : String (필수)
* pw : String (필수)

>response

    {
    "idValid": "available",
    "connectionValid": "available"
    }

>error

    {
    "idValid": "unavailable",
    "connectionValid": "unavailable"
    }



----
## 테이블 생성 api

* api 종류 : post
* 주소 : /v1/table/create

*데이터 베이스에 테이블을 생성하는 api*

**input data**

* name : String (필수)
* tableName : String (필수)
* fieldInfo : String(필수)

>예시 input

    {
	"name" : "uuzaza",
	"tableName" : "test2Table",
	"fieldInfo" : "sno int(11) NOT NULL, name char(10) DEFAULT NULL, PRIMARY KEY (sno)"
    }


>response

    {
    "idValid": "available",
    "connectionValid": "available"
    }

>error

    {
    "idValid": "unavailable",
    "connectionValid": "unavailable"
    }

----
## 테이블 모든 이름 조회 api

* api 종류 : post
* 주소 : /v1/table/all-tables

*로그인한 사용자가 가진 테이블 이름을 모두 반환하는 api*

**input data**

* name : String (필수)

>response

    {
    "result": "S01",
    "message": "",
    "value": [
        "test1",
        "test2",
        "test2Table",
        "test2Table2",
        "test2Table3"
    ]
    }

>error

    {
    "result": "S01",
    "message": "",
    "value": []
    }

----
## 테이블에 칼럼 insert api

* api 종류 : post
* 주소 : /v1/table/insert

*특정 테이블에 데이터를 insert 하는 api*

**input data**

* name : String (필수)
* tableName : String (필수)
* insert : String(필수)

>예시 input

    {
	"tableName" : "test1",
	"name" : "uuzaza",
	"insert" : "10, '테스트3'"
    }

>response

    {
    "result": "S01",
    "message": "insert 성공했습니다.",
    "value": ""
    }

>error

    {
        "result": "E01",
        "message": "테이블을 입력하지 않았습니다.",
        "value": ""
    }
    {
        "result": "E02",
        "message": "데이터베이스 이름을 입력하지 않았습니다.",
        "value": ""
    }
    {
        "result": "E03",
        "message": "입력할 데이터가 비어있습니다.",
        "value": ""
    }
    {
        "result": "E04",
        "message": "SQL 문법 오류입니다.",
        "value": "org.springframework.jdbc.BadSqlGrammarException: StatementCallback; bad SQL grammar [INSERT INTO test.tableB VALUES(33,201723332, '01022993322', 'testname', 'addressexample');]; nested exception is java.sql.SQLException: Column count doesn't match value count at row 1"
    }
    {
        "result": "E05",
        "message": "입력된 데이터 타입이 칼럼 타입과 다릅니다.",
        "value": ""
    }
    {
        "result": "E06",
        "message": "이미 입력된 데이터 입니다.",
        "value": ""
    }

----
## 테이블 정보 받기 api

* api 종류 : post
* 주소 : /v1/table/get-info

*특정 테이블의 칼럼 정보를 받아오는 api*

**input data**

* name : String (필수)
* tableName : String (필수)

>예시 input

    {
	"tableName" : "test1",
	"name" : "uuzaza",
    }

>response

    {
        "result": "S01",
        "message": "",
        "value": [
            {
                "ispk": "Y",
                "columnName": "sno",
                "datatype": "4",
                "columnsize": "10"
            },
            {
                "ispk": "N",
                "columnName": "name",
                "datatype": "1",
                "columnsize": "10"
            }
        ]
    }

>error

    {
    "result": "E01",
    "message": "정보가 없습니다.",
    "value": []
    }


----
## 테이블 데이터 전부 불러오기 api

* api 종류 : post
* 주소 : /v1/column/get-all

*Select * FROM TABLE*

**input data**

* name : String (필수)
* tableName : String (필수)

>예시 input

    {
	"tableName" : "test1",
	"name" : "uuzaza",
    }

>response

    {
    "result": "S01",
    "message": "",
    "value": [
        {
            "sno": 1,
            "name": "???"
        },
        {
            "sno": 2,
            "name": "이번항"
        },
        {
            "sno": 3,
            "name": "???"
        },
        {
            "sno": 4,
            "name": "??"
        },
        {
            "sno": 5,
            "name": "english"
        },
        {
            "sno": 6,
            "name": "???????"
        },
        {
            "sno": 7,
            "name": "메ㄸ데ㅐ"
        },
        {
            "sno": 8,
            "name": "테스트"
        },
        {
            "sno": 9,
            "name": "테스트2"
        },
        {
            "sno": 10,
            "name": "테스트3"
        },
        {
            "sno": 12,
            "name": "테스트3"
        }
    ]
    }

>error

    {
    "result": "E01",
    "message": "테이블을 입력하지 않았습니다.",
    "value": ""
    }
    {
    "result": "E02",
    "message": "데이터베이스 이름을 입력하지 않았습니다.",
    "value": ""
    }
    {
        "result": "E03",
        "message": "테이블이 존재하지 않습니다.",
        "value": ""
    }
    {
       "result": "E04",
       "message": "SQL 문법 오류입니다.",
       "value": "java.sql.SQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MariaDB server version for the right syntax to use near '?3##!! 3242.testTable!!' at line 1"
    }
----

## 테이블 데이터 Update api

* api 종류 : post
* 주소 : /v1/column/update

*UPDATE --- SET --- = --- WHERE pk = ---*

**input data**

* name : String (필수)
* tableName : String (필수)
* primary_key_name : String (필수)
* primary_key_value : String (필수)
* update_column_name : String (필수)
* update_value : String (필수)

>예시 input

    {
	"name" : "uuzaza",
	"tableName" : "test1",
	"primary_key_name" : "sno",
	"primary_key_value" : "1",
	"update_column_name" : "name",
	"update_value" : "'업데이트적용'"
     }


>response

    {
    "result": "S01",
    "message": "",
    "value": ""
     }

>error

    {
        "result": "E01",
        "message": "데이터 베이스 이름을 입력하지 않았습니다.",
        "value": ""
    }
    {
        "result": "E02",
        "message": "테이블 이름을 입력하지 않았습니다.",
        "value": ""
    }
    {
        "result": "E03",
        "message": "Primary Key를 입력하지 않았습니다.",
        "value": ""
    }
    {
        "result": "E04",
        "message": "Primary Key 값이 입력되지 않았습니다.",
        "value": ""
    }
    {
        "result": "E05",
        "message": "업데이트할 column 이름이 입력되지 않았습니다.",
        "value": ""
    }
    {
        "result": "E06",
        "message": "업데이트할 column의 값이 입력되지 않았습니다.",
        "value": ""
    }
    {
        "result": "E07",
        "message": "테이블이 존재하지 않습니다.",
        "value": ""
    }
    {
        "result": "E08",
        "message": "SQL 문법 오류입니다.",
        "value": "org.springframework.jdbc.BadSqlGrammarException: StatementCallback; bad SQL grammar [UPDATE test!.tableA SET name = 업데이트적용 WHERE sno = 1;]; nested exception is java.sql.SQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MariaDB server version for the right syntax to use near '!.tableA SET name = 업데이트적용 WHERE sno = 1' at line 1"
    }
    
    ----

## 테이블 데이터 Delete api

* api 종류 : post
* 주소 : /v1/column/delete

*DELETE --- WHERE WHERE pk = ---*

**input data**

* name : String (필수)
* tableName : String (필수)
* primary_key_name : String (필수)
* primary_key_value : String (필수)

>예시 input

    {
	"name" : "uuzaza",
	"tableName" : "test1",
	"primary_key_name" : "sno",
	"primary_key_value" : "6"
    }


>response

    {
        "result": "S01",
        "message": "삭제되었습니다.",
        "value": ""
    }

>error

    {
        "result": "E01",
        "message": "tableName 값이 입력되지 않았습니다.",
        "value": ""
    }
    {
        "result": "E02",
        "message": "name 값이 입력되지 않았습니다.",
        "value": ""
    }
    {
        "result": "E03",
        "message": "primary_key_name 값이 입력되지 않았습니다.",
        "value": ""
    }
    {
        "result": "E04",
        "message": "primary_key_value 값이 입력되지 않았습니다.",
        "value": ""
    }
    {
        "result": "E05",
        "message": "테이블이 존재하지 않습니다.",
        "value": ""
    }
    {
        "result": "E06",
        "message": "칼럼이 존재하지 않습니다.",
        "value": ""
    }
    
    
----

## 테이블 DROP api

* api 종류 : post
* 주소 : /v1/table/drop

*DROP TABLE ---*

**input data**

* name : String (필수)
* tableName : String (필수)

>예시 input

    {
	"name" : "test",
	"tableName" : "testTable"
     }


>response

    {
    "result": "S01",
    "message": "테이블이 삭제되었습니다.",
    "value": ""
    }

>error

    {
        "result": "E01",
        "message": "tableName 값이 입력되지 않았습니다.",
        "value": ""
    }
    {
        "result": "E02",
        "message": "name 값이 입력되지 않았습니다.",
        "value": ""
    }
    {
    "result": "E03",
    "message": "java.sql.SQLSyntaxErrorException: Unknown table 'DropTableTesttingHolyShitWhattheFuck'",
    "value": ""
    }
    
 ---
## 테이블 RENAME api

* api 종류 : post
* 주소 : /v1/table/rename

*ALTER TABLE name.TableName RENAME name.newName ---*

**input data**

* name : String (필수)
* tableName : String (필수)
* newName : String (필수)

>예시 input

    {
	"name" : "test",
	"tableName" : "test2Table",
	"newName" : "test2TableNewName"
	
    }


>response

    {
    "result": "S01",
    "message": "",
    "value": ""
    }

>error

    {
    "result": "E01",
    "message": "tableName 값이 입력되지 않았습니다.",
    "value": ""
    }
    {
        "result": "E02",
        "message": "name 값이 입력되지 않았습니다.",
        "value": ""
    }
    {
        "result": "E03",
        "message": "newName 값이 입력되지 않았습니다.",
        "value": ""
    }
    {
        "result": "E04",
        "message": "테이블 또는 데이터베이스가 존재하지 않습니다.",
        "value": ""
    }
    {
        "result": "E05",
        "message": "java.sql.SQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MariaDB server version for the right syntax to use near '' at line 1",
        "value": ""
    }
    
    
    
 ---
 ## 테이블 이름 중복검사 api

* api 종류 : post
* 주소 : /v1/table/duplicate

*테이블 이름의 중복여부 리턴 ---*

**input data**

* name : String (필수)
* tableName : String (필수)

>예시 input

    {
	"name" : "test",
	"tableName" : "test3Table"
    }


>response

    {
    "result": "S01",
    "message": "사용하실 수 있는 이름입니다.",
    "value": ""
    }

>error

    {
    "result": "E01",
    "message": "같은 이름의 테이블이 존재합니다.",
    "value": ""
    }
---
## 사용자 이메일 인증 요청 API

* api 종류 : post
* 주소 : /v1/auth/request

*사용자가 입력한 이메일에 인증번호를 전송한다.*

**input data**

* email : String (필수)

    >예시 input

        {
            "email" : "uuzaza74@gmail.com"
        }


    >response

        {
            "result": "S01",
            "message": "메일이 성공적으로 발송되었습니다.",
            "value": ""
        }

    >error

        {
            "result": "E01",
            "message": "이메일 형식이 잘못되었습니다.",
            "value": ""
        }
        
---
## 사용자 이메일 인증 확인 API

* api 종류 : post
* 주소 : /v1/auth/check

*사용자가 입력한 이메일에 전송된 인증번호를 받아서 디비값과 비교하여 인증한다.*

**input data**

* email : String (필수)
* authNum : String(필수)

>예시 input

        {
            "email" : "uuzaza74@gmail.com",
            "authNum" : "877387"
        }


>response

        {
            "result": "S01",
            "message": "인증되었습니다.",
            "value": null
        }

>error

    {
        "result": "E01",
        "message": "인증에 실패했습니다. 번호가 일치하지 않습니다.",
        "value": null
    }
    
---
## 테이블 내 데이터 검색 API
* api 종류 : post
* 주소 : /v1/table/search
*특정 테이블에 사용자가 입력한 keyword와 일치하는 데이터가 있는지 검색한다.*

**input data**

* tableName : String(필수)
* name : String(필수)
* keyword : String(필수)

    >예시 input

            {
                "tableName" : "testTable",
                "name" : "test",
                "keyword" : "3"
            }


    >response

            {
                "result": "S01",
                "message": "testTable 에서 [3] 검색결과",
                "value": [
                    {
                        "sno": "3",
                        "name": "테스트3"
                    }
                ]
            }

    >error

        {
            "result": "E02",
            "message": "java.sql.SQLSyntaxErrorException: Table 'test2.testTable' doesn't exist",
            "value": null
        }
        {
            "result": "E01",
            "message": "name 값이 입력되지 않았습니다.",
            "value": ""
        }
        {
            "result": "E02",
            "message": "tableName 값이 입력되지 않았습니다.",
            "value": ""
        }
        {
            "result": "E03",
            "message": "keyword 값이 입력되지 않았습니다.",
            "value": ""
        }
        {
            "result": "E04",
            "message": "테이블 또는 데이터베이스가 존재하지 않습니다.",
            "value": ""
        }
        {
            "result": "E05",
            "message": "java.sql.SQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MariaDB server version for the right syntax to use near '!.tableB' at line 1",
            "value": null
        }
        
---
## 사용자 지정 SELECT 쿼리문 API
* api 종류 : post
* 주소 : /v1/query/custom
*사용자가 지정한 커스텀한 SELECT 문을 처리할 수 있다.*

**input data**

* tableName : String(필수)
* name : String(필수)
* query : String(필수)

    >예시 input

            {
                "name" : "test",
                "tableName" : "testTable",
                "query" : "SELECT * FROM test.testTable WHERE sno = 2"
            }


    >response

            {
                "result": "S01",
                "message": "",
                "value": [
                    {
                        "sno": 2,
                        "name": "테스트2"
                    }
                ]
            }

    >error

        {
            "result": "E01",
            "message": "name 값이 입력되지 않았습니다.",
            "value": ""
        }
        {
            "result": "E02",
            "message": "tableName 값이 입력되지 않았습니다.",
            "value": ""
        }
        {
            "result": "E03",
            "message": "query 값이 입력되지 않았습니다.",
            "value": ""
        }
        {
            "result": "E04",
            "message": "테이블 또는 데이터베이스가 존재하지 않습니다.",
            "value": ""
        }
        {
            "result": "E05",
            "message": "SQL 문법 오류",
            "value": "java.sql.SQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MariaDB server version for the right syntax to use near '!' at line 1"
        }
---
   ## 테이블 데이터 csv 파일로 export API
   * api 종류 : post
   * 주소 : /v1/table/export
   *사용자가 선택한 테이블 데이터 전체를 csv 파일로 변환하여 이메일로 보낸다.*

   **input data**

   * tableName : String(필수)
   * name : String(필수)
   * email : String(필수)

       >예시 input

               {
                   "tableName" : "testTable",
                   "name" : "test",
                   "email" : "uuzaza@naver.com"
               }


       >response

               {
                   "result": "S01",
                   "message": "파일이 이메일로 전송되었습니다.",
                   "value": "/Users/taehyeongkim/Documents/GitHub/DBMasterSpringBoot/exportedSCSV"
               }

       >error

           {
               "result": "E01",
               "message": "Table 'test2.testTable' doesn't exist",
               "value": ""
           }
   ---
   ## 비밀번호 수정 API
   * api 종류 : post
   * 주소 : /v1/pw/change
   *사용자의 계정 비밀번호를 수정한다.*

   **input data**

   * name : String(필수)
   * oldPw : String(필수)
   * newPw : String(필수)

       >예시 input

               {
                   "name" : "test",
                   "oldPw" : "test",
                   "newPw" : "1q2w3e4r"
               }


       >response

               {
                   "result": "S01",
                   "message": "비밀번호가 수정되었습니다..",
                   "value": ""
               }

       >error

           {
               "result": "E01",
               "message": "이전 비밀번호가 일치하지 않습니다.",
               "value": ""
           }
   ---
   ## 테이블 JOIN API
   * api 종류 : post
   * 주소 : /v1/table/join
   *두 테이블을 입력받은 column 정보로 Join 하여 표시한다.*

   **input data**

   * name : String(필수)
   * tableName : String(필수)
   * joinTable : String(필수)
   * joiningColumn : String(필수)


       >예시 input

               {
                   "name" : "test",
                   "tableName" : "tableA",
                   "joinTable" : "tableB",
                   "joiningColumn" : "id"
               }


       >response

               {
                   "result": "S01",
                   "message": "tableA 과 tableB 의 join 결과",
                   "value": [
                       {
                           "address": "xfds",
                           "phone": "01029302",
                           "name": "fewg",
                           "payment": "1239",
                           "id": "10293039",
                           "dept": "pop",
                           "hobby": "eng"
                       },
                       {
                           "address": "few",
                           "phone": "01029382938",
                           "name": "awef",
                           "payment": "10",
                           "id": "19920392",
                           "dept": "sw",
                           "hobby": "wfa"
                       },
                       {
                           "address": "awef",
                           "phone": "01023231232",
                           "name": "jeijfe",
                           "payment": "12899",
                           "id": "201524447",
                           "dept": "qwd",
                           "hobby": "qwr1"
                       },
                       {
                           "address": "idonkwn",
                           "phone": "01012341234",
                           "name": "Kim",
                           "payment": "80000",
                           "id": "201724447",
                           "dept": "sdw",
                           "hobby": "???"
                       }
                   ]
               }

       >error

           {
               "result": "E02",
               "message": "java.sql.SQLSyntaxErrorException: Unknown column 'a.id2' in 'where clause'",
               "value": null
           }
   ---
   ## 특정 칼럼 기준 정렬 API
   * api 종류 : post
   * 주소 : /v1/table/sort
   *입력받은 테이블에 대해 입력받은 칼럼을 입력받은 순서(오름차순. 내림차순) 으로 정렬히여 반환한다.*

   **input data**

   * name : String(필수)
   * tableName : String(필수)
   * sortColumn : String(필수)
   * direction : String(필수)


       >예시 input

               {
                   "name" : "test",
                   "tableName" : "testTable",
                   "sortColumn" : "name",
                   "direction" : "DESC"
               }



       >response

               {
                   "result": "S01",
                   "message": "",
                   "value": [
                       {
                           "sno": 9,
                           "name": "테스트9",
                           "testColumn1": 1,
                           "testColumn2": 2
                       },
                       {
                           "sno": 8,
                           "name": "테스트8",
                           "testColumn1": 1,
                           "testColumn2": 2
                       },
                       {
                           "sno": 6,
                           "name": "테스트6",
                           "testColumn1": 1,
                           "testColumn2": 2
                       },
                       {
                           "sno": 5,
                           "name": "테스트5",
                           "testColumn1": 1,
                           "testColumn2": 2
                       },
                       {
                           "sno": 4,
                           "name": "테스트4",
                           "testColumn1": 1,
                           "testColumn2": 2
                       },
                       {
                           "sno": 3,
                           "name": "테스트3",
                           "testColumn1": 1,
                           "testColumn2": 2
                       },
                       {
                           "sno": 2,
                           "name": "테스트2",
                           "testColumn1": 1,
                           "testColumn2": 2
                       },
                       {
                           "sno": 12,
                           "name": "테스트12",
                           "testColumn1": 1,
                           "testColumn2": 2
                       },
                       {
                           "sno": 11,
                           "name": "테스트11",
                           "testColumn1": 1,
                           "testColumn2": 2
                       },
                       {
                           "sno": 10,
                           "name": "테스트10",
                           "testColumn1": 1,
                           "testColumn2": 2
                       },
                       {
                           "sno": 1,
                           "name": "테스트1",
                           "testColumn1": 1,
                           "testColumn2": 2
                       }
                   ]
               }

       >error

           {
               "result": "E02",
               "message": "java.sql.SQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MariaDB server version for the right syntax to use near 'DESCa' at line 1",
               "value": null
           }
           
           
   ---
   ## 특정 테이블 외래키 추가 API
   * api 종류 : post
   * 주소 : /v1/table/set-foreign
   *두개의 테이블을 입력받아 테이블 간 외래키 관계를 추가해 준다..*

   **input data**

   * name : String(필수)
   * tableName : String(필수)
   * baseColumn : String(필수)
   * targetTable : String(필수)
   * targetColumn : String(필수) 


       >예시 input "ALTER TABLE test.tableA ADD FOREIGN KEY (id) REFERENCES test.tableB(id)"

               {
                   "name" : "test",
                   "tableName" : "tableA",
                   "baseColumn" : "id",
                   "targetTable" : "tableB",
                   "targetColumn" : "id"
               }





       >response

               {
                   "result": "S01",
                   "message": "",
                   "value": ""
               }

       >error

           {
               "result": "E01",
               "message": "java.sql.SQLException: Can't create table 'test.#sql-14f3_c9a4' (errno: 150)",
               "value": ""
           }
           
---
-
## 특정 테이블 외래키 정보 조회 API
* api 종류 : post
* 주소 : /v1/table/get-foreign
*특정 테이블에 있는 외래키 정보를 받아온다.*

**input data**

* name : String(필수)
* tableName : String(필수)



    >예시 input

            {
                "name" : "test",
                "tableName" : "tableB"
            }

    >response

            {
                "result": "S01",
                "message": "tableA 의 id 칼럼과 외래키 관계임니다.",
                "value": ""
            }

    >error

        {
            "result": "E01",
            "message": "외래키가 없습니다.",
            "value": ""
        }
        
## thanks
* [markdown-js](https://github.com/evilstreak/markdown-js)

