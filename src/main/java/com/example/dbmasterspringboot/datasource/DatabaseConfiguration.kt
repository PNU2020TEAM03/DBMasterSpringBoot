//package com.example.dbmasterspringboot.datasource
//
//import org.springframework.boot.jdbc.DataSourceBuilder
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource
//import org.springframework.web.bind.annotation.PostMapping
//import org.springframework.web.bind.annotation.RequestBody
//import org.springframework.web.bind.annotation.RestController
//import javax.sql.DataSource
//import javax.xml.crypto.Data
//
//@Configuration
//@RestController
//open class DatabaseConfiguration {
//
//    private val BASE_URL = "jdbc:mysql://54.180.95.198:5536/"
//    private val BASE_QUERIES = "?serverTimezone=UTC&useSSL=true&useUnicode=true&characterEncoding=utf8"
//
//    private var name: String = "wooyoung"
//    private var pw: String = "root"
//
//    @PostMapping("/v1/create/datasource", "application/json")
//    fun getUserInfo(
//            @RequestBody user: HashMap<String, String>
//    ) {
//        val name = user["name"]
//        val pw = user["pw"]
//        println("$name AND $pw")
//        if (name != null && pw != null) {
//            this.name = name
//            this.pw = pw
//
//            createRouterDatasource()
//        }
//    }
//
//    @Bean
//    open fun createRouterDatasource(): DataSource {
//        val routingDataSource = MyRoutingDataSource()
//        val targetDataSources = HashMap<Any, Any>()
//
//        println("CREATE ${this.name} AND ${this.pw}")
//        val url = BASE_URL + this.name + BASE_QUERIES
//
//        targetDataSources[this.name] = createDataSource(url, this.name, this.pw)
//
//        routingDataSource.setTargetDataSources(targetDataSources)
//
//        return routingDataSource
//    }
//
//    private fun createDataSource(
//            url: String, name: String, pw: String
//    ): DataSource{
//        println(" CREATE $url AND $name AND $pw")
//        val dataSource = DataSourceBuilder.create()
//        dataSource.driverClassName("com.mysql.cj.jdbc.Driver")
//        dataSource.username(name)
//        dataSource.password(pw)
//        dataSource.url(url)
//
//        return dataSource.build()
//    }
//}