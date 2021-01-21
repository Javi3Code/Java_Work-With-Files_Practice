package com.jeycode.readfiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadWithBufferUsingIndexOf
{

      private static Integer LINE = 0;

      /**
       * 
       * Method main Metodo ejecutable de la clase
       *
       * @param args Argumentos del sistema
       * @throws Exception
       */
      public static void main(String[] args) throws Exception
      {

            if (args == null || args.length > 2)
            {
                  System.out.println("****La primera línea es la 0, la primera columna es la 0****\n");

                  var message = "Error: Argumento 1-> Nombre del fichero, Argumento 2-> Cadena a buscar ";
                  throw new Exception(message);
            }

            operation(args);

      }

      /**
       * 
       * Method operation: Metodo donde realizamos las operaciones clave. Obtenemos el
       * Path, y obtenemos un flujo de Strings con las lineas del fichero. Se
       * inicializa el lineMap, lo cargamos con cada linea. Por ultimo, buscamos.
       *
       * @param args : Array de argumentos.
       */
      private static void operation(String[] args)
      {
            var path = Paths.get(args[0]);
            try (var lines = Files.lines(path))
            {
                  lines.forEach(line-> searchAndPrint(line,args[1]));

            }
            catch (IOException ex)
            {

                  System.out.println("Imposible de leer el fichero, seguro que existe?, puede que esté corrupto");
            }
      }

      /**
       * 
       * Method searchAndPrint: Buscamos e imprimimos. Por cada linea, buscamos la
       * coincidencia.
       *
       * @param line : Linea actual.
       * @param args : Texto a buscar.
       */
      private static void searchAndPrint(String line,String args)
      {
            if (line.contains(args))
            {
                  search(line,args);
            }
            LINE++;
      }

      /**
       * 
       * Method search: Por cada linea buscamos la coincidencia. Se crea un index y se
       * comprueba las veces que sea necesario, se imprime si se encuentra.
       *
       * @param txtLine : Linea.
       * @param args    : Cadena a buscar.
       */
      private static void search(String txtLine,String args)
      {
            var index = txtLine.indexOf(args);
            while (index != -1)
            {
                  System.out.println("Encontrado en la línea : " + LINE + "// Columna: " + index);
                  index = txtLine.indexOf(args,index + 1);
            }
      }
}
