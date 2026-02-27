#!/usr/bin/env python3
"""
Script para generar mapeos estáticos de imágenes desde Wookieepedia
Usa la MediaWiki API oficial de starwars.fandom.com

Usage: python3 generate_image_mappings.py
Output: Archivos Kotlin con mapeos de imágenes para cada tipo de entidad
"""

import requests
import json
import time
from typing import Optional, Dict, List
from pathlib import Path

# Configuración
SWAPI_BASE = "https://swapi.dev/api"
WIKI_API = "https://starwars.fandom.com/api.php"
OUTPUT_DIR = Path("../core/data/src/main/java/com/diego/matesanz/jedi/archive/core/data/util")
PACKAGE = "com.diego.matesanz.jedi.archive.core.data.util"

# Rate limiting
REQUEST_DELAY = 0.5  # segundos entre requests

class WikiImageFetcher:
    """Obtiene imágenes de Wookieepedia usando MediaWiki API"""

    def __init__(self):
        self.session = requests.Session()
        self.session.headers.update({
            'User-Agent': 'JediArchiveApp/1.0 (Educational Project)'
        })

    def search_article(self, name: str) -> Optional[str]:
        """Busca el artículo más relevante en Wookieepedia"""
        params = {
            'action': 'opensearch',
            'search': name,
            'limit': 1,
            'format': 'json'
        }

        try:
            response = self.session.get(WIKI_API, params=params, timeout=10)
            data = response.json()

            if data[1]:  # Si hay resultados
                return data[1][0]  # Primer resultado
            return None
        except Exception as e:
            print(f"  ⚠️  Error buscando '{name}': {e}")
            return None

    def get_image_url(self, title: str) -> Optional[str]:
        """Obtiene la URL de la imagen principal de un artículo"""
        params = {
            'action': 'query',
            'titles': title,
            'prop': 'pageimages',
            'format': 'json',
            'pithumbsize': 500
        }

        try:
            response = self.session.get(WIKI_API, params=params, timeout=10)
            data = response.json()

            pages = data.get('query', {}).get('pages', {})
            page = list(pages.values())[0]

            thumbnail = page.get('thumbnail', {})
            return thumbnail.get('source')
        except Exception as e:
            print(f"  ⚠️  Error obteniendo imagen para '{title}': {e}")
            return None

    def fetch_image(self, name: str) -> Optional[str]:
        """Busca y obtiene la imagen para una entidad"""
        # Primero intenta con el nombre exacto
        image_url = self.get_image_url(name)

        if image_url:
            return image_url

        # Si no funciona, busca el artículo
        article_title = self.search_article(name)
        if not article_title:
            return None

        return self.get_image_url(article_title)


class SWAPIFetcher:
    """Obtiene datos de SWAPI"""

    def __init__(self):
        self.session = requests.Session()

    def fetch_all(self, endpoint: str) -> List[Dict]:
        """Obtiene todas las entidades de un endpoint con paginación"""
        results = []
        url = f"{SWAPI_BASE}/{endpoint}/"

        while url:
            try:
                response = self.session.get(url, timeout=10)
                data = response.json()
                results.extend(data['results'])
                url = data.get('next')
                time.sleep(REQUEST_DELAY)
            except Exception as e:
                print(f"  ⚠️  Error fetching {endpoint}: {e}")
                break

        return results


def extract_id_from_url(url: str) -> str:
    """Extrae el ID de una URL de SWAPI"""
    # https://swapi.dev/api/planets/1/ -> "1"
    return url.rstrip('/').split('/')[-1]


def generate_kotlin_mapping(entity_type: str, mappings: Dict[str, str], display_name: str):
    """Genera un archivo Kotlin con el mapeo de imágenes"""

    filename = f"{entity_type.capitalize()}ImageMapping.kt"
    filepath = OUTPUT_DIR / filename

    # Crear contenido del archivo
    content = f'''package {PACKAGE}

/**
 * Mapeo estático de IDs de {display_name} SWAPI a URLs de imágenes de Wookieepedia
 * Generado automáticamente desde MediaWiki API
 * Última actualización: {time.strftime("%Y-%m-%d")}
 * Total de imágenes: {len(mappings)}
 */
object {entity_type.capitalize()}ImageMapping {{
    val imageUrls = mapOf(
'''

    # Añadir mapeos ordenados por ID
    for id_str in sorted(mappings.keys(), key=lambda x: int(x)):
        url = mappings[id_str]
        content += f'        "{id_str}" to "{url}",\n'

    content += '''    )
}
'''

    # Escribir archivo
    filepath.parent.mkdir(parents=True, exist_ok=True)
    with open(filepath, 'w', encoding='utf-8') as f:
        f.write(content)

    print(f"  ✅ Generado: {filename} ({len(mappings)} imágenes)")


def process_entities(entity_type: str, display_name: str):
    """Procesa todas las entidades de un tipo"""
    print(f"\n{'='*60}")
    print(f"Procesando {display_name}...")
    print(f"{'='*60}")

    swapi = SWAPIFetcher()
    wiki = WikiImageFetcher()

    # Obtener entidades de SWAPI
    print(f"📥 Obteniendo {entity_type} de SWAPI...")
    entities = swapi.fetch_all(entity_type)
    print(f"  ✅ {len(entities)} {entity_type} encontrados")

    # Obtener imágenes de Wookieepedia
    print(f"🖼️  Buscando imágenes en Wookieepedia...")
    mappings = {}
    success_count = 0

    for entity in entities:
        # Films usan 'title' en lugar de 'name'
        name = entity.get('title') or entity.get('name')
        url = entity['url']
        entity_id = extract_id_from_url(url)

        print(f"  Buscando: {name} (ID: {entity_id})...", end='')

        image_url = wiki.fetch_image(name)
        time.sleep(REQUEST_DELAY)  # Rate limiting

        if image_url:
            mappings[entity_id] = image_url
            success_count += 1
            print(f" ✅")
        else:
            print(f" ❌ No encontrada")

    # Generar archivo Kotlin
    print(f"\n📝 Generando archivo Kotlin...")
    if mappings:
        generate_kotlin_mapping(entity_type, mappings, display_name)
        print(f"  📊 Tasa de éxito: {success_count}/{len(entities)} ({100*success_count/len(entities):.1f}%)")
    else:
        print(f"  ⚠️  No se encontraron imágenes para {entity_type}")

    return success_count, len(entities)


def main():
    print("""
╔═══════════════════════════════════════════════════════════╗
║   JEDI ARCHIVE - Generador de Mapeos de Imágenes         ║
║   Fuente: Wookieepedia (MediaWiki API)                   ║
╚═══════════════════════════════════════════════════════════╝
    """)

    # Tipos de entidades a procesar (excluyendo people, ya lo tenemos)
    entity_types = [
        ('planets', 'Planetas'),
        ('species', 'Especies'),
        ('starships', 'Naves Espaciales'),
        ('vehicles', 'Vehículos'),
        ('films', 'Películas')
    ]

    total_success = 0
    total_entities = 0

    for entity_type, display_name in entity_types:
        success, total = process_entities(entity_type, display_name)
        total_success += success
        total_entities += total

    print(f"\n{'='*60}")
    print(f"✅ PROCESO COMPLETADO")
    print(f"{'='*60}")
    print(f"Total de imágenes obtenidas: {total_success}/{total_entities}")
    print(f"Tasa de éxito global: {100*total_success/total_entities:.1f}%")
    print(f"\nArchivos generados en: {OUTPUT_DIR}")
    print(f"\nSiguientes pasos:")
    print(f"1. Revisar los archivos generados")
    print(f"2. Actualizar ImageUrlProvider.kt para usar los nuevos mapeos")
    print(f"3. Rebuild y probar la app")


if __name__ == "__main__":
    main()
