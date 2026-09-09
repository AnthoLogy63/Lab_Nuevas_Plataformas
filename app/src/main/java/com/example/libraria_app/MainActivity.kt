package com.example.libraria_app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.libraria_app.ui.theme.LIBRARIAAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            LIBRARIAAppTheme {
                RegistroLibro(
                    guardarRegistro = { titulo, autor, paginas ->
                        guardarRegistro(titulo, autor, paginas)
                    },
                    verRegistro = {
                        verRegistro()
                    }
                )
            }
        }
    }

    private fun guardarRegistro(
        titulo: String,
        autor: String,
        paginas: String
    ) {
        val datos = """
            Título: $titulo
            Autor: $autor
            Páginas leídas: $paginas
        """.trimIndent()

        openFileOutput("libro.txt", MODE_PRIVATE).use {
            it.write(datos.toByteArray())
        }
    }

    private fun verRegistro(): String {
        return try {
            val contenido = openFileInput("libro.txt").bufferedReader().use {
                it.readText()
            }

            Log.d("LIBRARIA", contenido)

            contenido

        } catch (e: Exception) {
            Log.e("LIBRARIA", "Error al leer el archivo: ${e.message}")
            "No se pudo leer el registro."
        }
    }
}

@Composable
fun RegistroLibro(
    guardarRegistro: (String, String, String) -> Unit,
    verRegistro: () -> String
) {
    var titulo by remember {
        mutableStateOf("")
    }

    var autor by remember {
        mutableStateOf("")
    }

    var paginas by remember {
        mutableStateOf("")
    }

    var registroLeido by remember {
        mutableStateOf("")
    }

    val naranja = Color(0xFFFF9800)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "LIBRARIA",
            color = naranja,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(30.dp))

        TextField(
            value = titulo,
            onValueChange = {
                titulo = it
            },
            label = {
                Text("Título del libro")
            },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = naranja,
                unfocusedIndicatorColor = naranja,
                focusedLabelColor = naranja,
                unfocusedLabelColor = Color.Gray,
                cursorColor = naranja
            )
        )

        Spacer(modifier = Modifier.height(15.dp))

        TextField(
            value = autor,
            onValueChange = {
                autor = it
            },
            label = {
                Text("Autor")
            },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = naranja,
                unfocusedIndicatorColor = naranja,
                focusedLabelColor = naranja,
                unfocusedLabelColor = Color.Gray,
                cursorColor = naranja
            )
        )

        Spacer(modifier = Modifier.height(15.dp))

        TextField(
            value = paginas,
            onValueChange = {
                paginas = it
            },
            label = {
                Text("Número de páginas leídas")
            },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = naranja,
                unfocusedIndicatorColor = naranja,
                focusedLabelColor = naranja,
                unfocusedLabelColor = Color.Gray,
                cursorColor = naranja
            )
        )

        Spacer(modifier = Modifier.height(25.dp))

        Button(
            onClick = {
                guardarRegistro(titulo, autor, paginas)
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = naranja
            )
        ) {
            Text("Guardar")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                registroLeido = verRegistro()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = naranja
            )
        ) {
            Text("Ver registro")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = registroLeido,
            modifier = Modifier.fillMaxWidth()
        )
    }
}