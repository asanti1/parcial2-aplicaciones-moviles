package com.example.parcial02agustinsantiaque.presentacion.ciudades

import android.annotation.SuppressLint
import android.util.Log
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
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class CiudadesViewModel(
    private val repositorio: Repositorio,
    private val router: Router,
    private val fusedLocationClient: FusedLocationProviderClient,
) : ViewModel() {

    var estado by mutableStateOf<CiudadesEstado>(CiudadesEstado.Vacio)

    fun ejecutar(intencion: CiudadesIntencion) {
        when (intencion) {
            is CiudadesIntencion.buscarLatLonByNombre -> buscarLatLonByNombre(intencion.textoBusqueda)
            is CiudadesIntencion.ciudadSeleccionada -> ciudadSeleccionada(intencion.ciudad)
            is CiudadesIntencion.navegarPorGeo -> navegarPorGeo()
        }
    }

    @SuppressLint("MissingPermission")
    private fun navegarPorGeo() {
        viewModelScope.launch {
            try {
                val location = fusedLocationClient.lastLocation.await()
                if (location != null) {
                    val latitud = location.latitude
                    val longitud = location.longitude
                    router.navegar(Rutas.Clima(latitud.toString(), longitud.toString()))

                } else {
                    estado = CiudadesEstado.Error
                }
            } catch (e: Exception) {

                estado = CiudadesEstado.Error
            }
        }
    }


    private fun ciudadSeleccionada(ciudad: Ciudad) {
        router.navegar(Rutas.Clima(ciudad.lat.toString(), ciudad.lon.toString()))
    }

    private fun buscarLatLonByNombre(textoBusqueda: String) {
        estado = CiudadesEstado.Cargando
        viewModelScope.launch {
            estado = try {
                val info = repositorio.buscarLatLonByNombre(textoBusqueda)
                Log.d("getLangLong", info.toString())
                if (info?.isNotEmpty() == true) {
                    CiudadesEstado.BusquedaTerminada(info)
                } else {
                    CiudadesEstado.Error
                }
            } catch (exception: Exception) {
                CiudadesEstado.Error
            }

        }
    }

    class CiudadesViewModelFactory(
        private val repositorio: Repositorio,
        private val router: Router,
        private val fusedLocationClient: FusedLocationProviderClient
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CiudadesViewModel::class.java)) {
                return CiudadesViewModel(
                    repositorio,
                    router,
                    fusedLocationClient,
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}


