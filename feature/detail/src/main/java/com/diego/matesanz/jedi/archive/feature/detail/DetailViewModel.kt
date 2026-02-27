package com.diego.matesanz.jedi.archive.feature.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diego.matesanz.jedi.archive.core.domain.model.*
import com.diego.matesanz.jedi.archive.core.domain.usecase.GetEntityDetailUseCase
import com.diego.matesanz.jedi.archive.core.navigation.EntityTypeArg
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel para la pantalla de detalle
 */
class DetailViewModel(
    private val getEntityDetailUseCase: GetEntityDetailUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    /**
     * Carga el detalle de una entidad
     */
    fun loadDetail(entityType: EntityType, id: String) {
        _uiState.value = DetailUiState.Loading

        viewModelScope.launch {
            val result = getEntityDetailUseCase(entityType, id)

            _uiState.value = result.fold(
                onSuccess = { entity ->
                    DetailUiState.Success(entity)
                },
                onFailure = { exception ->
                    DetailUiState.Error(
                        exception.message ?: "Unknown error occurred"
                    )
                }
            )
        }
    }

    /**
     * Reintenta cargar el detalle
     */
    fun retry(entityType: EntityType, id: String) {
        loadDetail(entityType, id)
    }

    /**
     * Convierte una entidad en secciones de información
     */
    fun getDetailSections(entity: SwapiEntity): List<DetailSection> {
        return when (entity) {
            is Person -> getPersonSections(entity)
            is Planet -> getPlanetSections(entity)
            is Species -> getSpeciesSections(entity)
            is Starship -> getStarshipSections(entity)
            is Vehicle -> getVehicleSections(entity)
            is Film -> getFilmSections(entity)
            else -> emptyList()
        }
    }

    private fun getPersonSections(person: Person): List<DetailSection> {
        return listOf(
            DetailSection(
                title = "Basic Information",
                items = listOf(
                    DetailItem.TextField("Birth Year", person.birthYear),
                    DetailItem.TextField("Gender", person.gender),
                    DetailItem.TextField("Height", "${person.height} cm"),
                    DetailItem.TextField("Mass", "${person.mass} kg")
                )
            ),
            DetailSection(
                title = "Appearance",
                items = listOf(
                    DetailItem.TextField("Eye Color", person.eyeColor),
                    DetailItem.TextField("Hair Color", person.hairColor),
                    DetailItem.TextField("Skin Color", person.skinColor)
                )
            ),
            DetailSection(
                title = "Relations",
                items = buildList {
                    person.homeworldId?.let {
                        add(DetailItem.NavigableTag("Homeworld", it, EntityTypeArg.PLANET))
                    }
                    if (person.speciesIds.isNotEmpty()) {
                        add(DetailItem.NavigableTagList(
                            "Species",
                            person.speciesIds.map { DetailItem.NavigableTag("Species", it, EntityTypeArg.SPECIES) }
                        ))
                    }
                    if (person.filmIds.isNotEmpty()) {
                        add(DetailItem.NavigableTagList(
                            "Films",
                            person.filmIds.map { DetailItem.NavigableTag("Film", it, EntityTypeArg.FILM) }
                        ))
                    }
                    if (person.starshipIds.isNotEmpty()) {
                        add(DetailItem.NavigableTagList(
                            "Starships",
                            person.starshipIds.map { DetailItem.NavigableTag("Starship", it, EntityTypeArg.STARSHIP) }
                        ))
                    }
                    if (person.vehicleIds.isNotEmpty()) {
                        add(DetailItem.NavigableTagList(
                            "Vehicles",
                            person.vehicleIds.map { DetailItem.NavigableTag("Vehicle", it, EntityTypeArg.VEHICLE) }
                        ))
                    }
                }
            )
        )
    }

    private fun getPlanetSections(planet: Planet): List<DetailSection> {
        return listOf(
            DetailSection(
                title = "Planetary Data",
                items = listOf(
                    DetailItem.TextField("Climate", planet.climate),
                    DetailItem.TextField("Terrain", planet.terrain),
                    DetailItem.TextField("Diameter", "${planet.diameter} km"),
                    DetailItem.TextField("Gravity", planet.gravity),
                    DetailItem.TextField("Population", planet.population)
                )
            ),
            DetailSection(
                title = "Orbital Characteristics",
                items = listOf(
                    DetailItem.TextField("Rotation Period", "${planet.rotationPeriod} hours"),
                    DetailItem.TextField("Orbital Period", "${planet.orbitalPeriod} days"),
                    DetailItem.TextField("Surface Water", "${planet.surfaceWater}%")
                )
            ),
            DetailSection(
                title = "Relations",
                items = buildList {
                    if (planet.residentIds.isNotEmpty()) {
                        add(DetailItem.NavigableTagList(
                            "Residents",
                            planet.residentIds.map { DetailItem.NavigableTag("Resident", it, EntityTypeArg.PERSON) }
                        ))
                    }
                    if (planet.filmIds.isNotEmpty()) {
                        add(DetailItem.NavigableTagList(
                            "Films",
                            planet.filmIds.map { DetailItem.NavigableTag("Film", it, EntityTypeArg.FILM) }
                        ))
                    }
                }
            )
        )
    }

    private fun getSpeciesSections(species: Species): List<DetailSection> {
        return listOf(
            DetailSection(
                title = "Classification",
                items = listOf(
                    DetailItem.TextField("Classification", species.classification),
                    DetailItem.TextField("Designation", species.designation),
                    DetailItem.TextField("Language", species.language)
                )
            ),
            DetailSection(
                title = "Physical Characteristics",
                items = listOf(
                    DetailItem.TextField("Average Height", "${species.averageHeight} cm"),
                    DetailItem.TextField("Average Lifespan", "${species.averageLifespan} years"),
                    DetailItem.TextField("Eye Colors", species.eyeColors),
                    DetailItem.TextField("Hair Colors", species.hairColors),
                    DetailItem.TextField("Skin Colors", species.skinColors)
                )
            ),
            DetailSection(
                title = "Relations",
                items = buildList {
                    species.homeworldId?.let {
                        add(DetailItem.NavigableTag("Homeworld", it, EntityTypeArg.PLANET))
                    }
                    if (species.peopleIds.isNotEmpty()) {
                        add(DetailItem.NavigableTagList(
                            "People",
                            species.peopleIds.map { DetailItem.NavigableTag("Person", it, EntityTypeArg.PERSON) }
                        ))
                    }
                    if (species.filmIds.isNotEmpty()) {
                        add(DetailItem.NavigableTagList(
                            "Films",
                            species.filmIds.map { DetailItem.NavigableTag("Film", it, EntityTypeArg.FILM) }
                        ))
                    }
                }
            )
        )
    }

    private fun getStarshipSections(starship: Starship): List<DetailSection> {
        return listOf(
            DetailSection(
                title = "Specifications",
                items = listOf(
                    DetailItem.TextField("Model", starship.model),
                    DetailItem.TextField("Class", starship.starshipClass),
                    DetailItem.TextField("Manufacturer", starship.manufacturer),
                    DetailItem.TextField("Cost", "${starship.costInCredits} credits")
                )
            ),
            DetailSection(
                title = "Technical Details",
                items = listOf(
                    DetailItem.TextField("Length", "${starship.length} m"),
                    DetailItem.TextField("Max Speed", "${starship.maxAtmospheringSpeed} km/h"),
                    DetailItem.TextField("Hyperdrive Rating", starship.hyperdriveRating),
                    DetailItem.TextField("MGLT", starship.mglt),
                    DetailItem.TextField("Cargo Capacity", "${starship.cargoCapacity} kg"),
                    DetailItem.TextField("Consumables", starship.consumables)
                )
            ),
            DetailSection(
                title = "Crew",
                items = listOf(
                    DetailItem.TextField("Crew", starship.crew),
                    DetailItem.TextField("Passengers", starship.passengers)
                )
            ),
            DetailSection(
                title = "Relations",
                items = buildList {
                    if (starship.pilotIds.isNotEmpty()) {
                        add(DetailItem.NavigableTagList(
                            "Pilots",
                            starship.pilotIds.map { DetailItem.NavigableTag("Pilot", it, EntityTypeArg.PERSON) }
                        ))
                    }
                    if (starship.filmIds.isNotEmpty()) {
                        add(DetailItem.NavigableTagList(
                            "Films",
                            starship.filmIds.map { DetailItem.NavigableTag("Film", it, EntityTypeArg.FILM) }
                        ))
                    }
                }
            )
        )
    }

    private fun getVehicleSections(vehicle: Vehicle): List<DetailSection> {
        return listOf(
            DetailSection(
                title = "Specifications",
                items = listOf(
                    DetailItem.TextField("Model", vehicle.model),
                    DetailItem.TextField("Class", vehicle.vehicleClass),
                    DetailItem.TextField("Manufacturer", vehicle.manufacturer),
                    DetailItem.TextField("Cost", "${vehicle.costInCredits} credits")
                )
            ),
            DetailSection(
                title = "Technical Details",
                items = listOf(
                    DetailItem.TextField("Length", "${vehicle.length} m"),
                    DetailItem.TextField("Max Speed", "${vehicle.maxAtmospheringSpeed} km/h"),
                    DetailItem.TextField("Cargo Capacity", "${vehicle.cargoCapacity} kg"),
                    DetailItem.TextField("Consumables", vehicle.consumables)
                )
            ),
            DetailSection(
                title = "Crew",
                items = listOf(
                    DetailItem.TextField("Crew", vehicle.crew),
                    DetailItem.TextField("Passengers", vehicle.passengers)
                )
            ),
            DetailSection(
                title = "Relations",
                items = buildList {
                    if (vehicle.pilotIds.isNotEmpty()) {
                        add(DetailItem.NavigableTagList(
                            "Pilots",
                            vehicle.pilotIds.map { DetailItem.NavigableTag("Pilot", it, EntityTypeArg.PERSON) }
                        ))
                    }
                    if (vehicle.filmIds.isNotEmpty()) {
                        add(DetailItem.NavigableTagList(
                            "Films",
                            vehicle.filmIds.map { DetailItem.NavigableTag("Film", it, EntityTypeArg.FILM) }
                        ))
                    }
                }
            )
        )
    }

    private fun getFilmSections(film: Film): List<DetailSection> {
        return listOf(
            DetailSection(
                title = "Film Information",
                items = listOf(
                    DetailItem.TextField("Episode", film.episodeId.toString()),
                    DetailItem.TextField("Director", film.director),
                    DetailItem.TextField("Producer", film.producer),
                    DetailItem.TextField("Release Date", film.releaseDate)
                )
            ),
            DetailSection(
                title = "Opening Crawl",
                items = listOf(
                    DetailItem.TextField("", film.openingCrawl)
                )
            ),
            DetailSection(
                title = "Relations",
                items = buildList {
                    if (film.characterIds.isNotEmpty()) {
                        add(DetailItem.NavigableTagList(
                            "Characters",
                            film.characterIds.map { DetailItem.NavigableTag("Character", it, EntityTypeArg.PERSON) }
                        ))
                    }
                    if (film.planetIds.isNotEmpty()) {
                        add(DetailItem.NavigableTagList(
                            "Planets",
                            film.planetIds.map { DetailItem.NavigableTag("Planet", it, EntityTypeArg.PLANET) }
                        ))
                    }
                    if (film.starshipIds.isNotEmpty()) {
                        add(DetailItem.NavigableTagList(
                            "Starships",
                            film.starshipIds.map { DetailItem.NavigableTag("Starship", it, EntityTypeArg.STARSHIP) }
                        ))
                    }
                    if (film.vehicleIds.isNotEmpty()) {
                        add(DetailItem.NavigableTagList(
                            "Vehicles",
                            film.vehicleIds.map { DetailItem.NavigableTag("Vehicle", it, EntityTypeArg.VEHICLE) }
                        ))
                    }
                    if (film.speciesIds.isNotEmpty()) {
                        add(DetailItem.NavigableTagList(
                            "Species",
                            film.speciesIds.map { DetailItem.NavigableTag("Species", it, EntityTypeArg.SPECIES) }
                        ))
                    }
                }
            )
        )
    }
}
