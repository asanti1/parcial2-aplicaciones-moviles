package com.example.parcial02agustinsantiaque.presentacion.ciudades

import com.example.parcial02agustinsantiaque.repositorio.models.Ciudad

sealed class CiudadesIntencion {
    data class buscarLatLonByNombre(val textoBusqueda: String) : CiudadesIntencion()
    data class ciudadSeleccionada(val ciudad: Ciudad) : CiudadesIntencion()
    data object navegarPorGeo : CiudadesIntencion()
    data object irAHistorial : CiudadesIntencion()
}