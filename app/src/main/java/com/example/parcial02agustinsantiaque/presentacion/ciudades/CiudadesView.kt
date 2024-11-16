package com.example.parcial02agustinsantiaque.presentacion.ciudades

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.example.parcial02agustinsantiaque.R
import com.example.parcial02agustinsantiaque.repositorio.models.Ciudad
import com.example.parcial02agustinsantiaque.ui.theme.DarkPrimary
import com.example.parcial02agustinsantiaque.ui.theme.DarkSecondary
import com.example.parcial02agustinsantiaque.ui.theme.DarkTertiary

@Composable
fun CiudadesView(
    estado: CiudadesEstado,
    ejecutar: (CiudadesIntencion) -> Unit,
) {
    var textoBusqueda by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DarkSecondary, shape = RoundedCornerShape(16.dp))
                    .padding(16.dp),
                verticalAlignment = CenterVertically
            ) {
                TextField(
                    value = textoBusqueda,
                    onValueChange = { textoBusqueda = it },
                    modifier = Modifier
                        .weight(1f)
                        .background(DarkSecondary, shape = RoundedCornerShape(16.dp))
                        .padding(8.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(onSearch = {
                        keyboardController?.hide()
                        ejecutar(CiudadesIntencion.getLatitudLongitud(textoBusqueda))
                    }),
                    placeholder = { Text("Buscar ciudad", color = Color.LightGray) }
                )
                IconButton(
                    onClick = { /* TODO: Implementar la acción para usar la geolocalización */ },
                    modifier = Modifier
                        .size(60.dp)
                        .padding(start = 8.dp)
                ) {
                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = "Usar mi localización",
                        tint = Color.White,
                        modifier = Modifier.size(48.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            when (estado) {
                is CiudadesEstado.Cargando -> Cargando()
                is CiudadesEstado.Error -> Error()
                is CiudadesEstado.BusquedaTerminada -> BusquedaTerminada(estado.info, ejecutar)
                is CiudadesEstado.Vacio -> Vacio()
            }
        }
    }
}

@Composable
fun BusquedaTerminada(
    ciudades: List<Ciudad>,
    ejecutar: (CiudadesIntencion) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        ciudades.forEach { ciudad ->
            CiudadCard(ciudad) {
                ejecutar(CiudadesIntencion.ciudadSeleccionada(it))
            }
        }
    }
}

@Composable
fun CiudadCard(ciudad: Ciudad, onClick: (Ciudad) -> Unit) {
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
        onClick = { onClick(ciudad) }
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
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "No encontramos ciudad con ese nombre",
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
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
            text = "Por favor busca algo o usa tu geolocalización",
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
