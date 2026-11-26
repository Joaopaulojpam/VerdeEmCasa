package br.edu.uninter.verdeemcasa.data

import br.edu.uninter.verdeemcasa.model.LightLevel
import br.edu.uninter.verdeemcasa.model.Plant
import org.json.JSONArray
import org.json.JSONObject

object PlantJsonSerializer {
    fun toJson(plants: List<Plant>): String {
        val array = JSONArray()
        plants.forEach { plant ->
            val obj = JSONObject().apply {
                put("id", plant.id)
                put("name", plant.name)
                put("lightLevel", plant.lightLevel.name)
                put("wateringIntervalDays", plant.wateringIntervalDays)
                put("lastWateredDate", plant.lastWateredDate)
                put("notes", plant.notes)
            }
            array.put(obj)
        }
        return array.toString()
    }

    fun fromJson(json: String): List<Plant> {
        if (json.isBlank()) return emptyList()
        return try {
            val array = JSONArray(json)
            buildList {
                for (i in 0 until array.length()) {
                    val obj = array.getJSONObject(i)
                    add(
                        Plant(
                            id = obj.optString("id"),
                            name = obj.optString("name"),
                            lightLevel = LightLevel.fromName(obj.optString("lightLevel")),
                            wateringIntervalDays = obj.optInt("wateringIntervalDays", 3),
                            lastWateredDate = obj.optString("lastWateredDate", "Ainda não regada"),
                            notes = obj.optString("notes", "")
                        )
                    )
                }
            }
        } catch (_: Exception) {
            emptyList()
        }
    }
}
