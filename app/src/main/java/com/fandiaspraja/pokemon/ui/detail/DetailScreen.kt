package com.fandiaspraja.pokemon.ui.detail

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.fandiaspraja.pokemon.core.data.Resource
import com.fandiaspraja.pokemon.core.domain.model.Pokemon
import com.fandiaspraja.pokemon.core.domain.model.PokemonDetail

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    pokemonName: String,
    viewModel: DetailViewModel,
    onBack: () -> Unit,
    ) {

    val state by viewModel.detail.collectAsState()

    LaunchedEffect(pokemonName) {
        Log.d("DETAIL", "terima name: $pokemonName")
        viewModel.getDetail(pokemonName)
    }


    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Pokemon Detail") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            when (state) {
                is Resource.Loading -> {
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                is Resource.Success -> {
                    val data = (state as Resource.Success<PokemonDetail>).data
                    if (data != null) {


                        item {
                            Column (
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(16.dp) // 🔥 spacing antar section
                            ) {
                                // 🔹 IMAGE
                                AsyncImage(
                                    model = data.sprites?.other?.officialArtwork?.frontDefault,
                                    contentDescription = data.name,
                                    modifier = Modifier.size(140.dp)
                                )

                                // 🔹 NAME
                                Text(
                                    text = "${data.name?.uppercase()}",
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold
                                )

                                // 🔹 TYPES
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    data.types?.forEach {
                                        TypeBadge("${it?.type?.name}")
                                    }
                                }

                                // 🔹 INFO CARD
                                PokemonCard {
                                    Text("Information", fontWeight = FontWeight.Bold)

                                    InfoRow("Height", "${data.height}")
                                    InfoRow("Weight", "${data.weight}")
                                    InfoRow("Base EXP", "${data.baseExperience}")

                                }

                                // 🔹 ABILITIES CARD
                                PokemonCard {
                                    Text("Abilities", fontWeight = FontWeight.Bold)

                                    data.abilities?.forEach {
                                        Text("• ${it?.ability?.name}")
                                    }
                                }

                                // 🔹 STATS CARD
                                PokemonCard {
                                    Text("Stats", fontWeight = FontWeight.Bold)

                                    data.stats?.forEach {
                                        StatItem("${it?.stat?.name}", it?.baseStat ?: 0)
                                    }
                                }
                            }

                        }

                    } else {
                        item {
                            Text("Pokemon details not found.")
                        }
                    }
                }

                is Resource.Error -> {
                    item {
                        val errorMessage = (state as Resource.Error).message ?: "An unknown error occurred."
                        Text(errorMessage)
                    }

                }

                else -> {}
            }


        }


    }
}

@Composable
fun StatItem(name: String, value: Int) {
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(name)
            Text(value.toString())
        }

        LinearProgressIndicator(
            progress = value / 100f,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(10.dp))
        )
    }
}

@Composable
fun TypeBadge(type: String) {
    val color = when (type.lowercase()) {
        "grass" -> Color(0xFF66BB6A)
        "fire" -> Color(0xFFEF5350)
        "water" -> Color(0xFF42A5F5)
        "electric" -> Color(0xFFFFCA28)
        "poison" -> Color(0xFFAB47BC)
        else -> Color.LightGray
    }

    Box(
        modifier = Modifier
            .background(color, shape = RoundedCornerShape(50))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = type,
            color = Color.White,
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Composable
fun PokemonCard(
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            content = content
        )
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label)
        Text(
            text = value,
            fontWeight = FontWeight.SemiBold
        )
    }
}