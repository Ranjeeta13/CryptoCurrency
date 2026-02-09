package com.example.crypto.models

data class Platform(
    val id: Int,
    val name: String,
    val slug: String,
    val symbol: String,
    val tokenAddress: String
)