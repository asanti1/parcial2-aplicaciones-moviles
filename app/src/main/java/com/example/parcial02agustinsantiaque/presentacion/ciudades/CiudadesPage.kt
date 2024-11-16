package com.example.parcial02agustinsantiaque.presentacion.ciudades

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.parcial02agustinsantiaque.repositorio.RepositorioImpl
import com.example.parcial02agustinsantiaque.router.RouterImpl

@Composable
fun CiudadesPage(navController: NavHostController) {
    val repositorio = remember { RepositorioImpl() }
    val router = remember { RouterImpl(navController) }

    val viewModel: CiudadesViewModel = viewModel(
        factory = CiudadesViewModel
            .CiudadesViewModelFactory(
                repositorio = repositorio,
                router = router
            )
    )

    CiudadesView(
        estado = viewModel.estado,
        ejecutar = { intencion -> viewModel.ejecutar(intencion) }
    )
}