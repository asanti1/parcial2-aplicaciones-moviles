package com.example.parcial02agustinsantiaque.router

sealed class Rutas(val id: String) {
    data object Ciudades : Rutas("ciudades")
    data object Historial : Rutas("historial")
    data class Clima(val lat: String, val lon: String) : Rutas("clima")
}