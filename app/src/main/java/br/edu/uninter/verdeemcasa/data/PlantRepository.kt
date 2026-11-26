package br.edu.uninter.verdeemcasa.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import br.edu.uninter.verdeemcasa.model.Plant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "verde_em_casa_prefs"
)

class PlantRepository(private val context: Context) {

    private val plantsKey = stringPreferencesKey("saved_plants_json")

    val plantsFlow: Flow<List<Plant>> = context.dataStore.data.map { preferences ->
        val rawJson = preferences[plantsKey].orEmpty()
        PlantJsonSerializer.fromJson(rawJson)
    }

    suspend fun addPlant(plant: Plant) {
        context.dataStore.edit { preferences ->
            val current = PlantJsonSerializer.fromJson(preferences[plantsKey].orEmpty())
            val updated = listOf(plant) + current
            preferences[plantsKey] = PlantJsonSerializer.toJson(updated)
        }
    }

    suspend fun markWateredToday(plantId: String, dateFormatted: String = currentDateFormatted()) {
        context.dataStore.edit { preferences ->
            val current = PlantJsonSerializer.fromJson(preferences[plantsKey].orEmpty())
            val updated = current.map { plant ->
                if (plant.id == plantId) {
                    plant.copy(lastWateredDate = dateFormatted)
                } else {
                    plant
                }
            }
            preferences[plantsKey] = PlantJsonSerializer.toJson(updated)
        }
    }

    suspend fun deletePlant(plantId: String) {
        context.dataStore.edit { preferences ->
            val current = PlantJsonSerializer.fromJson(preferences[plantsKey].orEmpty())
            val updated = current.filterNot { it.id == plantId }
            preferences[plantsKey] = PlantJsonSerializer.toJson(updated)
        }
    }

    companion object {
        fun currentDateFormatted(): String {
            val formatter = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))
            return formatter.format(Date())
        }
    }
}
