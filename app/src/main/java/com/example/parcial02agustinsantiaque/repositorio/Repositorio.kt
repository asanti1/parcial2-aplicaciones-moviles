package com.example.parcial02agustinsantiaque.repositorio

import com.example.parcial02agustinsantiaque.repositorio.DTO.RequestCiudadPorNombreDTO

interface Repositorio {
    suspend fun getLatitudLongitud(busqueda: String): RequestCiudadPorNombreDTO?
}