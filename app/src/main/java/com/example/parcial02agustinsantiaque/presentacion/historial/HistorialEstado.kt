package com.example.parcial02agustinsantiaque.presentacion.historial

import com.example.parcial02agustinsantiaque.repositorio.models.Ciudad

sealed class HistorialEstado {
    data object Vacio : HistorialEstado()
    data object Cargando : HistorialEstado()
    data class Ok(val ciudades: List<Ciudad>) : HistorialEstado()
    data object Error : HistorialEstado()
}