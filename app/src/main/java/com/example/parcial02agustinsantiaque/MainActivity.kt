package com.example.parcial02agustinsantiaque
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.parcial02agustinsantiaque.presentacion.ciudades.CiudadesPage
import com.example.parcial02agustinsantiaque.ui.theme.Parcial02AgustinSantiñaqueTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Parcial02AgustinSantiñaqueTheme {
                CiudadesPage()
            }
        }
    }
}