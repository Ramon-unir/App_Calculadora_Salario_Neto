package com.unirfp.appcalculadorasalarioneto


// Importaciones
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale // Para ajustar cómo se muestra la imagen
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MenuActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                PantallaMenu {
                    // Al pulsar el botón, pasamos a la pantalla principal
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}

@Composable
fun PantallaMenu(onEmpezarClick: () -> Unit) {
    // Caja (Box)  permite apilar elementos uno encima de otro
    Box(
        modifier = Modifier
            .fillMaxSize() // ocupa toda la pantalla
    ) {
        // Imagen de fondo
        Image(
            painter = painterResource(id = R.drawable.logo_movil_appsueldo),
            contentDescription = "Fondo del menú",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        // Cosas encima de la imagen
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Top, // centra verticalmente
            horizontalAlignment = Alignment.CenterHorizontally // centra horizontalmente
        ) {
            // Spacer para crear espacio desde arriba hasta el titulo
            Spacer(modifier = Modifier.weight(0.1f))


            // Título de la app
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = Color.Red
                        )
                    ) {
                        append("C")
                    }
                    append("alculadora de Salario Neto")




                },

                fontSize = 28.sp,

                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Spacer(modifier = Modifier.weight(1.5f))

            // Botón de empezar
            Button(
                onClick = onEmpezarClick,
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(50.dp)
            ) {
                Text(text = "Empezar", fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.weight(0.1f))

        }
    }
}