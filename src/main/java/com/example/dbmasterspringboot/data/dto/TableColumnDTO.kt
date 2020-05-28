package com.example.dbmasterspringboot.data.dto

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.ALWAYS)
data class TableColumnDTO(
        val ispk : String?,
        val columnName: String?,
        val datatype: String?,
        val columnsize: String?

//        val decimaldigits: String?,
//        val isNullable: String?,
//        val is_autoIncrment: String?
)