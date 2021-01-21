# Java_Work-With-Files_Practice

Pácticas con archivos y directorios, se ven diferentes posibilidades que nos ofrece el jdk para manipularlos, obtener info, leerlos, ...

  - ***Los programas deben recibir argumentos***

  - ***Existen ficheros asociados a los diferentes programas, para usarlos de ejemplo, fácil de localizarlos según su nombre (relacionado a la práctica).***

## Tecnología Utilizada

- JDK 11

## Árbol de Prácticas

- <a href="https://github.com/Javi3Code/Java_Work-With-Files_Practice/tree/master/src/com/jeycode/listoffilesanddirectories">Listado de ficheros y directorios</a>

  - Enunciado de la práctica: *Desarrolla un programa en Java que muestre por defecto un listado de los ficheros y directorios que contiene el directorio desde el que se ejecuta el programa. El nombre de los directorios se mostrará con / al final para indicarlo, de los ficheros se mostrará también su tamaño en kB, y de ambos se mostrará la fecha de última modificación (en formato ejemplo 28 de Septiembre de 2020 19:50) y los permisos en formato Linux (ejemplo r-x). Por último se mostrará un total con el espacio ocupado por todos los ficheros de ese directorio (en MB).
En caso de que se le pase como argumento una ruta de un fichero o directorio, se mostrará de qué se trata (si fichero o directorio) y si es un directorio mostrará los ficheros y directorios que contiene con toda la información descrita en el apartado anterior.
En caso de que se le pase como argumento “-all”, mostrará también la información del primer nivel de directorios. Es decir, si el directorio contiene tres directorios, te mostrará la información de lo que contiene el directorio (actual o pasado como argumento) y la información de lo que contienen los otros tres directorios.
Realiza una gestión adecuada de las excepciones indicando al usuario los errores que puedan suceder en la ejecución, y documenta adecuadamente el programa.*

- <a href="https://github.com/Javi3Code/Java_Work-With-Files_Practice/tree/master/src/com/jeycode/readfiles">Lectura de ficheros</a>

  - Esta práctica contiene tres soluciones con diferente complejidad.
    - 1.Usando FileReader, leyendo carácter a carácter.
    - 2.Usando BufferReader e indexOf para la búsqueda
    - 3.Usando BufferReader y sin usar indexOf para la búsqueda.
    
  - Enunciado de la práctica: *Desarrolla un programa en Java que busque un texto dado en un fichero de texto (pasado como argumento), y que para cada aparición indique la línea y la columna en que se encuentra.
Realiza una gestión adecuada de las excepciones indicando al usuario los errores que puedan suceder en la ejecución, y documenta adecuadamente el programa.*
  
- <a href="https://github.com/Javi3Code/Java_Work-With-Files_Practice/tree/master/src/com/jeycode/joinfilecontent">Unión de ficheros</a>

  - Enunciado de la práctica: *Desarrolla un programa en Java que reciba tres argumentos (la ruta de un fichero, la ruta de otro fichero, y una ruta a un directorio) y cree un fichero nuevo en el directorio pasado como tercer argumento que sea el resultado de añadir al contenido del primer fichero el contenido del segundo fichero.
Para realizar la operación el programa deberá comprobar previamente que no existe ningún error en los argumentos (los ficheros existen, el directorio existe…).
Realiza una gestión adecuada de las excepciones indicando al usuario los errores que puedan suceder en la ejecución, y documenta adecuadamente el programa.*

- <a href="https://github.com/Javi3Code/Java_Work-With-Files_Practice/tree/master/src/com/jeycode/jencodingfilecontent">Recodificación de ficheros</a>

  - Enunciado de la práctica: *Desarrolla un programa en Java que de un fichero codificado en UTF-8 que recibe como entrada (ya sea como argumento o pidiendo al usuario que introduzca la ruta) genere dos ficheros de salida con el mismo contenido del fichero de entrada, pero codificados en ISO-8859-1 y UTF-16. El fichero de entrada debe tener al menos varias vocales con tilde y la letra ñ en alguna línea.
Comprueba que el programa funciona correctamente revisando el fichero con un programa que muestre la codificación del fichero de texto.
Realiza una gestión adecuada de las excepciones indicando al usuario los errores que puedan suceder en la ejecución, y documenta adecuadamente el programa.*

- <a href="https://github.com/Javi3Code/Java_Work-With-Files_Practice/tree/master/src/com/jeycode/rwcreatetemp">Modificación de ficheros de texto</a>

  - Esta práctica contiene dos soluciones.
    - 1.Usando createTempFile.
    - 2.Sin usar createTemFile.

  - Enunciado de la práctica: *Desarrolla un programa en Java que reciba un argumento (la ruta de un fichero de texto) y realice diversos cambios en su contenido como eliminar secuencias de espacios al principio de la línea, sustituir secuencias de espacios en otros lugares por un solo espacio, y hacer que todas las líneas empiecen por mayúscula. Para las transformaciones en el texto, puedes utilizar funcionalidad de la clase Character. Además, el fichero original debe quedar también guardado, añadiendo el sufijo _old al nombre.
Realiza una gestión adecuada de las excepciones indicando al usuario los errores que puedan suceder en la ejecución, y documenta adecuadamente el programa.*
