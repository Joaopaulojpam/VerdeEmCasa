package br.edu.uninter.verdeemcasa.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import br.edu.uninter.verdeemcasa.data.PlantRepository
import br.edu.uninter.verdeemcasa.model.LightLevel
import br.edu.uninter.verdeemcasa.model.Plant
import br.edu.uninter.verdeemcasa.model.PlantValidator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPlantScreen(
    onBackClick: () -> Unit,
    onSavePlant: (Plant) -> Unit
) {
    var name by rememberSaveable { mutableStateOf("") }
    var selectedLight by rememberSaveable { mutableStateOf(LightLevel.MEIA_SOMBRA) }
    var intervalText by rememberSaveable { mutableStateOf("") }
    var notes by rememberSaveable { mutableStateOf("") }

    var nameError by rememberSaveable { mutableStateOf<String?>(null) }
    var intervalError by rememberSaveable { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cadastrar Planta", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                text = "Preencha as informações básicas para salvar sua planta localmente no dispositivo.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                    if (nameError != null) nameError = null
                },
                label = { Text("Nome da planta *") },
                placeholder = { Text("Ex: Jiboia, Espada-de-São-Jorge, Manjericão") },
                isError = nameError != null,
                supportingText = {
                    if (nameError != null) {
                        Text(nameError!!, color = MaterialTheme.colorScheme.error)
                    }
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "Luminosidade ideal",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    LightLevel.entries.forEach { level ->
                        FilterChip(
                            selected = selectedLight == level,
                            onClick = { selectedLight = level },
                            label = { Text(level.label) }
                        )
                    }
                }
                Text(
                    text = selectedLight.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            OutlinedTextField(
                value = intervalText,
                onValueChange = {
                    intervalText = it
                    if (intervalError != null) intervalError = null
                },
                label = { Text("Intervalo de rega (dias) *") },
                placeholder = { Text("Ex: 3") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = intervalError != null,
                supportingText = {
                    if (intervalError != null) {
                        Text(intervalError!!, color = MaterialTheme.colorScheme.error)
                    } else {
                        Text("Informe um número inteiro entre 1 e 90 dias.")
                    }
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Observações de cultivo (opcional)") },
                placeholder = { Text("Ex: Borrifar água nas folhas em dias secos.") },
                minLines = 3,
                maxLines = 5,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onBackClick,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancelar")
                }

                Button(
                    onClick = {
                        val validation = PlantValidator.validate(name, intervalText)
                        nameError = validation.nameError
                        intervalError = validation.intervalError

                        if (validation.isValid) {
                            val newPlant = Plant(
                                name = name.trim(),
                                lightLevel = selectedLight,
                                wateringIntervalDays = intervalText.trim().toInt(),
                                lastWateredDate = PlantRepository.currentDateFormatted(),
                                notes = notes.trim()
                            )
                            onSavePlant(newPlant)
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Filled.Check, contentDescription = null)
                    Text(" Salvar Planta")
                }
            }
        }
    }
}
