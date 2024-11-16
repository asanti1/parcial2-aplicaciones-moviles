package com.example.parcial02agustinsantiaque.repositorio.DTO

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocalNames(
    @SerialName("es")
    val spanishName: String? = null
)

@Serializable
data class ResponseCiudadPorNombreDTO(
    val name: String,
    val local_names: LocalNames? = null,
    val lat: Float,
    val lon: Float,
    val country: String,
    val state: String? = null
)
