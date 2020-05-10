# DBMasterSpringBoot

use https://tableconvert.com/ to make table

| **버전** | **날짜**       | **내용** | **작성자** |
|:------:|:------------:|:------:|:-------:|
| 1\.0   | 2020\.05\.10 | 추가     | 김태형     |
|        |              |        |         |
|        |              |        |         |
|        |              |        |         |




Base API : 기밀

| 주소         | /login/register         |                            |         |                         |
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
