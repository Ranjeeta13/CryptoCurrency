package com.example.crypto.models

data class Status(
    val creditCount: Int,
    val elapsed: String,
    val errorCode: String,
    val errorMessage: String,
    val timestamp: String
)