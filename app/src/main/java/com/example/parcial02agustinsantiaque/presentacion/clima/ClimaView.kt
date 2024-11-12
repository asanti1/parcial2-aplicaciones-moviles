package com.example.parcial02agustinsantiaque.presentacion.clima

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.example.parcial02agustinsantiaque.R
import com.example.parcial02agustinsantiaque.repositorio.models.Ciudad

@Composable
fun ClimaView(
    modifier: Modifier = Modifier,
    estado: ClimaEstado,
    ejecutar: (ClimaIntencion) -> Unit,
    ciudad: Ciudad,
    navController: NavController
) {
    Scaffold { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            when (estado) {
                is ClimaEstado.Cargando -> Cargando()
                is ClimaEstado.Error -> Error()
                is ClimaEstado.Ok -> Estamos(ciudad)
                is ClimaEstado.Vacio -> Text("Vacio")
            }
        }
    }
}

@Composable
fun Cargando() {
    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()
    Image(
        painter = rememberAsyncImagePainter(R.drawable.spinner, imageLoader),
        contentDescription = "Spinner de carga",
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun Error() {
    Spacer(modifier = Modifier.padding(top = 20.dp))
    Text("No encontramos ciudad con ese nombre")
}

@Composable
fun Estamos(ciudad: Ciudad) {
    Column {
        Text(
            "Lat: ${ciudad.lat}",
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            "Lon: ${ciudad.lon}",
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            "Nombre: ${ciudad.name}",
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            "Pais: ${ciudad.country}",
            style = MaterialTheme.typography.titleLarge
        )
    }

}