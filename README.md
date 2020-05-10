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
----
## 목차
[1.회원가입](##회원가입)
[2.아이디중복검사](##아이디-중복검사-api)
[3.로그인](##로그인-api)
[4.테이블 생성](##테이블-생성-api)
[5.모든 테이블 이름 조회](##테이블-모든-이름-조회-api)
[6.테이블에 칼럼 insert](##테이블에-칼럼-insert-api)
[7.테이블 정보 받기](##테이블 정보 받기 api)
[8.테이블 데이터 전부 불러오기](##테이블-데이터-전부-불러오기-api)

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
            "columnName": "sno",
            "datatype": "4",
            "columnsize": "10",
            "decimaldigits": null
        },
        {
            "columnName": "name",
            "datatype": "1",
            "columnsize": "10",
            "decimaldigits": null
        }
    ]
    }

>error

    {
    "result": "S01",
    "message": "",
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
## thanks
* [markdown-js](https://github.com/evilstreak/markdown-js)

