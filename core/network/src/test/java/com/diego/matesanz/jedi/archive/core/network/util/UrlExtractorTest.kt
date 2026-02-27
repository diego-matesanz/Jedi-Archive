package com.diego.matesanz.jedi.archive.core.network.util

import org.junit.Assert.assertEquals
import org.junit.Test

class UrlExtractorTest {

    @Test
    fun `extractId extracts ID from standard SWAPI URL`() {
        // Given
        val url = "https://swapi.dev/api/people/1/"

        // When
        val id = UrlExtractor.extractId(url)

        // Then
        assertEquals("1", id)
    }

    @Test
    fun `extractId extracts ID from URL without trailing slash`() {
        // Given
        val url = "https://swapi.dev/api/people/42"

        // When
        val id = UrlExtractor.extractId(url)

        // Then
        assertEquals("42", id)
    }

    @Test
    fun `extractId handles different entity types`() {
        // When & Then
        assertEquals("5", UrlExtractor.extractId("https://swapi.dev/api/people/5/"))
        assertEquals("10", UrlExtractor.extractId("https://swapi.dev/api/planets/10/"))
        assertEquals("3", UrlExtractor.extractId("https://swapi.dev/api/films/3/"))
        assertEquals("15", UrlExtractor.extractId("https://swapi.dev/api/starships/15/"))
        assertEquals("7", UrlExtractor.extractId("https://swapi.dev/api/vehicles/7/"))
        assertEquals("2", UrlExtractor.extractId("https://swapi.dev/api/species/2/"))
    }

    @Test
    fun `extractIds extracts IDs from list of URLs`() {
        // Given
        val urls = listOf(
            "https://swapi.dev/api/people/1/",
            "https://swapi.dev/api/people/2/",
            "https://swapi.dev/api/people/3/"
        )

        // When
        val ids = UrlExtractor.extractIds(urls)

        // Then
        assertEquals(listOf("1", "2", "3"), ids)
    }

    @Test
    fun `extractIds handles empty list`() {
        // Given
        val urls = emptyList<String>()

        // When
        val ids = UrlExtractor.extractIds(urls)

        // Then
        assertEquals(emptyList<String>(), ids)
    }

    @Test
    fun `extractIds handles mixed entity types`() {
        // Given
        val urls = listOf(
            "https://swapi.dev/api/people/1/",
            "https://swapi.dev/api/planets/5/",
            "https://swapi.dev/api/films/3/"
        )

        // When
        val ids = UrlExtractor.extractIds(urls)

        // Then
        assertEquals(listOf("1", "5", "3"), ids)
    }

    @Test
    fun `extractId handles multi-digit IDs`() {
        // Given
        val url = "https://swapi.dev/api/people/999/"

        // When
        val id = UrlExtractor.extractId(url)

        // Then
        assertEquals("999", id)
    }
}
