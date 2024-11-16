package com.example.parcial02agustinsantiaque.utils

import android.content.Context
import com.example.parcial02agustinsantiaque.repositorio.models.Ciudad
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class ManejoArchivoCiudades  {
    private val NOMBRE_ARCHIVO = "ciudades.json"
    private lateinit var context: Context

    fun initialize(context: Context) {
        this.context = context.applicationContext
    }
    fun getCiudadesDesdeJson(): List<Ciudad> {
        return try {
            val jsonString =
                context.openFileInput(NOMBRE_ARCHIVO).bufferedReader().use { it.readText() }
            Json.decodeFromString(jsonString)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    private fun guardarCiudadesComoJson(listaCiudades: List<Ciudad>) {
        try {
            val jsonString = Json.encodeToString(listaCiudades)
            context.openFileOutput(NOMBRE_ARCHIVO, Context.MODE_PRIVATE).use { output ->
                output.write(jsonString.toByteArray())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun agregarCiudadAJson(nuevaCiudad: Ciudad) {
        val listaCiudadesActual = getCiudadesDesdeJson().toMutableList()
        listaCiudadesActual.add(0, nuevaCiudad)

        guardarCiudadesComoJson(listaCiudadesActual)
    }
}