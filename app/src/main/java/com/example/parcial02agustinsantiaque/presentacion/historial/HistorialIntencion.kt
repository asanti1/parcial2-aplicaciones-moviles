package com.example.parcial02agustinsantiaque.presentacion.historial

import com.example.parcial02agustinsantiaque.repositorio.models.Ciudad

sealed class HistorialIntencion {
    data object volverAtras : HistorialIntencion()
    data class navegarPorGeo(val ciudad: Ciudad) : HistorialIntencion()
}