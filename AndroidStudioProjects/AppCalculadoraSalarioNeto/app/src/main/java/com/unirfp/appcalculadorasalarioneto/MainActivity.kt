package com.unirfp.appcalculadorasalarioneto


import android.content.Intent //  cambiar de pantalla (Activity)
import android.os.Bundle //  manejar la creación de la pantalla
import androidx.activity.ComponentActivity // Tipo de Activity que usamos con Compose
import androidx.activity.compose.setContent //  poner la UI de Compose
import androidx.compose.foundation.layout.* //  organizar los elementos (Column, Spacer...)
import androidx.compose.material3.* // Componentes básicos (Text, Button, TextField...) - material3
import androidx.compose.runtime.* //  estados (remember, mutableStateOf)
import androidx.compose.ui.Modifier //  ajustar tamaño/espaciado
import androidx.compose.ui.unit.dp //  medir espacios en "dp"
import androidx.compose.ui.unit.sp //  medir tamaño de letra


// pantalla donde el usuario introduce datos
class MainActivity : ComponentActivity() {

    // onCreate = aquí se "enciende" la pantalla cuando la app la abre
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // setContent -> aquí ponemos toda la interfaz con Compose
        setContent {
            // Tema por defecto
            MaterialTheme {
                // Llamamos a la función composable que dibuja la pantalla principal
                PantallaEntrada { salarioBruto, edad, hijos, pagas, estadoCivil ->
                    // Esta lambda se llama cuando el usuario pulsa "Calcular"
                    // Creamos un Intent para abrir ResultadoActivity y enviar los datos
                    val intent = Intent(this, ResultadoActivity::class.java)

                    // Calculamos retención y deducciones
                    val retencionIRPF = when {
                        hijos > 2 -> 0.12
                        edad > 50 -> 0.15
                        estadoCivil.lowercase() == "casado" || estadoCivil.lowercase() == "casado/a" -> 0.14
                        else -> 0.18
                    }
                    val deducciones = hijos * 100.0
                    val salarioNeto = (salarioBruto - (salarioBruto * retencionIRPF)) + deducciones

                    // Ponemos los datos en el Intent para la otra Activity
                    intent.putExtra("salarioBruto", salarioBruto)
                    intent.putExtra("salarioNeto", salarioNeto)
                    intent.putExtra("retencion", retencionIRPF * 100) // porcentaje
                    intent.putExtra("deducciones", deducciones)

                    // Iniciamos la otra pantalla
                    startActivity(intent)
                }
            }
        }
    }
}

/*
 * Aquí definimos la UI de la pantalla principal como una función composable.
 * Le pasamos un parámetro "onCalcular" que es una función que se llama cuando el usuario pulsa el botón.
 */
@Composable
fun PantallaEntrada(
    onCalcular: (salarioBruto: Double, edad: Int, hijos: Int, pagas: Int, estadoCivil: String) -> Unit
) {
    // recordamos los valores que el usuario escribe con remember + mutableStateOf
    var salarioText by remember { mutableStateOf("") } // texto del salario bruto
    var edadText by remember { mutableStateOf("") } // texto de edad
    var hijosText by remember { mutableStateOf("") } // texto nº hijos
    var pagasText by remember { mutableStateOf("") } // texto nº pagas
    var estadoCivilText by remember { mutableStateOf("") } // texto estado civil (ej: Soltero)

    // Column coloca elementos verticalmente (de arriba abajo)
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        // Título
        Text(text = "Calculadora de Salario Neto", fontSize = 24.sp, modifier = Modifier.padding(bottom = 12.dp))

        // Campo: Salario bruto
        OutlinedTextField(
            value = salarioText,
            onValueChange = { salarioText = it }, // cada vez que alguien escribe actualizamos la variable
            label = { Text("Salario bruto anual (€)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp)) // espacio pequeño

        // Campo: Edad
        OutlinedTextField(
            value = edadText,
            onValueChange = { edadText = it },
            label = { Text("Edad") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Campo: Número de hijos
        OutlinedTextField(
            value = hijosText,
            onValueChange = { hijosText = it },
            label = { Text("Número de hijos") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Campo: Número de pagas
        OutlinedTextField(
            value = pagasText,
            onValueChange = { pagasText = it },
            label = { Text("Número de pagas (ej. 12)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Campo: Estado civil
        OutlinedTextField(
            value = estadoCivilText,
            onValueChange = { estadoCivilText = it },
            label = { Text("Estado civil (ej. Soltero/Casado)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para calcular. Al pulsarlo validamos y llamamos a onCalcular con los valores correctos
        Button(
            onClick = {
                // Convertimos textos a números , si falla usamos valores por defecto
                val salarioBruto = salarioText.toDoubleOrNull() ?: 0.0
                val edad = edadText.toIntOrNull() ?: 0
                val hijos = hijosText.toIntOrNull() ?: 0
                val pagas = pagasText.toIntOrNull() ?: 12
                val estadoCivil = estadoCivilText.ifBlank { "Soltero" } // si no pone nada, asumimos Soltero

                // Llamamos a la función que nos pasó la Activity para navegar y enviar datos
                onCalcular(salarioBruto, edad, hijos, pagas, estadoCivil)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calcular salario neto")
        }
    }
}