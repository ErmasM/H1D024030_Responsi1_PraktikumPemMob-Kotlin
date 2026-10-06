package com.ermasmuhammad.pokemon.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ermasmuhammad.pokemon.R
import com.ermasmuhammad.pokemon.ui.viewmodel.DetailViewModel
import com.ermasmuhammad.pokemon.ui.state.PokemonDetailUiState

@Composable
fun PokemonDetailScreen(
    viewModel: DetailViewModel,
    pokemonId: Int,
    onBackClick: () -> Unit
) {

    val detailState by viewModel.detailState.collectAsState()

    LaunchedEffect(pokemonId) {
        viewModel.getPokemonDetail(pokemonId)
    }

    when (val state = detailState) {

        PokemonDetailUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is PokemonDetailUiState.Success -> {

            val pokemon = state.pokemon

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF6F6F6))
            ) {

                // Top Bar
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFFFCB05))
                            .windowInsetsPadding(WindowInsets.statusBars)
                            .padding(
                                horizontal = 12.dp,
                                vertical = 10.dp
                            )
                    ) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            IconButton(
                                onClick = onBackClick,
                                modifier = Modifier.size(48.dp)
                            ) {
                                Icon(
                                    painter = painterResource(
                                        id = R.drawable.outline_arrow_back_24
                                    ),
                                    contentDescription = "Kembali",
                                    tint = Color(0xFF222222),
                                    modifier = Modifier.size(32.dp)
                                )
                            }

                            Spacer(
                                modifier = Modifier.width(8.dp)
                            )

                            Text(
                                text = "Detail ${
                                    pokemon.name.replaceFirstChar {
                                        it.uppercase()
                                    }
                                }",
                                color = Color(0xFF222222),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                item {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {

                        // Pokémon Image
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp),
                            shape = RoundedCornerShape(24.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFFFF3C4)
                            ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 3.dp
                            )
                        ) {

                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {

                                Box(
                                    modifier = Modifier
                                        .size(220.dp)
                                        .background(
                                            color = Color.White.copy(
                                                alpha = 0.65f
                                            ),
                                            shape = CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {

                                    AsyncImage(
                                        model =
                                            pokemon.sprites.front_default,
                                        contentDescription =
                                            pokemon.name,
                                        modifier =
                                            Modifier.size(200.dp)
                                    )
                                }
                            }
                        }

                        Spacer(
                            modifier = Modifier.height(20.dp)
                        )

                        // Name & ID
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment =
                                Alignment.CenterHorizontally
                        ) {

                            Text(
                                text = pokemon.name.replaceFirstChar {
                                    it.uppercase()
                                },
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF222222),
                                textAlign = TextAlign.Center
                            )

                            Text(
                                text = "#${pokemon.id}",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFFCB05),
                                textAlign = TextAlign.Center
                            )
                        }

                        Spacer(
                            modifier = Modifier.height(18.dp)
                        )

                        // Type
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment =
                                Alignment.CenterHorizontally
                        ) {

                            Text(
                                text = "Type",
                                fontSize = 21.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF222222)
                            )

                            Spacer(
                                modifier = Modifier.height(10.dp)
                            )

                            Row(
                                horizontalArrangement =
                                    Arrangement.spacedBy(10.dp)
                            ) {

                                pokemon.types.forEach { type ->

                                    Box(
                                        modifier = Modifier
                                            .background(
                                                color =
                                                    Color(0xFFFFF3C4),
                                                shape =
                                                    RoundedCornerShape(14.dp)
                                            )
                                            .padding(
                                                horizontal = 18.dp,
                                                vertical = 10.dp
                                            )
                                    ) {

                                        Text(
                                            text =
                                                type.type.name
                                                    .replaceFirstChar {
                                                        it.uppercase()
                                                    },
                                            fontSize = 14.sp,
                                            fontWeight =
                                                FontWeight.Bold,
                                            color =
                                                Color(0xFF9A7600)
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(
                            modifier = Modifier.height(22.dp)
                        )

                        // Tinggi & Berat
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement =
                                Arrangement.spacedBy(12.dp)
                        ) {

                            Card(
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(18.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.White
                                ),
                                elevation = CardDefaults.cardElevation(
                                    defaultElevation = 2.dp
                                )
                            ) {

                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(18.dp)
                                ) {

                                    Text(
                                        text = "Tinggi",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Gray
                                    )

                                    Spacer(
                                        modifier =
                                            Modifier.height(8.dp)
                                    )

                                    Text(
                                        text =
                                            "${pokemon.height / 10.0} m",
                                        fontSize = 22.sp,
                                        fontWeight =
                                            FontWeight.Bold,
                                        color =
                                            Color(0xFF222222)
                                    )
                                }
                            }

                            Card(
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(18.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.White
                                ),
                                elevation = CardDefaults.cardElevation(
                                    defaultElevation = 2.dp
                                )
                            ) {

                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(18.dp)
                                ) {

                                    Text(
                                        text = "Berat",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Gray
                                    )

                                    Spacer(
                                        modifier =
                                            Modifier.height(8.dp)
                                    )

                                    Text(
                                        text =
                                            "${pokemon.weight / 10.0} kg",
                                        fontSize = 22.sp,
                                        fontWeight =
                                            FontWeight.Bold,
                                        color =
                                            Color(0xFF222222)
                                    )
                                }
                            }
                        }

                        Spacer(
                            modifier = Modifier.height(24.dp)
                        )

                        // Base Stats
                        Text(
                            text = "Base Stats",
                            fontSize = 21.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF222222)
                        )

                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 2.dp
                            )
                        ) {

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(18.dp),
                                verticalArrangement =
                                    Arrangement.spacedBy(18.dp)
                            ) {

                                pokemon.stats.forEach { stat ->

                                    val statName =
                                        when (stat.stat.name) {

                                            "special-attack" ->
                                                "Sp. Atk"

                                            "special-defense" ->
                                                "Sp. Def"

                                            else ->
                                                stat.stat.name
                                                    .replaceFirstChar {
                                                        it.uppercase()
                                                    }
                                        }

                                    val statProgress =
                                        stat.base_stat / 255f

                                    Row(
                                        modifier =
                                            Modifier.fillMaxWidth(),
                                        verticalAlignment =
                                            Alignment.CenterVertically
                                    ) {

                                        Text(
                                            text = statName,
                                            modifier =
                                                Modifier.width(90.dp),
                                            fontSize = 14.sp,
                                            fontWeight =
                                                FontWeight.Medium,
                                            color =
                                                Color(0xFF555555)
                                        )

                                        Text(
                                            text =
                                                stat.base_stat
                                                    .toString(),
                                            modifier =
                                                Modifier.width(40.dp),
                                            fontSize = 15.sp,
                                            fontWeight =
                                                FontWeight.Bold,
                                            color =
                                                Color(0xFF222222)
                                        )

                                        Box(
                                            modifier = Modifier
                                                .weight(1f)
                                                .height(12.dp)
                                                .background(
                                                    color =
                                                        Color(0xFFE8E8E8),
                                                    shape =
                                                        RoundedCornerShape(
                                                            10.dp
                                                        )
                                                )
                                        ) {

                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth(
                                                        statProgress
                                                    )
                                                    .height(12.dp)
                                                    .background(
                                                        color =
                                                            Color(0xFFFFCB05),
                                                        shape =
                                                            RoundedCornerShape(
                                                                10.dp
                                                            )
                                                    )
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(
                            modifier = Modifier.height(28.dp)
                        )
                    }
                }
            }
        }

        is PokemonDetailUiState.Error -> {

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