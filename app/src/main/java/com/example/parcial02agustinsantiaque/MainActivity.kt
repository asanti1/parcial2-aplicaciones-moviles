package com.example.parcial02agustinsantiaque

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.parcial02agustinsantiaque.presentacion.ciudades.CiudadesPage
import com.example.parcial02agustinsantiaque.presentacion.clima.ClimaPage
import com.example.parcial02agustinsantiaque.repositorio.models.Ciudad
import com.example.parcial02agustinsantiaque.ui.theme.Parcial02AgustinSantiñaqueTheme
import com.example.parcial02agustinsantiaque.utils.ManejoArchivoCiudades


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ciudades: List<Ciudad> = ManejoArchivoCiudades.getCiudadesDesdeJson(this)
        enableEdgeToEdge()
        setContent {
            Parcial02AgustinSantiñaqueTheme {

                val navController = rememberNavController()

                val startDestination = if (ciudades.isEmpty()) "ciudades" else "clima"

                NavHost(navController = navController, startDestination = startDestination)
                {
                    composable("ciudades") { CiudadesPage(navController, ciudades) }
                    composable("clima") { backStackEntry ->
                        val nombre = backStackEntry.arguments?.getString("nombre") ?: ""
                        val latitud = backStackEntry.arguments?.getString("latitud")?.toDouble() ?: 0.0
                        val longitud = backStackEntry.arguments?.getString("longitud")?.toDouble() ?: 0.0
                        val country = backStackEntry.arguments?.getString("country") ?: ""

                        ClimaPage(
                            navController = navController,
                            nombre = nombre,
                            latitud = latitud,
                            longitud = longitud,
                            country = country
                        )
                    }                }

            }
        }
    }
}