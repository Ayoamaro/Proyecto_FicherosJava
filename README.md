# Proyecto Ficheros Java

#### Acceso a Ficheros

Realiza en Java un programa que permita trabajar el **Acceso a Ficheros**. Este programa deberá tener una serie de opciones disponibles para el usuario:

- Comprobar que una carpeta o fichero existe,
- Crear, eliminar, renombrar, mover fichero y carpeta
- Visualizar y modificar el contenido de un fichero de texto
- Listar ficheros o carpetas de una carpeta,
- Visualizar nombre y ruta de un fichero,
- Otros que consideres interesantes

**Es obligatorio utilizar interfaces gráficas. Además se deberá añadir incluir tu nombre en el proyecto, el cuál deberá aparecer en cada pantalla del programa.**

Una posible interfaz para este programa podría ser la siguiente:

![](https://github.com/Ayoamaro/Proyecto_FicherosJava/blob/main/docs/images/interfaz_accesoficheros.PNG?raw=true)



#### Acceso Aleatorio

Realiza en Java un programa que permita trabajar el **Acceso Aleatorio**. Este programa deberá tener una serie de opciones disponibles para el usuario:

- Insertamos datos de los equipos en un fichero aleatorio.
- Crearemos un fichero que tiene en cada registro los siguientes campos:
  - **codEquipo** INTEGER IDENTITY PRIMARY KEY, 
  - **nomEquipo** VARCHAR(40), 
  - **codLiga** CHAR(5) DEFAULT 'PDN',
  - **localidad** VARCHAR(60), 
  - **internacional** BIT DEFAULT 0

- Haz método de insertar datos, introducir los datos desde 'Arrays'. La longitud de cada registro deberá ser siempre la misma. Cada entero ocupa 4 bytes, cada carácter Unicode ocupa 2 bytes y el 'Boolean' 1 byte.
- Haz método de visualizar el contenido del fichero.
- Haz un método que reciba un identificador del equipo (también indica la posición del registro en la que se encuentra ese equipo) y nos visualice todos los datos de ese equipo. Utilizar el acceso directo o aleatorio.
- Haz otro método que reciba un identificador del equipo y poder modificar las copas ganadas. Utilizar el acceso directo o aleatorio. Necesitaremos conocer en que byte comienza ese registro y el desplazamiento hasta las copas ganadas

Una posible interfaz para este programa podría ser la siguiente:

![](https://github.com/Ayoamaro/Proyecto_FicherosJava/blob/main/docs/images/interfaz_accesoaleatorio.PNG?raw=true)



#### Acceso XML

Práctica sobre el **Acceso a ficheros XML** sobre el fichero Equipos.xml utilizando JDOM:

- Visualizarlo
- Modificar las copas ganadas de un determinado equipo según el nombre de equipo determinado
- Eliminar un equipo determinado
- Añadir un contrato en un equipo determinado según el nombre de equipo (añadir el nombre del
- Futbolista con las fechas de inicio y fin del contrato)
- Escribirlo en otro fichero.

Para la realización de esta práctica se utilizará la siguiente dependencia que podremos añadir a nuestro **pom.xml** y permitirá usar el JDOM.

```
<!-- JDOM -->
<dependency>
	<groupId>org.jdom</groupId>
	<artifactId>jdom2</artifactId>
	<version>2.0.6</version>
</dependency>
```

Una posible interfaz para este programa podría ser la siguiente:

![](https://github.com/Ayoamaro/Proyecto_FicherosJava/blob/main/docs/images/interfaz_accesoXML.PNG?raw=true)