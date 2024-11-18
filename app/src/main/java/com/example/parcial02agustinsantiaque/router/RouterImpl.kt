package com.example.parcial02agustinsantiaque.router

import androidx.navigation.NavHostController

class RouterImpl(private val navHostController: NavHostController) : Router {

    override fun navegar(ruta: Rutas) {
        when (ruta) {
            Rutas.Ciudades -> navHostController.navigate(ruta.id)
            is Rutas.Clima -> {
                val rutaClima = "${ruta.id}?lat=${ruta.lat}&lon=${ruta.lon}"
                navHostController.navigate(rutaClima)
            }
        }
    }

    override fun regresar() {
        navHostController.popBackStack()
    }
}