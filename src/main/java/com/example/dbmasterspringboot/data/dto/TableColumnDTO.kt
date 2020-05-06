package com.example.dbmasterspringboot.data.dto


data class TableColumnDTO(
        val columnName: String?,
        val datatype: String?,
        val columnsize: String?,
        val decimaldigits: String?,
        val isNullable: String?,
        val is_autoIncrment: String?
)