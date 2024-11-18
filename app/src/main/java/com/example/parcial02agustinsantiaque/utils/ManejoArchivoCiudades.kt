package com.example.parcial02agustinsantiaque.utils

import android.content.Context
import com.example.parcial02agustinsantiaque.repositorio.models.Ciudad
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.FileNotFoundException


class ManejoArchivoCiudades(context: Context) {
    private val NOMBRE_ARCHIVO = "ciudades.json"
    private val context: Context = context.applicationContext

    fun getCiudadesDesdeJson(): List<Ciudad> {
        return try {
            val jsonString = context.openFileInput(NOMBRE_ARCHIVO).bufferedReader().use { it.readText() }
            Json.decodeFromString<List<Ciudad>>(jsonString)
        } catch (e: FileNotFoundException) {
            val listaCiudadesInicial = emptyList<Ciudad>()
            guardarCiudadesComoJson(listaCiudadesInicial)
            listaCiudadesInicial
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

    fun agregarOReubicarCiudadEnJson(nuevaCiudad: Ciudad) {
        val listaCiudadesActual = getCiudadesDesdeJson().toMutableList()
        val index = listaCiudadesActual.indexOfFirst { it.name == nuevaCiudad.name }
        if (index != -1) {
            listaCiudadesActual.removeAt(index)
        }
        listaCiudadesActual.add(0, nuevaCiudad)

        guardarCiudadesComoJson(listaCiudadesActual)
    }
}