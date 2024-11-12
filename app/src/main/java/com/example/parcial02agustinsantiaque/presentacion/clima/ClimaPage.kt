package com.example.parcial02agustinsantiaque.presentacion.clima

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.parcial02agustinsantiaque.repositorio.OpenWeatherMapRepositorio
import com.example.parcial02agustinsantiaque.repositorio.models.Ciudad

@Composable
fun ClimaPage(
    navController: NavHostController,
    nombre: String,
    latitud: Double,
    longitud: Double,
    country: String
) {
    val viewModel: ClimaViewModel = viewModel(
        factory = ClimaViewModel.ClimaViewModelFactory(repositorio = OpenWeatherMapRepositorio())
    )

    ClimaView(
        estado = viewModel.estado,
        navController = navController,
        ciudad = Ciudad(
            name = nombre,
            lat = latitud,
            lon = longitud,
            country = country,
        ),
        ejecutar = { viewModel.ejecutar(it) }
    )
}