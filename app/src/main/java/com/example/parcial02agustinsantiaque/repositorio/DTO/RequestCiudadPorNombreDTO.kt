package com.example.parcial02agustinsantiaque.repositorio.DTO

import kotlinx.serialization.Serializable

@Serializable
data class RequestCiudadPorNombreDTO(
    val name: String,
    val local_names: Map<String, String>? = null,
    val lat: Double,
    val lon: Double,
    val country: String,
    val state: String
)