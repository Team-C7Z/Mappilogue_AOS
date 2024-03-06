package com.c7z.mappilogue_aos.presentation.util

data class FailureResponse(
    val isSuccess : Boolean,
    val statusCode : Int,
    val errorCode : String,
    val target : String,
    val message : String,
    val errorStack : String,
    val timestamp : String,
    val path : String
)
