//package com.example.dbmasterspringboot.datasource
//
//import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource
//import org.springframework.web.context.request.RequestAttributes
//import org.springframework.web.context.request.RequestContextHolder
//
//class MyRoutingDataSource: AbstractRoutingDataSource() {
//
//    override fun determineCurrentLookupKey(): Any? {
//        val dbKey = RequestContextHolder
//                .getRequestAttributes()
//                ?.getAttribute("db_key", RequestAttributes.SCOPE_SESSION)
//
//        return "current: $dbKey"
//    }
//}