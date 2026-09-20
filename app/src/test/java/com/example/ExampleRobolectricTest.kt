package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.model.BioCategory
import com.example.data.repository.BioRepository
import com.example.data.util.StylishNameGenerator
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Trick Master", appName)
    }

    @Test
    fun `stylish name generator generates at least 100 styles`() {
        val styles = StylishNameGenerator.generateAllStyles("Trick Master")
        assertTrue("Expected at least 100 stylish Unicode versions, but got ${styles.size}", styles.size >= 100)

        // Ensure key required styles and categories exist
        val styleNames = styles.map { it.styleName }
        assertTrue("Must include bold styles", styleNames.any { it.contains("Bold", ignoreCase = true) })
        assertTrue("Must include italic styles", styleNames.any { it.contains("Italic", ignoreCase = true) })
        assertTrue("Must include script styles", styleNames.any { it.contains("Script", ignoreCase = true) })
        assertTrue("Must include gothic styles", styleNames.any { it.contains("Fraktur", ignoreCase = true) || it.contains("Gothic", ignoreCase = true) })
        assertTrue("Must include double struck styles", styleNames.any { it.contains("Double Struck", ignoreCase = true) })
        assertTrue("Must include small caps styles", styleNames.any { it.contains("Small", ignoreCase = true) })
        assertTrue("Must include circled styles", styleNames.any { it.contains("Circled", ignoreCase = true) })
        assertTrue("Must include squared styles", styleNames.any { it.contains("Squared", ignoreCase = true) })
        assertTrue("Must include decorative styles", styleNames.any { it.contains("Strikethrough", ignoreCase = true) || it.contains("Sigil", ignoreCase = true) })
        assertTrue("Must include gaming styles", styleNames.any { it.contains("Wings", ignoreCase = true) || it.contains("Clan", ignoreCase = true) || it.contains("Sniper", ignoreCase = true) })
        assertTrue("Must include royal styles", styleNames.any { it.contains("Crown", ignoreCase = true) || it.contains("Royal", ignoreCase = true) || it.contains("Sovereign", ignoreCase = true) })
        assertTrue("Must include aesthetic and cute styles", styleNames.any { it.contains("Aesthetic", ignoreCase = true) || it.contains("Angel", ignoreCase = true) || it.contains("Honey", ignoreCase = true) })
    }

    @Test
    fun `bio repository contains at least 300 bios across 12 categories`() {
        val allBios = BioRepository.getAllBios()
        assertTrue("Expected at least 300 ready-made bios, but got ${allBios.size}", allBios.size >= 300)

        val categories = BioCategory.entries
        assertEquals("Expected exactly 12 bio categories", 12, categories.size)

        // Check required categories exist
        val expectedCategoryNames = setOf(
            "Attitude", "Love", "Royal", "Sad", "Instagram", "Savage",
            "Cute", "Motivational", "Friendship", "Gaming", "Aesthetic", "Simple"
        )
        val actualCategoryNames = categories.map { it.displayName }.toSet()
        assertEquals(expectedCategoryNames, actualCategoryNames)

        // Check each category has at least 20 bios
        for (category in categories) {
            val biosInCat = BioRepository.getBiosByCategory(category)
            assertTrue(
                "Category ${category.displayName} must have at least 20 bios, but has ${biosInCat.size}",
                biosInCat.size >= 20
            )
        }
    }
}
