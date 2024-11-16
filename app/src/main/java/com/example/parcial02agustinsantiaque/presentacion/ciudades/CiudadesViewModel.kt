package com.example.parcial02agustinsantiaque.presentacion.ciudades

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.parcial02agustinsantiaque.repositorio.Repositorio
import com.example.parcial02agustinsantiaque.repositorio.models.Ciudad
import com.example.parcial02agustinsantiaque.router.Router
import com.example.parcial02agustinsantiaque.router.Rutas
import kotlinx.coroutines.launch

class CiudadesViewModel(
    val repositorio: Repositorio,
    val router: Router
) : ViewModel() {

    var estado by mutableStateOf<CiudadesEstado>(CiudadesEstado.Vacio)

    fun ejecutar(intencion: CiudadesIntencion) {
        when (intencion) {
            is CiudadesIntencion.getLatitudLongitud -> getLatitudLongitud(intencion.textoBusqueda)
            is CiudadesIntencion.ciudadSeleccionada -> ciudadSeleccionada(intencion.ciudad)
        }
    }

    fun ciudadSeleccionada(ciudad: Ciudad) {
        router.navegar(Rutas.Clima(ciudad.lat.toString(), ciudad.lon.toString()))
    }

    private fun getLatitudLongitud(textoBusqueda: String) {
        estado = CiudadesEstado.Cargando
        viewModelScope.launch {
            try {
                val info = repositorio.buscarLatLonByNombre(textoBusqueda)
                if (info != null) {
                    println("Datos recibidos: $info")
                    estado = CiudadesEstado.BusquedaTerminada(info)
                } else {
                    estado = CiudadesEstado.Error
                }
            } catch (exception: Exception) {
                estado = CiudadesEstado.Error
            }

        }
    }

    class CiudadesViewModelFactory(
        private val repositorio: Repositorio,
        private val router: Router
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CiudadesViewModel::class.java)) {
                return CiudadesViewModel(repositorio, router) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}


