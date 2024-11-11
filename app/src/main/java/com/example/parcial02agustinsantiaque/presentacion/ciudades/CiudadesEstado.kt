package com.example.parcial02agustinsantiaque.presentacion.ciudades

import com.example.parcial02agustinsantiaque.repositorio.DTO.RequestCiudadPorNombreDTO

sealed class CiudadesEstado {
    data object Vacio: CiudadesEstado()
    data object Cargando: CiudadesEstado()
    data class Ok(val info: RequestCiudadPorNombreDTO) : CiudadesEstado()
    data object Error: CiudadesEstado()
}