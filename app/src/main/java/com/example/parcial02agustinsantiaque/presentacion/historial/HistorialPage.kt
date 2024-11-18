package com.example.parcial02agustinsantiaque.presentacion.historial

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.parcial02agustinsantiaque.router.RouterImpl
import com.example.parcial02agustinsantiaque.utils.ManejoArchivoCiudades

@Composable
fun HistorialPage(
    navController: NavHostController,
    manejoArchivoCiudades: ManejoArchivoCiudades
) {
    val router = remember { RouterImpl(navController) }

    val viewModel: HistorialViewModel = viewModel(
        factory = HistorialViewModel
            .HistorialViewModelFactory(
                router = router,
                manejoArchivoCiudades = manejoArchivoCiudades
            )
    )

    HistorialView (
        estado = viewModel.estado,
        ejecutar = { intencion -> viewModel.ejecutar(intencion) },
    )
}