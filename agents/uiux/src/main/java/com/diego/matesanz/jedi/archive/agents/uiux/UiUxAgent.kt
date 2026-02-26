package com.diego.matesanz.jedi.archive.agents.uiux

import com.diego.matesanz.jedi.archive.core.agents.*

/**
 * Senior UI/UX Designer Agent
 *
 * Responsabilidades:
 * - Definir Design System (colores, tipografía, espaciados)
 * - Diseñar flujos de usuario óptimos
 * - Garantizar accesibilidad (WCAG)
 * - Crear componentes reutilizables
 * - Diseñar estados (loading, error, empty)
 * - Aplicar Material Design 3 principles
 * - Optimizar usabilidad
 */
class UiUxAgent : BaseAgent() {

    override val id = AgentId.UIUX

    override val name = "Senior UI/UX Designer"

    override val description = """
        Diseñador UI/UX senior especializado en diseño de experiencias móviles Android.
        Experto en Material Design 3, accesibilidad, usabilidad y Design Systems.
        Define la identidad visual y garantiza UX óptima.
    """.trimIndent()

    override val role = AgentRole.DESIGNER

    override val triggerKeywords = listOf(
        "diseño",
        "ui",
        "ux",
        "interfaz",
        "visual",
        "color",
        "tipografía",
        "spacing",
        "theme",
        "estilo",
        "accesibilidad",
        "usabilidad",
        "experiencia",
        "usuario",
        "material",
        "design system",
        "componente visual",
        "layout",
        "flujo"
    )

    override val priority = 9

    override val systemPrompt = """
        Eres un Senior UI/UX Designer con expertise en:
        - Material Design 3 (Material You)
        - Design Systems
        - Accesibilidad (WCAG 2.1 AA)
        - Usabilidad móvil
        - Design tokens
        - Atomic design
        - User flows

        Principios que aplicas:

        **Material Design 3:**
        - Dynamic color (color schemes basados en primary)
        - Type scale (Display, Headline, Title, Body, Label)
        - Elevation y shadows sutiles
        - Motion y transitions
        - Componentes M3 (Cards, Buttons, FAB, etc.)

        **Accesibilidad:**
        - Contraste mínimo 4.5:1 para texto
        - Touch targets mínimo 48dp
        - Content descriptions
        - Soporte para lectores de pantalla
        - Navegación con teclado/D-pad

        **Usabilidad:**
        - Jerarquía visual clara
        - Feedback inmediato
        - Estados visibles (loading, error, success)
        - Consistencia en patrones
        - Reducción de carga cognitiva

        **Design System que defines:**
        ```
        :core:designsystem
        ├── Color.kt (Primary, Secondary, Tertiary + semantic)
        ├── Typography.kt (Type scale completo)
        ├── Spacing.kt (4dp, 8dp, 16dp, 24dp, 32dp...)
        ├── Shape.kt (Rounded corners)
        ├── Theme.kt (Light/Dark themes)
        └── components/ (Buttons, Cards, TextFields...)
        ```

        **Design Tokens:**
        - Colors: semantic naming (onSurface, onPrimary...)
        - Typography: role-based (displayLarge, bodyMedium...)
        - Spacing: consistent scale
        - Elevation: levels 0-5

        Cuando diseñas:
        1. Consideras el contexto de uso
        2. Priorizas accesibilidad
        3. Mantienes consistencia
        4. Documentas decisiones

        Colaboras con:
        - Engineer: implementa tus diseños
        - Architect: encaja en la estructura
    """.trimIndent()

    override suspend fun process(message: AgentMessage): AgentResponse {
        val intent = analyzeIntent(message)

        return when (intent) {
            Intent.DESIGN -> handleDesignRequest(message)
            Intent.CREATE -> handleDesignSystemCreation(message)
            Intent.REVIEW -> handleDesignReview(message)
            Intent.MODIFY -> handleDesignModification(message)
            Intent.QUERY -> handleDesignQuery(message)
            else -> handleGeneralDesignRequest(message)
        }
    }

    private fun handleDesignRequest(message: AgentMessage): AgentResponse {
        return successResponse("""
            # Design System - Jedi Archive

            Propongo un diseño inspirado en el universo Star Wars pero moderno y limpio:

            ## 🎨 Paleta de Colores

            **Theme Galáctico:**
            - Primary: Azul profundo espacial (#1A237E)
            - Secondary: Dorado Jedi (#FFB300)
            - Tertiary: Gris metálico (#546E7A)
            - Error: Rojo Sith (#D32F2F)

            **Semantic Colors:**
            - Success: Verde (#388E3C)
            - Warning: Ámbar (#FFA000)
            - Info: Cyan (#00ACC1)

            **Surfaces:**
            - Background: #FAFAFA (light) / #121212 (dark)
            - Surface: #FFFFFF (light) / #1E1E1E (dark)

            ## 📝 Tipografía

            **Font Family:** Roboto (system default Material)

            **Type Scale:**
            - Display Large: 57sp (títulos principales)
            - Headline Medium: 28sp (encabezados sección)
            - Title Large: 22sp (títulos cards)
            - Body Large: 16sp (texto principal)
            - Body Medium: 14sp (texto secundario)
            - Label Medium: 12sp (labels, captions)

            ## 📐 Spacing System

            ```kotlin
            object Spacing {
                val xs = 4.dp
                val sm = 8.dp
                val md = 16.dp
                val lg = 24.dp
                val xl = 32.dp
                val xxl = 48.dp
            }
            ```

            ## 🔲 Componentes

            **Cards:**
            - Elevation: 2dp
            - Corner radius: 12dp
            - Padding interno: 16dp

            **Buttons:**
            - Primary: Filled button con primary color
            - Secondary: Outlined button
            - Height: 48dp (touch target)

            **Lists:**
            - Item height: min 56dp
            - Dividers: 1dp, color outline

            ## ♿ Accesibilidad

            ✓ Contraste WCAG AA mínimo
            ✓ Touch targets 48dp+
            ✓ Content descriptions completos
            ✓ Soporte dark mode
            ✓ Text scaling support

            ¿Quieres que implemente este Design System?
        """.trimIndent(),
            metadata = mapOf(
                "design_system" to "material3_custom",
                "theme" to "galactic_jedi",
                "accessibility" to "wcag_aa"
            )
        )
    }

    private fun handleDesignSystemCreation(message: AgentMessage): AgentResponse {
        return collaborationResponse(
            content = """
                # Creación del Design System

                Voy a crear el módulo :core:designsystem con:

                ## Archivos a crear:

                **1. Color.kt**
                - Esquema de colores light/dark
                - Semantic colors
                - Color roles Material 3

                **2. Typography.kt**
                - Type scale completo
                - Font families
                - Line heights

                **3. Spacing.kt**
                - Sistema de espaciado
                - Padding/Margin scales

                **4. Shape.kt**
                - Corner radius definitions
                - Shape themes

                **5. Theme.kt**
                - JediArchiveTheme composable
                - Light/Dark theme switching
                - Material3 theme integration

                **6. Components/**
                - Buttons customizados
                - Cards predefinidas
                - TextFields con estilo

                ## Necesito colaboración:

                - **Architect**: Define estructura del módulo
                - **Engineer**: Implementa los componentes Compose

                ¿Procedo con la creación?
            """.trimIndent(),
            collaborators = listOf(AgentId.ARCHITECT, AgentId.ENGINEER)
        )
    }

    private fun handleDesignReview(message: AgentMessage): AgentResponse {
        return successResponse("""
            Revisaré el diseño actual evaluando:

            **UI Visual:**
            ✓ Consistencia de colores
            ✓ Jerarquía tipográfica
            ✓ Espaciado consistente
            ✓ Alineación y grid

            **UX:**
            ✓ Flujos intuitivos
            ✓ Feedback visual
            ✓ Estados claros
            ✓ Error handling

            **Accesibilidad:**
            ✓ Contraste de colores
            ✓ Tamaños de touch
            ✓ Descriptions
            ✓ Navegación

            **Material Design:**
            ✓ Adherencia a guidelines
            ✓ Componentes M3
            ✓ Motion apropiado

            ¿Qué pantalla/componente quieres que revise?
        """.trimIndent())
    }

    private fun handleDesignModification(message: AgentMessage): AgentResponse {
        return collaborationResponse(
            content = """
                Para modificar el diseño:

                1. Evaluaré el impacto en el sistema
                2. Mantendré consistencia
                3. Verificaré accesibilidad
                4. Documentaré cambios

                Necesitaré que Engineer implemente los cambios.

                ¿Qué aspecto del diseño quieres modificar?
            """.trimIndent(),
            collaborators = listOf(AgentId.ENGINEER)
        )
    }

    private fun handleDesignQuery(message: AgentMessage): AgentResponse {
        return successResponse("""
            Como UI/UX Designer puedo ayudarte con:

            🎨 Design Systems completos
            🖌️ Paletas de colores accesibles
            📝 Tipografía y hierarchy
            📐 Spacing y layouts
            ♿ Accesibilidad (WCAG)
            📱 UX flows y user journeys
            🎭 Theming (light/dark)
            🧩 Componentes reutilizables
            ✨ Micro-interactions

            ¿Qué aspecto de diseño te interesa?
        """.trimIndent())
    }

    private fun handleGeneralDesignRequest(message: AgentMessage): AgentResponse {
        return successResponse("""
            He analizado tu petición de diseño.

            Para crear el mejor diseño necesito saber:
            - ¿Qué tipo de app es? (contenido, tono, target)
            - ¿Hay brand guidelines existentes?
            - ¿Preferencias de estilo? (minimalista, bold, playful)
            - ¿Requisitos de accesibilidad específicos?

            Diseñaré un sistema completo y usable.
        """.trimIndent())
    }
}
