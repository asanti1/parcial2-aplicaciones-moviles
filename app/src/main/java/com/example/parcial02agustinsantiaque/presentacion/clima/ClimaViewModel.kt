package com.example.parcial02agustinsantiaque.presentacion.clima

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavBackStackEntry
import com.example.parcial02agustinsantiaque.repositorio.Repositorio
import com.example.parcial02agustinsantiaque.repositorio.models.Clima
import com.example.parcial02agustinsantiaque.repositorio.models.ClimaActual
import com.example.parcial02agustinsantiaque.router.Router
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class ClimaYPronostico(
    val actual: ClimaActual,
    val pronostico: List<Clima>
) {
    companion object {
        fun fromClimaActualAndProcesado(
            climaActual: ClimaActual,
            climaProcesado: Map<String, Triple<Double, Double, Double>>
        ): ClimaYPronostico {
            val pronostico = climaProcesado.map { (fecha, datos) ->
                Clima(
                    dateTime = fecha,
                    tempMin = datos.first,
                    tempMax = datos.second,
                    probLLuvia = datos.third
                )
            }
            return ClimaYPronostico(climaActual, pronostico)
        }
    }
}


class ClimaViewModel(
    val router: Router,
    val backStackEntry: NavBackStackEntry,
    val repositorio: Repositorio,
) : ViewModel() {

    var estado by mutableStateOf<ClimaEstado>(ClimaEstado.Vacio)

    fun ejecutar(intencion: ClimaIntencion) {
        if (intencion == ClimaIntencion.volverAtras) {
            volverAtras()
        }
    }

    init {
        cargarClimaYPronostico()
    }

    fun volverAtras() {
        router.regresar()
    }

    fun cargarClimaYPronostico() {
        viewModelScope.launch {
            estado = ClimaEstado.Cargando
            Log.d("ClimaViewModel", "Estado cambiado a Cargando")
            try {
                val climaActual = repositorio.getClimaActual(
                    lat = backStackEntry.arguments!!.getString("lat")!!,
                    lon = backStackEntry.arguments!!.getString("lon")!!
                )
                val pronosticoData = repositorio.getClimaCincoDias(
                    lat = backStackEntry.arguments!!.getString("lat")!!,
                    lon = backStackEntry.arguments!!.getString("lon")!!
                )
                val pronosticoProcesado = procesarDatosClima(pronosticoData)
                val climaIntegrado =
                    ClimaYPronostico.fromClimaActualAndProcesado(climaActual, pronosticoProcesado)
                estado = ClimaEstado.Ok(climaIntegrado)
            } catch (e: Exception) {
                estado = ClimaEstado.Error
            }
        }
    }

    @SuppressLint("NewApi")
    fun procesarDatosClima(data: List<Clima>): Map<String, Triple<Double, Double, Double>> {
        val formatterEntrada = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formatterSalida = DateTimeFormatter.ofPattern("dd/MM")
        val hoy = LocalDate.now().format(formatterEntrada)

        val datosFiltrados = data.filter {
            val datePart = it.dateTime.split(" ")[0]
            LocalDate.parse(datePart, formatterEntrada)
                .format(formatterSalida) != hoy.split("-")[1] + "/" + hoy.split("-")[2]
        }

        val climaPorDia = datosFiltrados.groupBy {
            LocalDate.parse(it.dateTime.split(" ")[0], formatterEntrada).format(formatterSalida)
        }.mapValues { (_, dataDiaria) ->
            Triple(
                dataDiaria.map { it.tempMin }.average(),
                dataDiaria.map { it.tempMax }.average(),
                dataDiaria.map { it.probLLuvia }.average()
            )
        }

        val keys = climaPorDia.keys.toList()
        val primerosCuatroDias = keys.take(4)

        return climaPorDia.filterKeys { primerosCuatroDias.contains(it) }
    }
    /*   fun procesarDatosClima(data: List<Clima>): Map<String, Triple<Double, Double, Double>> {
           val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
           val hoy = LocalDateTime.now().format(formatter).split(" ")[0]
           val formatterEntrada = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
           val formatterSalida = DateTimeFormatter.ofPattern("dd/MM")
           val datosFiltrados = data.filter {
               it.dateTime.split(" ")[0] != hoy
           }

           val climaPorDia = datosFiltrados.groupBy { it.dateTime.split(" ")[0] }
               .mapValues { (_, dataDiaria) ->
                   Triple(
                       dataDiaria.map { it.tempMin }.average(),
                       dataDiaria.map { it.tempMax }.average(),
                       dataDiaria.map { it.probLLuvia }.average()
                   )
               }
           val keys = climaPorDia.keys.toList()
           val primerosCuatroDias = keys.take(4)

           return climaPorDia.filterKeys { primerosCuatroDias.contains(it) }
       }*/


    class ClimaViewModelFactory(
        private val router: Router,
        private val backStackEntry: NavBackStackEntry,
        private val repositorio: Repositorio
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ClimaViewModel::class.java)) {
                return ClimaViewModel(router, backStackEntry, repositorio) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }

    }
}


