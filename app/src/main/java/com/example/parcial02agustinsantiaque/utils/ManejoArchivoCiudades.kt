package com.example.parcial02agustinsantiaque.utils

import android.content.Context
import com.example.parcial02agustinsantiaque.repositorio.models.Ciudad
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object ManejoArchivoCiudades {
    private const val NOMBRE_ARCHIVO = "ciudades.json"

    fun getCiudadesDesdeJson(context: Context): List<Ciudad> {
        return try {
            val jsonString =
                context.openFileInput(NOMBRE_ARCHIVO).bufferedReader().use { it.readText() }
            Json.decodeFromString(jsonString)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    private fun guardarCiudadesComoJson(context: Context ,listaCiudades: List<Ciudad>) {
        try {
            val jsonString = Json.encodeToString(listaCiudades)
            context.openFileOutput(NOMBRE_ARCHIVO, Context.MODE_PRIVATE).use { output ->
                output.write(jsonString.toByteArray())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun agregarCiudadAJson(context: Context, nuevaCiudad: Ciudad) {
        val listaCiudadesActual = getCiudadesDesdeJson(context).toMutableList()
        listaCiudadesActual.add(0, nuevaCiudad)

        guardarCiudadesComoJson(context, listaCiudadesActual)
    }
}