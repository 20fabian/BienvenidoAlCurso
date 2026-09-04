package com.tecsup.bienvenidoalcurso

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class EventoSocial(
    val id: String,
    val titulo: String,
    val organizador: String,
    val esOrganizadorVerificado: Boolean,
    val categoria: String,
    val distrito: String,
    val fechaHora: String,
    val inscritos: Int,
    val maxCapacidad: Int,
    val requiereAprobacion: Boolean,
    val esUbicacionOculta: Boolean
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaExplorarEventos() {
    val distritosDisponibles = listOf("Todos", "Los Olivos", "San Martín de Porres", "Miraflores", "San Miguel")
    var distritoSeleccionado by remember { mutableStateOf("Todos") }

    val listaEventos = remember {
        mutableStateListOf(
            EventoSocial("1", "Noche de Videojuegos & Pizza", "Carlos R.", true, "Videojuegos", "Los Olivos", "Sáb 23 Ago - 7:00 PM", 8, 12, requiereAprobacion = true, esUbicacionOculta = true),
            EventoSocial("2", "Partido de Fútbol 6v6", "Jorly M.", false, "Deportes", "San Martín de Porres", "Sáb 23 Ago - 4:00 PM", 14, 20, requiereAprobacion = false, esUbicacionOculta = false),
            EventoSocial("3", "Networking & Café", "Camila P.", true, "Tecnología", "Miraflores", "Dom 24 Ago - 5:00 PM", 6, 10, requiereAprobacion = true, esUbicacionOculta = true)
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Column {
                        Text("Descubre Eventos 🇵🇪", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text("Encuentra reuniones cerca de ti", fontSize = 12.sp, color = Color.Gray)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF8F9FA))
        ) {
            Text(
                text = "Filtrar por Distrito:",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
            
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(distritosDisponibles) { distrito ->
                    FilterChip(
                        selected = (distrito == distritoSeleccionado),
                        onClick = { distritoSeleccionado = distrito },
                        label = { Text(distrito) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            val eventosFiltrados = if (distritoSeleccionado == "Todos") {
                listaEventos
            } else {
                listaEventos.filter { it.distrito == distritoSeleccionado }
            }

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(eventosFiltrados) { evento ->
                    TarjetaEventoItem(evento = evento)
                }
            }
        }
    }
}

@Composable
fun TarjetaEventoItem(evento: EventoSocial) {
    var estadoSolicitud by remember { mutableStateOf("INICIAL") }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = evento.categoria,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }

                Text(
                    text = "👥 ${evento.inscritos}/${evento.maxCapacidad} cupos",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (evento.inscritos >= evento.maxCapacidad) Color.Red else Color(0xFF00875A)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = evento.titulo,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Organizado por: ${evento.organizador}", fontSize = 13.sp, color = Color.Gray)
                if (evento.esOrganizadorVerificado) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Verificado",
                        tint = Color(0xFF006D3B),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Place, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${evento.distrito} ${if (evento.esUbicacionOculta) "(Ubicación exacta protegida 🔒)" else ""}",
                    fontSize = 13.sp,
                    color = Color.DarkGray
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            HorizontalDivider(color = Color(0xFFEEEEEE))

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (evento.requiereAprobacion) {
                        Icon(Icons.Default.Lock, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Requiere aprobación", fontSize = 11.sp, color = Color.Gray)
                    }
                }

                Button(
                    onClick = {
                        estadoSolicitud = if (evento.requiereAprobacion) "PENDIENTE" else "UNIDO"
                    },
                    enabled = estadoSolicitud == "INICIAL",
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (estadoSolicitud == "UNIDO") Color(0xFF00875A) else MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = when (estadoSolicitud) {
                            "PENDIENTE" -> "Solicitud Enviada ⏳"
                            "UNIDO" -> "¡Ya estás unido! ✅"
                            else -> if (evento.requiereAprobacion) "Solicitar unirme" else "Unirme ahora"
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewExplorarEventos() {
    MaterialTheme {
        PantallaExplorarEventos()
    }
}
