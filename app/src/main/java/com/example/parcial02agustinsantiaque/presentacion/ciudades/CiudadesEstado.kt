package com.example.parcial02agustinsantiaque.presentacion.ciudades

import com.example.parcial02agustinsantiaque.repositorio.models.Ciudad

sealed class CiudadesEstado {
    data object Vacio : CiudadesEstado()
    data object Cargando : CiudadesEstado()
    data class BusquedaTerminada(val ciudades: List<Ciudad>) : CiudadesEstado()
    data object Error : CiudadesEstado()
}