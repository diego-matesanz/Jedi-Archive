# Sistema Multi-Agente - Jedi Archive

Sistema inteligente de orquestación de agentes especializados para desarrollo Android.

## 📋 Índice

1. [¿Qué es el Sistema de Agentes?](#qué-es-el-sistema-de-agentes)
2. [Arquitectura](#arquitectura)
3. [Agentes Disponibles](#agentes-disponibles)
4. [Cómo Funciona](#cómo-funciona)
5. [Uso](#uso)
6. [Ejemplos](#ejemplos)
7. [Extensión](#extensión)

## ¿Qué es el Sistema de Agentes?

Un **sistema multi-agente** donde diferentes agentes especializados colaboran para resolver peticiones complejas de desarrollo. Cada agente tiene:

- **Rol específico**: Arquitecto, Desarrollador, Diseñador, etc.
- **Conocimiento especializado**: Expertise en su dominio
- **Capacidad de colaboración**: Puede trabajar con otros agentes
- **Autonomía**: Toma decisiones dentro de su ámbito

### Ventajas

✅ **Separación de responsabilidades**: Cada agente se enfoca en su especialidad
✅ **Escalabilidad**: Fácil añadir nuevos agentes
✅ **Reutilización**: Agentes compartidos entre proyectos
✅ **Colaboración inteligente**: Agentes trabajan juntos automáticamente
✅ **Context-aware**: Mantiene historial de conversación

## Arquitectura

### Estructura de Módulos

```
JediArchive/
├── app/                              # Aplicación principal
│   ├── AgentSystemInitializer.kt    # Inicialización del sistema
│   └── AgentSystemDemo.kt           # Ejemplos de uso
│
├── core/
│   └── agents/                       # Core del sistema de agentes
│       ├── Agent.kt                  # Interface base
│       ├── BaseAgent.kt              # Implementación base
│       ├── AgentMessage.kt           # Sistema de mensajería
│       ├── AgentOrchestrator.kt      # Orquestador central
│       └── AgentRegistry.kt          # Registro de agentes
│
└── agents/                           # Agentes especializados
    ├── architect/                    # Arquitecto Senior
    ├── engineer/                     # Ingeniero Senior
    ├── uiux/                         # Diseñador UI/UX
    ├── api/                          # Integrador de APIs
    └── qa/                           # QA/Testing Engineer
```

### Diagrama de Flujo

```
Usuario
   ↓
   │ "Quiero implementar una lista de personajes"
   ↓
AgentOrchestrator
   ↓
   │ 1. Analiza el mensaje
   │ 2. Selecciona agente apropiado
   ↓
EngineerAgent (seleccionado)
   ↓
   │ "Para implementar necesito colaboración"
   ↓
Colaboración con:
   ├─→ ArchitectAgent (estructura)
   ├─→ UiUxAgent (diseño)
   └─→ ApiAgent (datos)
   ↓
Respuesta integrada al Usuario
```

## Agentes Disponibles

### 1. **Architect Agent** 🏗️

**Responsabilidades:**
- Definir arquitectura Clean Architecture
- Diseñar estructura multimódulo
- Establecer patrones (MVVM, Repository, UseCase)
- Garantizar SOLID principles
- Definir flujo de dependencias

**Keywords:** `arquitectura`, `estructura`, `módulo`, `capa`, `clean architecture`, `patrón`

**Priority:** 10 (máxima para decisiones arquitectónicas)

**Colabora con:** Engineer, API Agent, UI/UX

---

### 2. **Engineer Agent** 💻

**Responsabilidades:**
- Implementar features con Jetpack Compose
- Desarrollar ViewModels y state management
- Crear Composables siguiendo Material 3
- Implementar navegación
- Optimizar rendimiento

**Keywords:** `implementar`, `crear`, `desarrollar`, `feature`, `pantalla`, `composable`, `viewmodel`

**Priority:** 8

**Colabora con:** Todos los agentes (es el implementador principal)

---

### 3. **UI/UX Agent** 🎨

**Responsabilidades:**
- Definir Design System
- Diseñar flujos de usuario
- Garantizar accesibilidad (WCAG)
- Aplicar Material Design 3
- Crear componentes visuales reutilizables

**Keywords:** `diseño`, `ui`, `ux`, `color`, `tipografía`, `accesibilidad`, `design system`

**Priority:** 9

**Colabora con:** Architect, Engineer

---

### 4. **API Integration Agent** 🌐

**Responsabilidades:**
- Integrar APIs REST
- Configurar Retrofit/Ktor
- Definir DTOs y Mappers
- Implementar Repository pattern
- Manejo de errores de red

**Keywords:** `api`, `retrofit`, `network`, `dto`, `repository`, `endpoint`, `integrar`

**Priority:** 9

**Colabora con:** Architect, Engineer, QA

---

### 5. **QA/Testing Agent** 🧪

**Responsabilidades:**
- Definir estrategia de testing
- Implementar unit tests
- Crear integration tests
- Desarrollar UI tests con Compose
- Garantizar cobertura y calidad

**Keywords:** `test`, `testing`, `prueba`, `qa`, `mock`, `junit`, `verificar`

**Priority:** 7

**Colabora con:** Engineer, API Agent

## Cómo Funciona

### 1. Enrutamiento Inteligente

El **AgentOrchestrator** analiza el mensaje del usuario y selecciona el agente apropiado mediante:

- **Keywords matching**: Busca palabras clave en el mensaje
- **Priority scoring**: Si múltiples agentes pueden manejar, usa el de mayor prioridad
- **Semantic analysis**: Analiza contexto e intención

```kotlin
// Ejemplo interno
"Quiero crear una pantalla de login"
  → Keywords detectadas: ["crear", "pantalla"]
  → Candidatos: EngineerAgent (8), UiUxAgent (9)
  → Seleccionado: EngineerAgent (más keywords matched)
```

### 2. Colaboración entre Agentes

Cuando un agente necesita ayuda, solicita colaboración:

```kotlin
// Dentro de EngineerAgent
return collaborationResponse(
    content = "Para implementar necesito...",
    collaborators = listOf(
        AgentId.ARCHITECT,  // Define estructura
        AgentId.UIUX,       // Provee diseño
        AgentId.API_INTEGRATION  // Provee datos
    )
)
```

El orquestador automáticamente:
1. Coordina la colaboración
2. Pasa el contexto a cada agente colaborador
3. Combina las respuestas
4. Retorna una respuesta unificada

### 3. Memoria de Conversación

El sistema mantiene historial de conversaciones:

```kotlin
conversationMemory[conversationId] = [
    AgentMessage("Define arquitectura", sender=null, ...),
    AgentMessage("Propuesta de arquitectura...", sender=ARCHITECT, ...),
    AgentMessage("Implementa lista", sender=null, ...),
    // ... más mensajes
]
```

Esto permite a los agentes:
- Mantener contexto entre peticiones
- Referencias a decisiones previas
- Colaboración coherente

### 4. Análisis de Intención

Cada agente analiza la intención del mensaje:

```kotlin
enum class Intent {
    CREATE,     // "crear", "implementar", "generar"
    MODIFY,     // "modificar", "actualizar", "cambiar"
    REVIEW,     // "revisar", "analizar", "validar"
    DESIGN,     // "diseñar", "diseño"
    TEST,       // "test", "probar"
    DOCUMENT,   // "documentar"
    QUERY       // Consultas generales
}
```

Esto permite respuestas contextuales y apropiadas.

## Uso

### Inicialización

```kotlin
// En tu Application o Activity
val orchestrator = AgentSystemInitializer.initialize()
```

### Procesar Peticiones

```kotlin
// Ejemplo simple
suspend fun handleUserRequest(message: String) {
    orchestrator.processUserRequest(message).collect { response ->
        println(response.content)
    }
}
```

### Con Manejo de Estados

```kotlin
orchestrator.processUserRequest("Define la arquitectura").collect { response ->
    when (response.status) {
        ResponseStatus.SUCCESS -> {
            // Mostrar resultado
            showResult(response.content)
        }
        ResponseStatus.IN_PROGRESS -> {
            // Mostrar loading
            showLoading(response.content)
        }
        ResponseStatus.NEEDS_COLLABORATION -> {
            // El orquestador maneja automáticamente
            showInfo("Coordinando agentes...")
        }
        ResponseStatus.ERROR -> {
            // Mostrar error
            showError(response.content)
        }
        ResponseStatus.NEEDS_USER_INPUT -> {
            // Solicitar más información
            promptUser(response.content)
        }
    }
}
```

### Historial de Conversación

```kotlin
// Obtener historial
val history = orchestrator.getConversationHistory(conversationId)

// Limpiar conversaciones antiguas
orchestrator.clearOldConversations(olderThanMillis = 3600000)
```

## Ejemplos

### Ejemplo 1: Definir Arquitectura

```kotlin
orchestrator.processUserRequest(
    "Necesito definir la arquitectura del proyecto con Clean Architecture"
).collect { response ->
    // ArchitectAgent es seleccionado automáticamente
    println(response.content)
    // Output: Propuesta detallada de arquitectura multimódulo
}
```

### Ejemplo 2: Implementar Feature

```kotlin
orchestrator.processUserRequest(
    "Implementa una pantalla de lista de personajes con Compose"
).collect { response ->
    // EngineerAgent es seleccionado
    // Automáticamente colabora con ArchitectAgent, UiUxAgent y ApiAgent
    println(response.content)
}
```

### Ejemplo 3: Integrar API

```kotlin
orchestrator.processUserRequest(
    "Integra la API de Star Wars (SWAPI) con Retrofit"
).collect { response ->
    // ApiIntegrationAgent es seleccionado
    println(response.content)
    // Output: Setup completo de Retrofit, DTOs, Repository, Mappers
}
```

### Ejemplo 4: Crear Design System

```kotlin
orchestrator.processUserRequest(
    "Define un design system con Material 3 y modo oscuro"
).collect { response ->
    // UiUxAgent es seleccionado
    println(response.content)
    // Output: Paleta de colores, tipografía, componentes, etc.
}
```

### Ejemplo 5: Testing

```kotlin
orchestrator.processUserRequest(
    "Necesito tests unitarios para el ViewModel de personajes"
).collect { response ->
    // QaTestingAgent es seleccionado
    println(response.content)
    // Output: Estrategia de testing y ejemplos de tests
}
```

## Extensión

### Crear un Nuevo Agente

1. **Crear módulo:**
```bash
mkdir -p agents/custom/src/main/java/com/diego/matesanz/jedi/archive/agents/custom
```

2. **Implementar agente:**
```kotlin
class CustomAgent : BaseAgent() {
    override val id = AgentId.CUSTOM  // Añadir a enum
    override val name = "Custom Agent"
    override val description = "Descripción de capacidades"
    override val role = AgentRole.CUSTOM  // Añadir a enum
    override val triggerKeywords = listOf("palabra1", "palabra2")
    override val priority = 5

    override val systemPrompt = """
        Eres un agente especializado en...
        [Definir comportamiento y expertise]
    """.trimIndent()

    override suspend fun process(message: AgentMessage): AgentResponse {
        // Implementar lógica
        return successResponse("Respuesta del agente")
    }
}
```

3. **Registrar agente:**
```kotlin
// En AgentSystemInitializer
registry.register(CustomAgent())
```

### Mejores Prácticas

✅ **Especialización clara**: Cada agente debe tener un dominio bien definido
✅ **Keywords descriptivos**: Facilita el enrutamiento correcto
✅ **System prompt detallado**: Define bien el comportamiento esperado
✅ **Colaboración explícita**: Declara cuándo necesitas otros agentes
✅ **Estados apropiados**: Usa ResponseStatus correctamente
✅ **Contexto enriquecido**: Pasa información relevante en colaboraciones

### Evitar

❌ Agentes con responsabilidades sobrelapadas
❌ Keywords demasiado genéricos
❌ Lógica de negocio en el orquestador
❌ Colaboración circular infinita
❌ Ignorar el contexto de conversación

## Beneficios del Sistema

### Para Desarrollo

- **Claridad**: Cada agente tiene responsabilidades claras
- **Modularidad**: Fácil añadir/modificar agentes
- **Testabilidad**: Cada agente es testeable independientemente
- **Reutilización**: Agentes reutilizables en otros proyectos

### Para el Equipo

- **Organización**: Estructura clara de roles
- **Colaboración**: Framework para trabajo en equipo
- **Escalabilidad**: Crece con el proyecto
- **Documentación viva**: Los agentes documentan su dominio

### Para el Proyecto

- **Arquitectura robusta**: Decisiones arquitectónicas consistentes
- **Código de calidad**: Múltiples agentes revisando
- **Desarrollo ágil**: Respuestas rápidas y especializadas
- **Mantenibilidad**: Sistema organizado y extensible

## Roadmap

### Próximas Mejoras

- [ ] Integración con LLMs reales (Claude, GPT)
- [ ] Persistencia de conversaciones
- [ ] UI para interactuar con agentes
- [ ] Analytics del sistema
- [ ] Agente de Seguridad
- [ ] Agente de Performance
- [ ] System de plugins para agentes externos

---

**Versión:** 1.0.0
**Proyecto:** Jedi Archive
**Autor:** Sistema Multi-Agente
**Licencia:** MIT
