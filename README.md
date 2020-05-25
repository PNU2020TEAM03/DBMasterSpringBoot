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

>error

    {
    "result": "E01",
    "message": "java.sql.SQLIntegrityConstraintViolationException: Duplicate entry 'test123' for key 'PRIMARY'",
    "value": ""
    }

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
    "timestamp": "2020-05-10T02:45:43.846+0000",
    "status": 500,
    "error": "Internal Server Error",
    "message": "StatementCallback; SQL [INSERT INTO uuzaza.test1 VALUES(10, '테스트3');]; Duplicate entry '10' for key 'PRIMARY'; nested exception is java.sql.SQLIntegrityConstraintViolationException: Duplicate entry '10' for key 'PRIMARY'",
    "path": "/dbmasterspringboot-1.0/v1/table/insert"
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
            "primaryKey": "Y",
            "columnName": "sno",
            "datatype": "4",
            "columnsize": "10",
            "decimaldigits": null
        },
        {
            "primaryKey": "N",
            "columnName": "name",
            "datatype": "1",
            "columnsize": "10",
            "decimaldigits": null
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
    "result": "E02",
    "message": "java.sql.SQLSyntaxErrorException: Table 'uuzaz3a.test1' doesn't exist",
    "value": null
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
    "message": "java.sql.SQLSyntaxErrorException: Unknown column '업데이트적용' in 'field list'",
    "value": ""
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
    "message": "",
    "value": ""
    }

>error

    {
    "result": "E01",
    "message": "java.sql.SQLSyntaxErrorException: Unknown column 'sno3' in 'where clause'",
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
    "message": "",
    "value": ""
    }

>error

    {
    "result": "E01",
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
    "message": "java.sql.SQLSyntaxErrorException: Table 'test.test2Table' doesn't exist",
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
            "result": "E02",
            "message": "java.sql.SQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MariaDB server version for the right syntax to use near '2' at line 1",
            "value": null
        }
---
   
## thanks
* [markdown-js](https://github.com/evilstreak/markdown-js)

