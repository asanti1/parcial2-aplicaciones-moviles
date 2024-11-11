package com.example.parcial02agustinsantiaque.presentacion.ciudades

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.parcial02agustinsantiaque.repositorio.Repositorio
import kotlinx.coroutines.launch

class CiudadesViewModel(val repositorio: Repositorio) : ViewModel() {

    var estado by mutableStateOf<CiudadesEstado>(CiudadesEstado.Vacio)

    fun ejecutar(intencion: CiudadesIntencion) {
        when (intencion) {
            is CiudadesIntencion.getLatitudLongitud -> getLatitudLongitud(intencion.textoBusqueda)
        }
    }

    private fun getLatitudLongitud(textoBusqueda: String) {
        estado = CiudadesEstado.Cargando
        viewModelScope.launch {
            try {
                val info = repositorio.getLatitudLongitud(textoBusqueda)
                if (info != null) {
                    estado = CiudadesEstado.Ok(info)
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
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CiudadesViewModel::class.java)) {
                return CiudadesViewModel(repositorio) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}


