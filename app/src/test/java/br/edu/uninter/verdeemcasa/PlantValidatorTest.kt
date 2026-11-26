package br.edu.uninter.verdeemcasa

import br.edu.uninter.verdeemcasa.model.PlantValidator
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class PlantValidatorTest {

    @Test
    fun validPlantInput_returnsValidResult() {
        val result = PlantValidator.validate(nameInput = "Jiboia", intervalInput = "3")
        assertTrue(result.isValid)
        assertNull(result.nameError)
        assertNull(result.intervalError)
    }

    @Test
    fun blankName_returnsNameError() {
        val result = PlantValidator.validate(nameInput = "   ", intervalInput = "4")
        assertFalse(result.isValid)
        assertNotNull(result.nameError)
        assertNull(result.intervalError)
    }

    @Test
    fun nonNumericInterval_returnsIntervalError() {
        val result = PlantValidator.validate(nameInput = "Manjericão", intervalInput = "abc")
        assertFalse(result.isValid)
        assertNull(result.nameError)
        assertNotNull(result.intervalError)
    }

    @Test
    fun outOfRangeInterval_returnsIntervalError() {
        val zeroResult = PlantValidator.validate(nameInput = "Samambaia", intervalInput = "0")
        val hugeResult = PlantValidator.validate(nameInput = "Samambaia", intervalInput = "120")
        assertFalse(zeroResult.isValid)
        assertFalse(hugeResult.isValid)
        assertNotNull(zeroResult.intervalError)
        assertNotNull(hugeResult.intervalError)
    }
}
