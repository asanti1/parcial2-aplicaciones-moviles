package com.example.parcial02agustinsantiaque.presentacion.historial

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.example.parcial02agustinsantiaque.R
import com.example.parcial02agustinsantiaque.repositorio.models.Ciudad
import com.example.parcial02agustinsantiaque.ui.theme.DarkPrimary
import com.example.parcial02agustinsantiaque.ui.theme.DarkTertiary

@Composable
fun HistorialView(
    estado: HistorialEstado,
    ejecutar: (HistorialIntencion) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = DarkPrimary)
            .padding(top = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { ejecutar(HistorialIntencion.volverAtras) }) {
                Text("Volver")
            }
            when (estado) {
                is HistorialEstado.Cargando -> Cargando()
                is HistorialEstado.Error -> Error()
                is HistorialEstado.Ok -> Ok(estado.ciudades, ejecutar)
                is HistorialEstado.Vacio -> Vacio()
            }
        }
    }

}


@Composable
fun Ok(ciudades: List<Ciudad>, ejecutar: (HistorialIntencion) -> Unit) {
    ciudades.forEach { ciudad ->
        CiudadCard(ciudad) {
            ejecutar(HistorialIntencion.navegarPorGeo(ciudad))
        }
    }
}

@Composable
fun CiudadCard(ciudad: Ciudad, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(DarkTertiary),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = DarkTertiary,
            contentColor = Color.White
        ),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = "${ciudad.name}, ${ciudad.state}, ${ciudad.country}",
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun Cargando() {
    val imageLoader = ImageLoader.Builder(LocalContext.current).components {
        if (Build.VERSION.SDK_INT >= 28) {
            add(ImageDecoderDecoder.Factory())
        } else {
            add(GifDecoder.Factory())
        }
    }.build()
    Image(
        painter = rememberAsyncImagePainter(R.drawable.spinner, imageLoader),
        contentDescription = "Spinner de carga",
        modifier = Modifier
            .fillMaxSize()
            .background(DarkPrimary)
    )
}

@Composable
fun Error() {
}

@Composable
fun Vacio() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkPrimary),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Para tener algo en el historial primero debes buscar una ciudad",
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}