# Jedi Archive

Aplicación Android con Sistema Multi-Agente inteligente para desarrollo colaborativo.

## 🚀 Características

- **Sistema Multi-Agente**: Orquestación inteligente de agentes especializados
- **5 Agentes Especializados**: Arquitecto, Engineer, UI/UX, API Integration, QA
- **Clean Architecture**: Estructura multimódulo por capas
- **Jetpack Compose**: UI moderna con Material 3
- **Colaboración Inteligente**: Los agentes trabajan juntos automáticamente

## 📚 Documentación

Ver [AGENT_SYSTEM.md](./AGENT_SYSTEM.md) para documentación completa del sistema de agentes.

## 🏗️ Arquitectura

```
:app                    # Aplicación principal + Sistema de agentes
:core:agents           # Framework de agentes (base)
:agents:architect      # Agente Arquitecto Senior
:agents:engineer       # Agente Engineer Senior
:agents:uiux          # Agente UI/UX Designer
:agents:api           # Agente API Integration
:agents:qa            # Agente QA/Testing
```

## 🎯 Uso Rápido

```kotlin
// Inicializar sistema
val orchestrator = AgentSystemInitializer.initialize()

// Procesar petición
orchestrator.processUserRequest(
    "Implementa una lista de personajes con Compose"
).collect { response ->
    println(response.content)
}
```

## 🛠️ Tecnologías

- Kotlin
- Jetpack Compose
- Material Design 3
- Clean Architecture
- Multi-módulo
- Coroutines & Flow

## 📦 Instalación

```bash
./gradlew build
```

## 🧪 Demo

```bash
./gradlew :app:run
# O ejecuta AgentSystemDemo.main()
```

## 👥 Agentes Disponibles

1. **Architect** - Define arquitectura y estructura
2. **Engineer** - Implementa features con Compose
3. **UI/UX** - Diseña sistema de diseño y UX
4. **API Integration** - Integra APIs REST
5. **QA/Testing** - Crea tests y garantiza calidad

## 📄 Licencia

MIT
