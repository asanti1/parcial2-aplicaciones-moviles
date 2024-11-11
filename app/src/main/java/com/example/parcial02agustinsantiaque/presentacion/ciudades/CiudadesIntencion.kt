package com.example.parcial02agustinsantiaque.presentacion.ciudades

sealed class CiudadesIntencion {
    data class getLatitudLongitud(val textoBusqueda: String) : CiudadesIntencion()
}