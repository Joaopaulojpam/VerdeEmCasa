package br.edu.uninter.verdeemcasa.model

data class PlantValidationResult(
    val nameError: String? = null,
    val intervalError: String? = null
) {
    val isValid: Boolean
        get() = nameError == null && intervalError == null
}

object PlantValidator {
    fun validate(nameInput: String, intervalInput: String): PlantValidationResult {
        val trimmedName = nameInput.trim()
        val nameError = when {
            trimmedName.isEmpty() -> "Informe o nome da planta."
            trimmedName.length < 2 -> "O nome deve ter pelo menos 2 caracteres."
            trimmedName.length > 60 -> "O nome pode ter no máximo 60 caracteres."
            else -> null
        }

        val parsedDays = intervalInput.trim().toIntOrNull()
        val intervalError = when {
            intervalInput.trim().isEmpty() -> "Informe o intervalo de rega em dias."
            parsedDays == null -> "Digite apenas números inteiros (ex: 3)."
            parsedDays < 1 || parsedDays > 90 -> "O intervalo deve estar entre 1 e 90 dias."
            else -> null
        }

        return PlantValidationResult(
            nameError = nameError,
            intervalError = intervalError
        )
    }
}
