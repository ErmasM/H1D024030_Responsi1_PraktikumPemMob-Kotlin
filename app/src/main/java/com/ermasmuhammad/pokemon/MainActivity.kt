package com.ermasmuhammad.pokemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.ermasmuhammad.pokemon.ui.screen.HomeScreen
import com.ermasmuhammad.pokemon.ui.screen.PokemonDetailScreen
import com.ermasmuhammad.pokemon.ui.theme.MyApplicationTheme
import com.ermasmuhammad.pokemon.ui.viewmodel.DetailViewModel
import com.ermasmuhammad.pokemon.ui.viewmodel.HomeViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme {

                val homeViewModel = HomeViewModel()
                val detailViewModel = DetailViewModel()

                var selectedPokemonId by remember {
                    mutableStateOf<Int?>(null)
                }

                if (selectedPokemonId == null) {

                    HomeScreen(
                        viewModel = homeViewModel,
                        onPokemonClick = { id ->
                            selectedPokemonId = id
                        }
                    )

                } else {

                    PokemonDetailScreen(
                        viewModel = detailViewModel,
                        pokemonId = selectedPokemonId!!,
                        onBackClick = {
                            selectedPokemonId = null
                        }
                    )
                }
            }
        }
    }
}