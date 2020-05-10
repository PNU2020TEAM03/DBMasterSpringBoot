# DBMasterSpringBoot

use https://tableconvert.com/ to make table

| **버전** | **날짜**       | **내용** | **작성자** |
|:------:|:------------:|:------:|:-------:|
| 1\.0   | 2020\.05\.10 | 추가     | 김태형     |
|        |              |        |         |
|        |              |        |         |
|        |              |        |         |




Base API : 기밀


| 주소         | /login/register                                                                            |
|--------------|-------------------------|----------------------------|---------|-------------------------|
| api 종류     |                                                                                           |
| 설명         |                                                                                         |
| input형태    |                         |                            |         |                         |
| input데이터  | Id                      | 필수                       | string  | 이메일 형식             |
|              | pw                      | 필수                       | string  | 영어+숫자 조합 8자이상  |
|              | company_name            | 필수                       | string  | 업체이름                |
|              | company_address         | 필수                       | string  | 업체주소                |
|              | postcode                |                            | string  | 우편번호                |
|              | Company_address_detail  |                            | string  | 주소 상세               |
|              | owner                   | 필수                       | string  | 사장                    |
|              | phone                   | 필수                       | string  | 사장폰                  |
| 결과         |                  { 

"domain": "https://taky.co.kr", 

"code": "S01", 

"admin_serial": "56", 

"admin_name": "노성환", 

"company_serial": "57", 

"company_name": "프리랜드", 

"use_taky": "N", 

"use_cardreader": "N", 

"use_online_market": "N", 

"min_point": "100", 

"max_point": "1000", 

"use_app_order": "N", 

"use_app_waiting": "N", 

"use_coupon_msg": "N", 

"use_kakaopay": "Y", 

"use_cashbill": "Y", 

"start_date": "2019-05-01", 

"end_date": "2019-12-31", 

"subscription": "F", 

"notice": "" 

}                   |
| 에러코드     | E00                     | 서버 syntax 에러(주로 db)  |         |                         |
|              | E01                     | input error                |         |                         |
|              | E02                     | 이미 가입 된 아이디        |         |                         |
