package com.example.parcial02agustinsantiaque.repositorio

import com.example.parcial02agustinsantiaque.repositorio.models.Ciudad
import com.example.parcial02agustinsantiaque.repositorio.models.Clima
import com.example.parcial02agustinsantiaque.repositorio.models.ClimaActual

interface Repositorio {
    suspend fun buscarLatLonByNombre(busqueda: String): List<Ciudad>?
    suspend fun getClimaCincoDias(lat: String, lon: String): List<Clima>
    suspend fun getClimaActual(lat: String, lon: String): ClimaActual
}