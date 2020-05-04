package com.example.dbmasterspringboot.data.dto

data class UserDTO(
        val dbname: String,
        val pw: String,
        val visit_count: String,
        val check_duplication: String,
        val latest_visit_date: String,
        val connection: String
)