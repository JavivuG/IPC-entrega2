# IPC Entrega 2: biblioteca con búsqueda e importación

Aplicación de escritorio en Java Swing para gestionar una biblioteca de libros, repartida en tres vistas: inicio, edición y búsqueda.

Asignatura **Interacción Persona-Computadora**, segunda entrega, 2.º curso, 2.º cuatrimestre, curso 2022-2023. Grado en Ingeniería Informática, Universidad de Valladolid.

## Qué hace

- **Vista inicial**: muestra el número de libros, el precio medio y la proporción de leídos frente a no leídos, y lleva a las otras dos vistas.
- **Vista de edición**: alta, edición y borrado de libros (título, ISBN, descripción, estado, precio y "leído"), con validación de los datos y un botón de ayuda con los atajos.
- **Vista de búsqueda**: busca por título, por ISBN o por ambos, sin distinguir mayúsculas ni tildes, y ordena por orden de inserción, alfabéticamente (ascendente o descendente) o por precio (ascendente o descendente).
- **Importar y exportar** la biblioteca en ficheros `.txt` con una línea por libro y los campos separados por `;`: `título;descripción;isbn;estado;precio;leído`. Al importar, la aplicación indica cuántos libros ha añadido de cuántas entradas.

## Tecnologías

- Java 11
- Swing, con la apariencia Nimbus, `JFileChooser` y formularios del editor visual de NetBeans (`.form`)
- Proyecto NetBeans con Ant (`build.xml`, `nbproject/`)

## Lo que aprendí / Aspectos técnicos destacados

- **Navegación entre ventanas**: `GestorVistas` libera la vista actual (`dispose`) antes de abrir la siguiente, la centra en pantalla y le fija un tamaño mínimo.
- **MVC con un modelo compartido**: `Biblioteca` es el modelo único, con la lista completa y la lista resultado de la búsqueda, y cada vista tiene su propio controlador (`ControladorInicial`, `ControladorEdicion`, `ControladorBusqueda`).
- **Búsqueda tolerante**: el texto se normaliza con `java.text.Normalizer` (NFD) y se eliminan las marcas diacríticas (`\p{M}`), así que "codigo" encuentra "Código".
- **Ordenación por precio** con `Comparable<ReferenciaLibro>`, y orden alfabético con `Arrays.sort` y `Collections.reverse`.
- **E/S robusta**: lectura y escritura con `try-with-resources` (`BufferedReader`, `BufferedWriter`). La importación comprueba que cada línea tenga seis campos y un precio numérico, y no admite ISBN duplicados.

## Estructura del repositorio

```text
IPC-entrega2/
├── build.xml  manifest.mf  nbproject/   # Proyecto NetBeans/Ant
└── src/
    ├── images/                          # Iconos de los botones (PNG)
    └── com/entrega2/
        ├── main/Main.java
        ├── modelo/Biblioteca.java  ReferenciaLibro.java
        └── vista/GestorVistas.java
                  VistaInicial  VistaEdicion  VistaBusqueda   (.java + .form)
                  ControladorInicial  ControladorEdicion  ControladorBusqueda
```

## Requisitos

- JDK 11 o superior.
- Opcional: NetBeans o Apache Ant para usar `build.xml`.

## Cómo compilar y ejecutar

Abre la carpeta como proyecto en NetBeans y ejecútalo, o usa Ant:

```sh
ant run
```

Sin IDE ni Ant, con el JDK. Hay que copiar los iconos al classpath, porque se cargan como recurso `/images/...`:

```sh
javac --release 11 -d build/classes $(find src -name '*.java')
cp -r src/images build/classes/
java -cp build/classes com.entrega2.main.Main
```

La compilación con `javac` está comprobada con OpenJDK 17. `ant run` y el arranque de la interfaz gráfica no se han probado.

## Autoría

Javier García González (`jagarci`).

---

Parte de [UVa-Projects](https://github.com/JavivuG/UVa-Projects).
