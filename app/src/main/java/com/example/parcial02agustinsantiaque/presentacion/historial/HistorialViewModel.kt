package com.example.parcial02agustinsantiaque.presentacion.historial

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.parcial02agustinsantiaque.repositorio.models.Ciudad
import com.example.parcial02agustinsantiaque.router.Router
import com.example.parcial02agustinsantiaque.router.Rutas
import com.example.parcial02agustinsantiaque.utils.ManejoArchivoCiudades

class HistorialViewModel(
    private val router: Router,
    private val manejoArchivoCiudades: ManejoArchivoCiudades
) : ViewModel() {
    private var ciudades: List<Ciudad> = emptyList()
    var estado by mutableStateOf<HistorialEstado>(HistorialEstado.Vacio)

    init {
        traerCiudadesDeArchivo()
    }

    fun ejecutar(intencion: HistorialIntencion) {
        when (intencion) {
            is HistorialIntencion.volverAtras -> volverAtras()
            is HistorialIntencion.navegarPorGeo -> navegarPorGeo(intencion.ciudad)
        }
    }

    private fun navegarPorGeo(ciudad: Ciudad) {
        manejoArchivoCiudades.agregarOReubicarCiudadEnJson(ciudad)
        router.navegar(Rutas.Clima(ciudad.lat.toString(), ciudad.lon.toString()))
    }

    private fun volverAtras() {
        router.regresar()
    }

    private fun traerCiudadesDeArchivo() {
        estado = HistorialEstado.Cargando
        ciudades = manejoArchivoCiudades.getCiudadesDesdeJson()
        estado = if (ciudades.isEmpty()) {
            HistorialEstado.Vacio
        } else {
            HistorialEstado.Ok(ciudades)
        }
    }

    class HistorialViewModelFactory(
        private val router: Router,
        private val manejoArchivoCiudades: ManejoArchivoCiudades
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HistorialViewModel::class.java)) {
                return HistorialViewModel(router, manejoArchivoCiudades) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}


