package com.example.parcial02agustinsantiaque.repositorio.models

import kotlinx.serialization.Serializable

@Serializable
data class Ciudad(
    val name: String,
    val lat: Float,
    val lon: Float,
    val country: String,
    val state: String? = null
)