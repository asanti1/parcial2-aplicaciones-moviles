package com.example.parcial02agustinsantiaque.presentacion.ciudades

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Button
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.example.parcial02agustinsantiaque.R
import com.example.parcial02agustinsantiaque.repositorio.models.Ciudad

@Composable
fun CiudadesView(
    estado: CiudadesEstado,
    ejecutar: (CiudadesIntencion) -> Unit,
) {
    var textoBusqueda by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .padding(top = 10.dp)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.primary)
                .fillMaxWidth()
                .padding(end = 16.dp)
                .clip(RoundedCornerShape(0.dp)),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = CenterVertically
        ) {
            TextField(
                singleLine = true,
                value = textoBusqueda,
                onValueChange = { textoBusqueda = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 1.dp)
                    .border(
                        1.dp, color = MaterialTheme.colorScheme.onBackground, shape = RectangleShape
                    ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(onSearch = {
                    keyboardController?.hide()
                    ejecutar(CiudadesIntencion.getLatitudLongitud(textoBusqueda))
                }),
            )
            Image(
                painter = painterResource(R.drawable.my_location_24px),
                contentDescription = "Usar mi localización",
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(24.dp)
            )
        }
    }
    when (estado) {
        is CiudadesEstado.Cargando -> Cargando()
        is CiudadesEstado.Error -> Error()
        is CiudadesEstado.BusquedaTerminada -> BusquedaTerminada(estado.info, ejecutar)
        is CiudadesEstado.Vacio -> Vacio()
    }
}

@Composable
fun BusquedaTerminada(
    ciudades: List<Ciudad>,
    ejecutar: (CiudadesIntencion) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 5.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(color = MaterialTheme.colorScheme.primary)
                .align(alignment = Alignment.Center),

            ) {
            ciudades.map { ciudad ->
                Button(
                    onClick = { ejecutar(CiudadesIntencion.ciudadSeleccionada(ciudad)) },
                    modifier = Modifier
                        .fillMaxWidth(0.95f)
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 1.dp),
                        text = "${ciudad.name},${ciudad.state},${ciudad.country}"
                    )
                }
            }
        }
    }
}

@Composable
fun Cargando() {
    val imageLoader = ImageLoader.Builder(LocalContext.current).components {
        if (SDK_INT >= 28) {
            add(ImageDecoderDecoder.Factory())
        } else {
            add(GifDecoder.Factory())
        }
    }.build()
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
fun Vacio() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 5.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .align(alignment = Alignment.Center),
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .clip(RoundedCornerShape(8.dp)),
                textAlign = TextAlign.Center,
                text = "Por favor busca algo o usa tu geolocalizacion"
            )
        }
    }

}


