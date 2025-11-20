package com.example.loginapp.model

data class Faculty(
    val id: Int,
    val name: String,
    val departmentId: Int,
    val courseId: Int? = null,
    val roomNo: String,
    val floorNo: String,
    val building: String
)
