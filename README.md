# DBMasterSpringBoot

use https://tableconvert.com/ to make table

| **버전** | **날짜**       | **내용** | **작성자** |
|:------:|:------------:|:------:|:-------:|
| 1\.0   | 2020\.05\.10 | 추가     | 김태형     |
|        |              |        |         |
|        |              |        |         |
|        |              |        |         |




Base API : 기밀


[예시]
| 주소         | /v1/sign-up/check-name        |                            |         |                         |
|--------------|-------------------------|----------------------------|---------|-------------------------|
| api 종류     |                                                                                       |
| 설명         |                                                                                   |
| input형태    |                                                                                          |
| input데이터  | Id                      | 필수                       | string  | 이메일 형식             |
|              | pw                      | 필수                       | string  | 영어+숫자 조합 8자이상  |
|              | company_name            | 필수                       | string  | 업체이름                |
|              | company_address         | 필수                       | string  | 업체주소                |
|              | postcode                |                            | string  | 우편번호                |
|              | Company_address_detail  |                            | string  | 주소 상세               |
|              | owner                   | 필수                       | string  | 사장                    |
|              | phone                   | 필수                       | string  | 사장폰                  |
| 결과         | {"code": "S01"}         |                            |         |                         |
| 에러코드     | E00                     | 서버 syntax 에러(주로 db)  |         |                         |
|              | E01                     | input error                |         |                         |
|              | E02                     | 이미 가입 된 아이디        |         |                         |




# 회원가입
| 주소         | /v1/sign-up/request       |                            |         |                         |
|--------------|-------------------------|----------------------------|---------|-------------------------|
| api 종류     |                                                                                       |
| 설명         |                                                                                   |
| input형태    |                                                                                          |
| input데이터  | name                      | 필수                       | string  | 영문 아이디             |
|              | pw                      | 필수                       | string  | 8자이상 16자 이하 영문숫자 특수문자 조합  |
| 결과         | {"result": "S01","message": "회원가입에 성공했습니다.","value": ""}                        |   
| 에러코드     | E01                    | 아이디 중복됨  |         |                         |


# 아이디 중복검사
| 주소         | /v1/sign-up/check-name     |                            |         |                         |
|--------------|-------------------------|----------------------------|---------|-------------------------|
| api 종류     |                                                                                       |
| 설명         |                                                                                   |
| input형태    |                                                                                          |
| input데이터  | name                     | 필수                       | string  | 영문 아이디             |
| 결과         | {"result": "S01","message": "회원가입에 성공했습니다.","value": ""}                        |   
| 에러코드     | E01                    | 아이디 중복됨  |     {"result": "E01","message": "duplicate","value": ""}    |         |

# 아이디 중복검사
| 주소         | /v1/connection/check    |                            |         |                         |
|--------------|-------------------------|----------------------------|---------|-------------------------|
| api 종류     |                                                                                       |
| 설명         |                                                                                   |
| input형태    |                                                                                          |
| input데이터  | name                     | 필수                       | string  | 영문 아이디             |
|              | pw                      | 필수                       | string  | 8자이상 16자 이하 영문숫자 특수문자 조합  |
| 결과         | {"result":"S01","message":"available","value":""}                      |   
| 에러코드     | E01                    | 아이디 중복됨  |     {"result": "E01","message": "duplicate","value": ""}    |         |

# 로그인
| 주소         | /v1/connection/check    |                            |         |                         |
|--------------|-------------------------|----------------------------|---------|-------------------------|
| api 종류     |                                                                                       |
| 설명         |                                                                                   |
| input형태    |                                                                                          |
| input데이터  | name                     | 필수                       | string  | 영문 아이디             |
|              | pw                      | 필수                       | string  | 8자이상 16자 이하 영문숫자 특수문자 조합  |
| 결과         | {"idValid":"available","connectionValid":"available"}                    |   
| 에러코드     | E01                    | 접근불가  |     {"idValid":"unavailable","connectionValid":"unavailable"}   |         |

# 테이블 생성
| 주소         | /v1/connection/check    |                            |         |                         |
|--------------|-------------------------|----------------------------|---------|-------------------------|
| api 종류     |                                                                                       |
| 설명         |                                                                                   |
| input형태    |                                                                                          |
| input데이터  | name      | 필수               | string  | 영문 아이디             |
|              | tableName       | 필수       | string  | 8자이상 16자 이하 영문숫자 특수문자 조합  |
|              | fieldInfo     | 필수     | string  | sno int(11) NOT NULL, name char(10) DEFAULT NULL, PRIMARY KEY (sno)  |
| 결과         | {"idValid":"available","connectionValid":"available"}                    |   
| 에러코드     | E01              | 이미 있는 테이블  |    {"result":"E01","message":"java.sql.SQLSyntaxErrorException: Table 'test2Table' already exists","value":""}  |         |






