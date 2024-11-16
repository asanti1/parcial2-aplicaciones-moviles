package com.example.parcial02agustinsantiaque.repositorio.models

import kotlinx.serialization.Serializable

@Serializable
data class Clima(
    val dateTime: String,
    val tempMin: Double,
    val tempMax: Double,
    val probLLuvia: Double
)