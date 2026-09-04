package com.tecsup.bienvenidoalcurso

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// 1. Ejemplo de Contenedores (Card, LazyRow, Surface)
@Composable
fun ContenedoresEjemplo() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Ejemplo de Card y LazyRow:", style = MaterialTheme.typography.titleMedium)
        
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            LazyRow(modifier = Modifier.padding(8.dp)) {
                items(5) { index ->
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = MaterialTheme.shapes.small,
                        modifier = Modifier
                            .padding(4.dp)
                            .size(60.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text("Item $index")
                        }
                    }
                }
            }
        }
    }
}

// 2. Ejemplo de Controles (Checkbox, Switch, Slider, ProgressIndicator)
@Composable
fun ControlesEjemplo() {
    var checkedState by remember { mutableStateOf(true) }
    var switchState by remember { mutableStateOf(false) }
    var sliderPosition by remember { mutableStateOf(0.5f) }

    Column(modifier = Modifier.padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = checkedState,
                onCheckedChange = { checkedState = it }
            )
            Text("Checkbox activo")
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Switch(
                checked = switchState,
                onCheckedChange = { switchState = it }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Switch")
        }

        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicator(progress = { sliderPosition }, modifier = Modifier.fillMaxWidth())
        
        Slider(
            value = sliderPosition,
            onValueChange = { sliderPosition = it }
        )
    }
}

// 3. Vista Previa para la documentación del laboratorio
@Preview(showBackground = true)
@Composable
fun PreviewExploracionComponentes() {
    MaterialTheme {
        Column {
            ContenedoresEjemplo()
            HorizontalDivider()
            ControlesEjemplo()
        }
    }
}
