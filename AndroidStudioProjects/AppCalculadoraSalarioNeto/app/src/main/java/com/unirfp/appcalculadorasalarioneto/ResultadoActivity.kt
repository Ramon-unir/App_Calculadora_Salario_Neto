package com.unirfp.appcalculadorasalarioneto

// Importaciones
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Activity que muestra los resultados
class ResultadoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Recuperamos los datos enviados en el Intent (si no vienen, ponemos 0 por defecto)
        val salarioBruto = intent.getDoubleExtra("salarioBruto", 0.0)
        val salarioNeto = intent.getDoubleExtra("salarioNeto", 0.0)
        val retencion = intent.getDoubleExtra("retencion", 0.0)
        val deducciones = intent.getDoubleExtra("deducciones", 0.0)

        // setContent para dibujar la pantalla con Compose
        setContent {
            MaterialTheme {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)) {

                    Text("Resultados del Cálculo", fontSize = 22.sp, modifier = Modifier.padding(bottom = 12.dp))

                    // Mostramos cada resultado formateado con 2 decimales
                    Text(text = "Salario bruto: %.2f €".format(salarioBruto), modifier = Modifier.padding(4.dp))
                    Text(text = "Salario neto: %.2f €".format(salarioNeto), modifier = Modifier.padding(4.dp))
                    Text(text = "Retención IRPF: %.2f %%".format(retencion), modifier = Modifier.padding(4.dp))
                    Text(text = "Deducciones: %.2f €".format(deducciones), modifier = Modifier.padding(4.dp))

                    Spacer(modifier = Modifier.height(20.dp))

                    // Un botón para volver (cierra la actividad actual)
                    Button(onClick = { finish() }, modifier = Modifier.fillMaxWidth()) {
                        Text("Volver")
                    }
                }
            }
        }
    }
}