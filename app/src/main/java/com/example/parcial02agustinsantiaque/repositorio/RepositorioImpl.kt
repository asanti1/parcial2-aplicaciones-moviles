package com.example.parcial02agustinsantiaque.repositorio

import ResponseClimaLatLon
import android.util.Log
import com.example.parcial02agustinsantiaque.repositorio.DTO.ResponseCiudadPorNombreDTO
import com.example.parcial02agustinsantiaque.repositorio.models.Ciudad
import com.example.parcial02agustinsantiaque.repositorio.models.Clima
import com.example.parcial02agustinsantiaque.repositorio.models.ClimaActual
import com.istea.appdelclima.repository.modelos.ResponseClimaActualDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

private const val BASE_URL_CIUDAD = "https://api.openweathermap.org/geo/1.0/direct"
private const val BASE_URL_CLIMA = "https://api.openweathermap.org/data/2.5/forecast"
private const val BASE_URL_CLIMA_ACTUAL = "https://api.openweathermap.org/data/2.5/weather"
private const val LANGUAGE = "es"
private const val API_KEY = "4c0e9513e459d4c28008e6a910b2356a"
private const val UNITS = "metric"

class RepositorioImpl : Repositorio {
    @OptIn(ExperimentalSerializationApi::class)
    private val cliente = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                explicitNulls = false
            })
        }
    }

    override suspend fun buscarLatLonByNombre(busqueda: String): List<Ciudad>? {
        return try {
            val respuesta: List<ResponseCiudadPorNombreDTO> = cliente.get(BASE_URL_CIUDAD) {
                parameter("q", busqueda)
                parameter("lang", LANGUAGE)
                parameter("limit", 5)
                parameter("appid", API_KEY)
            }.body()

            respuesta.map { item ->
                Ciudad(
                    name = item.name,
                    lat = item.lat,
                    lon = item.lon,
                    country = item.country,
                    state = item.state
                )
            }
        } catch (e: Exception) {
            Log.e("Clima", "Error fetching weather data", e)
            e.printStackTrace()
            null
        }
    }

    override suspend fun getClimaCincoDias(lat: String, lon: String): List<Clima> {
        return try {
            val respuestaCompleta: ResponseClimaLatLon = cliente.get(BASE_URL_CLIMA) {
                parameter("lat", lat)
                parameter("lon", lon)
                parameter("units", UNITS)
                parameter("lang", LANGUAGE)
                parameter("appid", API_KEY)
            }.body()


            respuestaCompleta.list.map { forecastItem ->
                Clima(
                    dateTime = forecastItem.dt_txt,
                    tempMin = forecastItem.main.temp_min,
                    tempMax = forecastItem.main.temp_max,
                    probLLuvia = forecastItem.pop,
                )
            }
        } catch (e: Exception) {
            Log.e("Clima", "Error fetching weather data", e)
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun getClimaActual(lat: String, lon: String): ClimaActual {
        return try {
            val respuesta: ResponseClimaActualDTO = cliente.get(BASE_URL_CLIMA_ACTUAL) {
                parameter("lat", lat)
                parameter("lon", lon)
                parameter("units", UNITS)
                parameter("lang", LANGUAGE)
                parameter("appid", API_KEY)
            }.body()

            ClimaActual(
                temperatura = respuesta.main.temp,
                sensTermica = respuesta.main.feels_like,
                clima = respuesta.weather[0].description,
                nubosidad = respuesta.clouds.all
            )
        } catch (e: Exception) {
            Log.e("Clima", "Error fetching weather data", e)
            throw e
        }
    }
}