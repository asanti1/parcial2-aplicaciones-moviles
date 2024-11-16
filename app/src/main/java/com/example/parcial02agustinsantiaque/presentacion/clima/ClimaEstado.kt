package com.example.parcial02agustinsantiaque.presentacion.clima


sealed class ClimaEstado {
    data object Vacio : ClimaEstado()
    data object Cargando : ClimaEstado()
    data class Ok(val climaYPronostico: ClimaYPronostico) : ClimaEstado()
    data object Error : ClimaEstado()
}