# Rokys - Automatización UI con Selenium + TestNG

Proyecto de automatización web para el sitio de Rokys, implementado en Java con Selenium WebDriver y TestNG.

## Objetivo

Validar flujos críticos de la web:

- Login negativo (credenciales inválidas)
- Login positivo
- Flujo de compra hasta Checkout (sin completar pago)

## Stack Tecnológico

- Java 21
- Maven
- Selenium Java 4.18.1
- TestNG 7.9.0
- SLF4J Simple (scope test)


## Requisitos Previos

1. Tener instalado JDK 21.
2. Tener Maven instalado (o usar el wrapper de Maven incluido en el proyecto).
3. Tener instalado Chrome o Edge.
4. Conexión a internet para acceder a https://rokys.com/.

Nota: Selenium 4 usa Selenium Manager para resolver el driver del navegador automáticamente en la mayoría de casos.

## Instalación

Desde la carpeta raíz del proyecto (`Rokys`):

```bash
mvn clean install -DskipTests
```

Si prefieres usar Maven Wrapper en Windows:

```powershell
.\mvnw.cmd clean install -DskipTests
```

## Ejecución de Pruebas

Ejecutar toda la suite:

```bash
mvn test
```

Ejecutar solo la clase principal de pruebas:

```bash
mvn -Dtest=tests.RokysTest test
```

## Navegadores Soportados

El proyecto soporta:

- `chrome`
- `edge`

La selección de navegador está preparada en `DriverFactory` y el test usa un parámetro TestNG con valor por defecto `chrome`.

## Casos de Prueba Implementados

En `RokysTest`:

1. `testLoginNegativo`
2. `testLoginPositivo`
3. `testFlujoCompraCompletoSinPago`

## Consideraciones Importantes

- Algunos elementos del sitio son dinámicos (anuncios/modales). El proyecto incluye manejo de ese comportamiento en las Page Objects.
- Las credenciales de pruebas no deberían quedar hardcodeadas en repositorios públicos.
- Para un entorno productivo, se recomienda externalizar datos sensibles a variables de entorno o archivos de configuración no versionados.

