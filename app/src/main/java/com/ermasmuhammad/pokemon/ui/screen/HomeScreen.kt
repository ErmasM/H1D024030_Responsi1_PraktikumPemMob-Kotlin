package com.ermasmuhammad.pokemon.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ermasmuhammad.pokemon.ui.state.PokemonUiState
import com.ermasmuhammad.pokemon.ui.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onPokemonClick: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    var searchQuery by rememberSaveable {
        mutableStateOf("")
    }

    var selectedType by rememberSaveable {
        mutableStateOf("All")
    }

    val categories = listOf(
        "All",
        "Normal",
        "Fire",
        "Water",
        "Grass",
        "Electric",
        "Ice",
        "Fighting",
        "Poison",
        "Ground",
        "Flying",
        "Psychic",
        "Bug",
        "Rock",
        "Ghost",
        "Dragon",
        "Dark",
        "Steel",
        "Fairy"
    )

    when (val state = uiState) {

        PokemonUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is PokemonUiState.Success -> {

            val filteredPokemon = state.pokemon.filter { pokemon ->

                val matchesSearch = pokemon.name.contains(
                    searchQuery,
                    ignoreCase = true
                )

                val matchesType =
                    selectedType == "All" ||
                            pokemon.types.any {
                                it.type.name.equals(
                                    selectedType,
                                    ignoreCase = true
                                )
                            }

                matchesSearch && matchesType
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF7F7F7))
            ) {

                // Top Bar
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFFFCB05))
                        .windowInsetsPadding(WindowInsets.statusBars)
                        .padding(
                            horizontal = 20.dp,
                            vertical = 18.dp
                        )
                ) {
                    Text(
                        text = "Pokédex",
                        color = Color.White,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Search Bar
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = {
                        searchQuery = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 18.dp,
                            vertical = 16.dp
                        ),
                    placeholder = {
                        Text(
                            text = "Cari Pokémon",
                            color = Color(0xFF777777)
                        )
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFFFCB05),
                        unfocusedBorderColor = Color(0xFFFFCB05),
                        focusedTextColor = Color(0xFF222222),
                        unfocusedTextColor = Color(0xFF222222),
                        cursorColor = Color(0xFFFFCB05),
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )

                // Category Title
                Text(
                    text = "Kategori",
                    modifier = Modifier.padding(
                        horizontal = 20.dp
                    ),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF222222)
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                // Category List
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(
                        horizontal = 18.dp
                    ),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(categories) { category ->

                        val isSelected =
                            selectedType == category

                        Box(
                            modifier = Modifier
                                .background(
                                    color = if (isSelected) {
                                        Color(0xFFFFCB05)
                                    } else {
                                        Color.White
                                    },
                                    shape = RoundedCornerShape(20.dp)
                                )
                                .clickable {
                                    selectedType = category
                                }
                                .padding(
                                    horizontal = 16.dp,
                                    vertical = 9.dp
                                )
                        ) {
                            Text(
                                text = category,
                                color = if (isSelected) {
                                    Color.White
                                } else {
                                    Color(0xFF444444)
                                },
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                // Section Title
                Text(
                    text = "Daftar Pokémon",
                    modifier = Modifier.padding(
                        horizontal = 20.dp
                    ),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF222222)
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                // Pokémon Grid
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            horizontal = 12.dp
                        ),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    items(filteredPokemon) { pokemon ->

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onPokemonClick(pokemon.id)
                                },
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 3.dp
                            )
                        ) {

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                horizontalAlignment =
                                    Alignment.CenterHorizontally
                            ) {

                                // Pokémon Image
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(125.dp)
                                        .background(
                                            color = Color(0xFFF1F1F1),
                                            shape = RoundedCornerShape(14.dp)
                                        ),
                                    contentAlignment =
                                        Alignment.Center
                                ) {

                                    AsyncImage(
                                        model = pokemon.sprites.front_default,
                                        contentDescription =
                                            pokemon.name,
                                        modifier =
                                            Modifier.size(110.dp)
                                    )
                                }

                                Spacer(
                                    modifier = Modifier.height(8.dp)
                                )

                                // Pokémon ID
                                Text(
                                    text = "#${pokemon.id}",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Gray
                                )

                                Spacer(
                                    modifier = Modifier.height(2.dp)
                                )

                                // Pokémon Name
                                Text(
                                    text = pokemon.name.replaceFirstChar {
                                        it.uppercase()
                                    },
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF222222)
                                )

                                Spacer(
                                    modifier = Modifier.height(4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        is PokemonUiState.Error -> {

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment =
                    Alignment.CenterHorizontally,
                verticalArrangement =
                    Arrangement.Center
            ) {
                Text(
                    text = state.message
                )
            }
        }
    }
}