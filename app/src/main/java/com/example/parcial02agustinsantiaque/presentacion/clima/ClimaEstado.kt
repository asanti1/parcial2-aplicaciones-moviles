package com.example.parcial02agustinsantiaque.presentacion.clima

import com.example.parcial02agustinsantiaque.repositorio.DTO.RequestCiudadPorNombreDTO

sealed class ClimaEstado {
    data object Vacio: ClimaEstado()
    data object Cargando: ClimaEstado()
    data class Ok(val info: RequestCiudadPorNombreDTO) : ClimaEstado()
    data object Error: ClimaEstado()
}