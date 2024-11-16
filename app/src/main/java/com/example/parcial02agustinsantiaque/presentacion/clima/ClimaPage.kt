package com.example.parcial02agustinsantiaque.presentacion.clima

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.example.parcial02agustinsantiaque.repositorio.RepositorioImpl
import com.example.parcial02agustinsantiaque.router.RouterImpl

@Composable
fun ClimaPage(
    navController: NavHostController,
    backStackEntry: NavBackStackEntry,
) {
    val repositorio = remember { RepositorioImpl() }
    val router = remember { RouterImpl(navController) }


    val viewModel: ClimaViewModel = viewModel(
        factory = ClimaViewModel.ClimaViewModelFactory(
            router = router,
            backStackEntry = backStackEntry,
            repositorio = repositorio
        )
    )

    ClimaView(
        estado = viewModel.estado,
        ejecutar = { intencion -> viewModel.ejecutar(intencion) }
    )
}