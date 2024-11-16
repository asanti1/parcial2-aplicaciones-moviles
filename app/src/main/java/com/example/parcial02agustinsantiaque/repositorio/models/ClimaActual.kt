package com.example.parcial02agustinsantiaque.repositorio.models

import kotlinx.serialization.Serializable

@Serializable
data class ClimaActual (
    val temperatura: Double,
    val sensTermica : Double,
    val clima: String? = "No Disponible",
    val nubosidad : Long
)