package com.example.parcial02agustinsantiaque

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.parcial02agustinsantiaque.presentacion.ciudades.CiudadesPage
import com.example.parcial02agustinsantiaque.presentacion.clima.ClimaPage
import com.example.parcial02agustinsantiaque.router.Rutas
import com.example.parcial02agustinsantiaque.ui.theme.Parcial02AgustinSantiñaqueTheme
import com.example.parcial02agustinsantiaque.utils.ManejoArchivoCiudades

class MainActivity : ComponentActivity() {
    private lateinit var manejoArchivoCiudades: ManejoArchivoCiudades

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Parcial02AgustinSantiñaqueTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = Rutas.Ciudades.id) {
                    composable(route = Rutas.Ciudades.id) { CiudadesPage(navController = navController) }
                    composable(route = "clima?lat={lat}&lon={lon}",
                        arguments = listOf(
                            navArgument("lat") { type = NavType.StringType },
                            navArgument("lon") { type = NavType.StringType }
                        ))
                    { backStackEntry ->
                        ClimaPage(navController = navController, backStackEntry)
                    }
                }
            }

        }
    }
}