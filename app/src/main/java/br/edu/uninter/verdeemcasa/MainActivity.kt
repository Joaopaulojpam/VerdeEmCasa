package br.edu.uninter.verdeemcasa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import br.edu.uninter.verdeemcasa.data.PlantRepository
import br.edu.uninter.verdeemcasa.ui.VerdeEmCasaApp
import br.edu.uninter.verdeemcasa.ui.theme.VerdeEmCasaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val repository = PlantRepository(applicationContext)
        setContent {
            VerdeEmCasaTheme {
                VerdeEmCasaApp(repository = repository)
            }
        }
    }
}
