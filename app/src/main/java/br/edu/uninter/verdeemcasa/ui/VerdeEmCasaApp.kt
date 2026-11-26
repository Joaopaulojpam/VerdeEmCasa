package br.edu.uninter.verdeemcasa.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.edu.uninter.verdeemcasa.data.PlantRepository
import br.edu.uninter.verdeemcasa.ui.screens.AddPlantScreen
import br.edu.uninter.verdeemcasa.ui.screens.HomeScreen
import br.edu.uninter.verdeemcasa.ui.screens.PlantDetailScreen
import kotlinx.coroutines.launch

object Routes {
    const val HOME = "home"
    const val ADD_PLANT = "add_plant"
    const val DETAIL = "detail/{plantId}"

    fun detail(plantId: String): String = "detail/$plantId"
}

@Composable
fun VerdeEmCasaApp(repository: PlantRepository) {
    val navController = rememberNavController()
    val plants by repository.plantsFlow.collectAsState(initial = emptyList())
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {
        composable(Routes.HOME) {
            HomeScreen(
                plants = plants,
                snackbarHostState = snackbarHostState,
                onAddPlantClick = { navController.navigate(Routes.ADD_PLANT) },
                onPlantClick = { plantId -> navController.navigate(Routes.detail(plantId)) },
                onWaterPlantClick = { plant ->
                    scope.launch {
                        repository.markWateredToday(plant.id)
                        snackbarHostState.showSnackbar("Rega registrada para ${plant.name}!")
                    }
                }
            )
        }

        composable(Routes.ADD_PLANT) {
            AddPlantScreen(
                onBackClick = { navController.popBackStack() },
                onSavePlant = { newPlant ->
                    scope.launch {
                        repository.addPlant(newPlant)
                        navController.popBackStack()
                        snackbarHostState.showSnackbar("Planta \"${newPlant.name}\" salva com sucesso!")
                    }
                }
            )
        }

        composable(
            route = Routes.DETAIL,
            arguments = listOf(navArgument("plantId") { type = NavType.StringType })
        ) { backStackEntry ->
            val plantId = backStackEntry.arguments?.getString("plantId").orEmpty()
            val plant = plants.firstOrNull { it.id == plantId }

            PlantDetailScreen(
                plant = plant,
                onBackClick = { navController.popBackStack() },
                onWaterPlantClick = { targetPlant ->
                    scope.launch {
                        repository.markWateredToday(targetPlant.id)
                        navController.popBackStack()
                        snackbarHostState.showSnackbar("Rega atualizada para ${targetPlant.name}!")
                    }
                },
                onDeletePlantClick = { targetPlant ->
                    scope.launch {
                        repository.deletePlant(targetPlant.id)
                        navController.popBackStack()
                        snackbarHostState.showSnackbar("Planta \"${targetPlant.name}\" removida.")
                    }
                }
            )
        }
    }
}
