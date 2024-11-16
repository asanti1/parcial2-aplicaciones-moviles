import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseClimaLatLon(
    val list: List<ForecastItem>,
)

@Serializable
data class ForecastItem(
    val dt: Long,
    val dt_txt: String,
    val main: Main,
    val weather: List<Weather>,
    val clouds: Clouds,
    val wind: Wind,
    val visibility: Int? = null,
    val pop: Double,
    val sys: Sys,
    val rain: Rain? = null
)

@Serializable
data class Main(
    val temp: Double,
    @SerialName("feels_like")
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Int,
    val sea_level: Int,
    val grnd_level: Int,
    val humidity: Int,
    val temp_kf: Double
)

@Serializable
data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String,
    val visibility: Int?
)

@Serializable
data class Clouds(
    val all: Int
)

@Serializable
data class Wind(
    val speed: Double,
    val deg: Int,
    val gust: Double
)

@Serializable
data class Sys(
    val pod: String
)

@Serializable
data class Rain(
    @SerialName("3h")
    val threeHours: Double
)

@Serializable
data class City(
    val id: Int,
    val name: String,
    val coord: Coord,
    val country: String,
    val population: Int,
    val timezone: Int,
    val sunrise: Long,
    val sunset: Long
)

@Serializable
data class Coord(
    val lat: Double,
    val lon: Double
)