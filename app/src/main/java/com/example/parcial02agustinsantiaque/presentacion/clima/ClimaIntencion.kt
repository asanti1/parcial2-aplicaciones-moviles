package com.example.parcial02agustinsantiaque.presentacion.clima

sealed class ClimaIntencion {
    data class getLatitudLongitud(val textoBusqueda: String) : ClimaIntencion()
}