package com.example.parcial02agustinsantiaque.presentacion.ciudades

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.example.parcial02agustinsantiaque.R
import com.example.parcial02agustinsantiaque.repositorio.DTO.RequestCiudadPorNombreDTO

@Composable
fun CiudadesView(
    modifier: Modifier = Modifier,
    estado: CiudadesEstado,
    ejecutar: (CiudadesIntencion) -> Unit
) {
    var textoBusqueda by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            Row(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.primary)
                    .fillMaxWidth()
                    .padding(end = 16.dp)
                    .clip(RoundedCornerShape(0.dp)),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    singleLine = true,
                    value = textoBusqueda,
                    onValueChange = { textoBusqueda = it },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 6.dp)
                        .border(
                            1.dp,
                            color = MaterialTheme.colorScheme.onBackground,
                            shape = RectangleShape
                        ),
                    keyboardOptions =
                    KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            keyboardController?.hide()
                            ejecutar(CiudadesIntencion.getLatitudLongitud(textoBusqueda))
                        }
                    ),
                )

                Image(
                    painter = painterResource(R.drawable.my_location_24px),
                    contentDescription = "Usar mi localización",
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(24.dp)
                )
            }
            when (estado) {
                is CiudadesEstado.Cargando -> Cargando()
                is CiudadesEstado.Error -> Error()
                is CiudadesEstado.Ok -> Estamos(estado.info)
                is CiudadesEstado.Vacio -> Text("Vacio")
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
fun Estamos(info: RequestCiudadPorNombreDTO) {
    Column {
        Text(
            "Lat: ${info.lat}",
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            "Lon: ${info.lon}",
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            "Nombre: ${info.name}",
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            "Pais: ${info.country}",
            style = MaterialTheme.typography.titleLarge
        )
    }

}