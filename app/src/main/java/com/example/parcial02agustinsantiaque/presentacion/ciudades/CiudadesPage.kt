package com.example.parcial02agustinsantiaque.presentacion.ciudades

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.parcial02agustinsantiaque.repositorio.OpenWeatherMapRepositorio

@Composable
fun CiudadesPage() {
    val viewModel : CiudadesViewModel = viewModel(
        factory = CiudadesViewModel.CiudadesViewModelFactory(repositorio = OpenWeatherMapRepositorio() )
    )

    CiudadesView(estado = viewModel.estado) {
        viewModel.ejecutar(it)
    }
}