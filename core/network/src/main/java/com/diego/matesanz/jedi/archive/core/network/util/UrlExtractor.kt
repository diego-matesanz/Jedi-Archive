package com.diego.matesanz.jedi.archive.core.network.util

/**
 * Utilidad para extraer IDs de URLs de SWAPI
 *
 * SWAPI devuelve URLs como: "https://swapi.dev/api/people/1/"
 * Necesitamos extraer el ID: "1"
 */
object UrlExtractor {

    /**
     * Extrae el ID de una URL de SWAPI
     * @param url URL completa de SWAPI
     * @return ID extraído o cadena vacía si no se puede extraer
     */
    fun extractId(url: String): String {
        return url.trimEnd('/').substringAfterLast('/')
    }

    /**
     * Extrae IDs de una lista de URLs
     */
    fun extractIds(urls: List<String>): List<String> {
        return urls.map { extractId(it) }
    }
}
