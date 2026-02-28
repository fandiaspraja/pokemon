package com.fandiaspraja.pokemon.ui.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.fandiaspraja.pokemon.core.data.Resource
import com.fandiaspraja.pokemon.core.domain.model.Pokemon
import kotlin.text.replaceFirstChar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onClickDetail: (Pokemon) -> Unit
) {

    var searchQuery by remember { mutableStateOf("") }

    val pagingItems = viewModel.pokemons.collectAsLazyPagingItems()


//    val pokemonState by viewModel.pokemonList.collectAsState()
//
//    when (val resource = pokemonState) {
//        is Resource.Loading -> {
//            // Show a centered loading indicator
//            Box(
//                modifier = Modifier.fillMaxSize(),
//                contentAlignment = Alignment.Center
//            ) {
//                CircularProgressIndicator()
//            }
//        }
//        is Resource.Success -> {
//            // On success, get the data and display it in a LazyColumn
//            val pokemons = resource.data
//            Log.d("home", "pokemons: ${pokemons?.size}")
//            if (pokemons != null) {
//                LazyColumn {
//                    // 3. Use the 'items' extension that takes a List
//                    items(pokemons) { pokemon ->
//                        Text(
//                            text = "${pokemon.name}",
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .clickable { pokemon.name?.let { onClickDetail(it) } }
//                                .padding(16.dp)
//                        )
//                    }
//                }
//            }
//        }
//        is Resource.Error -> {
//            // On error, show a centered error message
//            Box(
//                modifier = Modifier.fillMaxSize(),
//                contentAlignment = Alignment.Center
//            ) {
//                Text("Error: ${resource.message}")
//            }
//        }
//
//        else -> {
//            Box(
//                modifier = Modifier.fillMaxSize(),
//                contentAlignment = Alignment.Center
//            ) {
//                Text("Error: ${resource.message}")
//            }
//        }
//    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Pokedex") },
            )
        }
    ) { innerPadding ->


        Column(
            modifier = Modifier.padding(innerPadding)
        ) {

            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { Text("Search Pokémon...") },
                singleLine = true
            )

            LazyColumn (
                modifier = Modifier
//                .padding(innerPadding)
//                .fillMaxWidth()
//                .wrapContentHeight()
                    .fillMaxSize()
            ) {
                val filteredItems = (0 until pagingItems.itemCount).mapNotNull { index ->
                    pagingItems[index]
                }.filter {
                    // Logika filter: cocokkan nama pokemon dengan query (case-insensitive)
                    it.name!!.contains(searchQuery, ignoreCase = true)
                }

                items(
                    items = filteredItems,
                    key = { pokemon -> pokemon.id }
                ) { item ->
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .clickable {
                                Log.d("NAV", "klik: ${item.name}")
                                onClickDetail(item)
                            },
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        // The Text now lives inside the Card
                        Text(
                            text = item.name!!.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() },
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                        )
                    }
                }

                // Jangan hapus blok ini, ini untuk menangani state loading dari Paging
                if (searchQuery.isEmpty()) { // Hanya tampilkan loader jika tidak sedang mencari
                    pagingItems.apply {
                        when {
                            loadState.refresh is LoadState.Loading -> item {
                                Box(
                                    modifier = Modifier
                                        .fillParentMaxSize(), // Gunakan fillParentMaxSize untuk loading awal
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }

                            loadState.append is LoadState.Loading -> item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }

                            loadState.refresh is LoadState.Error -> item {
                                Box(
                                    modifier = Modifier.fillParentMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("Error loading data")
                                }
                            }

                            loadState.append is LoadState.Error -> item {
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("Error loading more items")
                                }
                            }
                        }
                    }
                }

//                items(
//                    count = pagingItems.itemCount,
//                    key = { index -> pagingItems[index]?.id ?: index }
//                ) { index ->
//                    val item = pagingItems[index]
//
//                    item?.let {
//                        Card(
//                            colors = CardDefaults.cardColors(
//                                containerColor = MaterialTheme.colorScheme.primaryContainer,
//                            ),
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .height(100.dp)
//                                .padding(horizontal = 16.dp, vertical = 8.dp) // Add padding around the card
//                                .clickable { // Move the clickable modifier here
//                                    Log.d("NAV", "klik: ${item.name}")
//                                    onClickDetail(item)
//                                },
//                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp) // Give it a slight shadow
//                        ) {
//                            // The Text now lives inside the Card
//                            Text(
//                                text = "${item.name}",
//                                fontSize = 18.sp,
//                                fontWeight = FontWeight.Bold,
//                                modifier = Modifier
//                                    .padding( horizontal = 16.dp, vertical = 24.dp) // Add padding inside the card
//                            )
//                        }
//                    }
//                }

                pagingItems.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }                    }

                        loadState.append is LoadState.Loading -> item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }                    }

                        loadState.refresh is LoadState.Error -> item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("Error loading data")
                            }
                        }

                        loadState.append is LoadState.Error -> item {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("Error loading more items")
                            }
                        }
                    }
                }

            }
        }


    }


}