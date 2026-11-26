package br.edu.uninter.verdeemcasa.model

import java.util.UUID

enum class LightLevel(val label: String, val description: String) {
    SOL_PLENO("Sol Pleno", "Luz solar direta por pelo menos 4 horas ao dia."),
    MEIA_SOMBRA("Meia-Sombra", "Claridade indireta ou sol suave no início da manhã."),
    SOMBRA("Sombra", "Ambiente interno bem iluminado sem sol direto.");

    companion object {
        fun fromName(name: String): LightLevel {
            return entries.firstOrNull { it.name == name } ?: MEIA_SOMBRA
        }
    }
}

data class Plant(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val lightLevel: LightLevel,
    val wateringIntervalDays: Int,
    val lastWateredDate: String,
    val notes: String = ""
)
