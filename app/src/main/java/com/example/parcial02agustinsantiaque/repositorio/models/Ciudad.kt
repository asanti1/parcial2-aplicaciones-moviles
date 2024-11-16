package com.example.parcial02agustinsantiaque.repositorio.models

data class Ciudad(
    val name: String,
    val lat: Float,
    val lon: Float,
    val country: String,
    val state: String? = null
)