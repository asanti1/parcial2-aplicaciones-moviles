package com.example.parcial02agustinsantiaque.presentacion.ciudades

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.parcial02agustinsantiaque.repositorio.OpenWeatherMapRepositorio
import com.example.parcial02agustinsantiaque.repositorio.models.Ciudad

@Composable
fun CiudadesPage(navController: NavController, ciudades: List<Ciudad> = emptyList()) {
    val viewModel: CiudadesViewModel = viewModel(
        factory = CiudadesViewModel
            .CiudadesViewModelFactory(
                repositorio = OpenWeatherMapRepositorio()
            )
    )

    CiudadesView(
        estado = viewModel.estado,
        ciudades = ciudades,
        navController = navController,
        ejecutar = { viewModel.ejecutar(it) }
    )
}