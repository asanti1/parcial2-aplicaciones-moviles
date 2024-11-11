package com.example.parcial02agustinsantiaque.repositorio

import com.example.parcial02agustinsantiaque.repositorio.DTO.RequestCiudadPorNombreDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/* *
 * API KEY = 4c0e9513e459d4c28008e6a910b2356a
 * PARAMETROS:
 * URL obtener lat y long a partir de ciudad= https://api.openweathermap.org/geo/1.0/direct?q=
 * URL forecast 5 dias = https://api.openweathermap.org/data/2.5/forecast?
 * appid = API KEY
 * lang = lenguaje
 * lat = latitud
 * long = longitud
 * q= Ciudad
 * units = unidades (metric)
 * limit (para geolocalizacion limita los resultados maximo 5)
 * */
class OpenWeatherMapRepositorio : Repositorio {
    private val BASE_URL_CIUDAD = "https://api.openweathermap.org/geo/1.0/direct"
    private val BASE_URL_CLIMA = "https://api.openweathermap.org/data/2.5/forecast"
    private val LANGUAGE = "es"
    private val API_KEY = "TU API KEY AQUI"
    private val UNITS = "metric"

    private val cliente = HttpClient() {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    override suspend fun getLatitudLongitud(busqueda: String): RequestCiudadPorNombreDTO? {
        return try {
            val respuesta: List<RequestCiudadPorNombreDTO> = cliente.get(BASE_URL_CIUDAD) {
                parameter("q", busqueda)
                parameter("lang", LANGUAGE)
                parameter("limit", 1)
                parameter("appid", API_KEY)
            }.body()

            respuesta.firstOrNull()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}