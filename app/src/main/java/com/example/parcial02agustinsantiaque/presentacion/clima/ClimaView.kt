package com.example.parcial02agustinsantiaque.presentacion.clima

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.example.parcial02agustinsantiaque.R
import com.example.parcial02agustinsantiaque.repositorio.models.Clima
import com.example.parcial02agustinsantiaque.repositorio.models.ClimaActual
import com.example.parcial02agustinsantiaque.ui.theme.DarkPrimary
import com.example.parcial02agustinsantiaque.ui.theme.DarkSecondary
import com.example.parcial02agustinsantiaque.ui.theme.DarkTertiary
import java.util.Locale

@Composable
fun ClimaView(
    estado: ClimaEstado,
    ejecutar: (ClimaIntencion) -> Unit,
) {
    when (estado) {
        is ClimaEstado.Cargando -> Cargando()
        is ClimaEstado.Error -> Error()
        is ClimaEstado.Ok -> Ok(estado.climaYPronostico, ejecutar)
        is ClimaEstado.Vacio -> Text(text = "ads")
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
        modifier = Modifier
            .fillMaxSize()
            .background(DarkPrimary),

        )
}

@Composable
fun Error() {
    Spacer(modifier = Modifier.padding(top = 20.dp))
    Text("ERROERERORROERERROER")
}

@Composable
fun Ok(clima: ClimaYPronostico, ejecutar: (ClimaIntencion) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = DarkPrimary)
            .padding(top = 10.dp)
    ) {
        Spacer(modifier = Modifier.padding(top = 20.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                IconButton(
                    onClick = { ejecutar(ClimaIntencion.volverAtras) },
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver",
                        tint = Color.White,
                    )
                }

                CurrentWeatherCard(clima.actual)

            }

            Spacer(modifier = Modifier.height(16.dp))

            ForecastForNextDays(clima.pronostico)
        }
    }
}

@Composable
fun CurrentWeatherCard(climaActual: ClimaActual) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp)
            .background(DarkSecondary),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = DarkSecondary,
            contentColor = Color.White
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground), // Asegúrate de tener este recurso
                    contentDescription = "Weather Icon",
                    tint = Color(0xFFFFC107),
                    modifier = Modifier.size(64.dp)
                )
                Text(
                    text = "${climaActual.temperatura}°C",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = climaActual.clima!!,
                    fontSize = 18.sp,
                    color = Color.White
                )
                Text(
                    text = "Sensacion Termica: ${climaActual.sensTermica}°C",
                    fontSize = 18.sp,
                    color = Color.White
                )
            }
        }

    }
}

@Composable
fun ForecastForNextDays(pronostico: List<Clima>) {
    Column(horizontalAlignment = Alignment.Start) {
        Text(
            text = "Pronóstico para los próximos días",
            fontSize = 20.sp,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        pronostico.forEach { day ->
            ForecastDayCard(day)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@SuppressLint("SimpleDateFormat", "DefaultLocale")
@Composable
fun ForecastDayCard(pronostico: Clima) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .background(DarkTertiary),
        colors = CardColors(
            contentColor = DarkSecondary, disabledContentColor = DarkTertiary,
            containerColor = DarkSecondary,
            disabledContainerColor = DarkTertiary
        ),
        shape = RoundedCornerShape(8.dp),

        ) {

        Row(
            modifier = Modifier
                .padding(16.dp)
        ) {

            Text(
                text = "Dia ${pronostico.dateTime}: ",
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = "Min: ${String.format("%.2f", pronostico.tempMin)}°C" +
                        " / Max: ${String.format("%.2f", pronostico.tempMax)}°C ",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}