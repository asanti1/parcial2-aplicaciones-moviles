package com.example.parcial02agustinsantiaque.presentacion.clima

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.parcial02agustinsantiaque.repositorio.Repositorio
import kotlinx.coroutines.launch

class ClimaViewModel(val repositorio: Repositorio) : ViewModel() {

    var estado by mutableStateOf<ClimaEstado>(ClimaEstado.Vacio)

    fun ejecutar(intencion: ClimaIntencion) {
        when (intencion) {
            is ClimaIntencion.getLatitudLongitud -> getLatitudLongitud(intencion.textoBusqueda)
        }
    }

    private fun getLatitudLongitud(textoBusqueda: String) {
        estado = ClimaEstado.Cargando
        viewModelScope.launch {
            try {
                val info = repositorio.getLatitudLongitud(textoBusqueda)
                if (info != null) {
                    estado = ClimaEstado.Ok(info)
                } else {
                    estado = ClimaEstado.Error
                }
            } catch (exception: Exception) {
                estado = ClimaEstado.Error
            }

        }
    }


    class ClimaViewModelFactory(
        private val repositorio: Repositorio,
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ClimaViewModel::class.java)) {
                return ClimaViewModel(repositorio) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}


