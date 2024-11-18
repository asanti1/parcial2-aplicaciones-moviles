package com.example.parcial02agustinsantiaque

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import android.Manifest
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.PermissionChecker

class MainActivity : ComponentActivity() {
    private lateinit var manejoArchivoCiudades: ManejoArchivoCiudades
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var locationPermissionDecided by mutableStateOf(false)
    private var hasLocationPermission by mutableStateOf(false)

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        checkAndRequestLocationPermission()
        checkLocationPermission()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Parcial02AgustinSantiñaqueTheme {
                val navController = rememberNavController()

                if (locationPermissionDecided) {
                    NavHost(navController = navController, startDestination = Rutas.Ciudades.id) {
                        composable(route = Rutas.Ciudades.id)
                        {
                            CiudadesPage(
                                navController = navController,
                                fusedLocationClient = fusedLocationClient,
                                hasLocationPermission = hasLocationPermission
                            )
                        }
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

    private fun checkAndRequestLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            locationPermissionDecided = true
            hasLocationPermission = true
        }
    }

    private fun checkLocationPermission() {
        val context = application.applicationContext // Uso correcto de 'application'
        val hasPermission = PermissionChecker.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PermissionChecker.PERMISSION_GRANTED ||
                PermissionChecker.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PermissionChecker.PERMISSION_GRANTED
        hasLocationPermission = hasPermission
    }

    @Suppress("DEPRECATION")
    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            locationPermissionDecided = true
            hasLocationPermission = grantResults.isNotEmpty() &&
                    (grantResults[0] == PackageManager.PERMISSION_GRANTED ||
                            grantResults.getOrNull(1) == PackageManager.PERMISSION_GRANTED)
        }
    }
}