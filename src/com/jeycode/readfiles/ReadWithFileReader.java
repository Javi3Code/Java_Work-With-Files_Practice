package com.jeycode.readfiles;

import java.io.FileReader;
import java.io.IOException;

public class ReadWithFileReader
{

      private static final char LINE_BREAK = '\n';
      private static Integer LINE = 0;
      private static Integer COLUMN = 0;
      private static int charAt_Index = 0;
      private static char first_character;
      private static int args_length;
      private static StringBuilder buffer;

      /**
       * 
       * Method main Metodo ejecutable de la clase
       *
       * @param args Argumentos del sistema
       * @throws Exception
       */
      public static void main(String[] args) throws Exception
      {
            checkArguments(args);
            initOperation(args);
      }

      private static void checkArguments(String[] args) throws Exception
      {
            if (args == null || args.length > 2)
            {
                  System.out.println("****La primera línea es la 0, la primera columna es la 0****\n");

                  var message = "Error: Argumento 1-> Nombre del fichero, Argumento 2-> Cadena a buscar ";
                  throw new Exception(message);
            }
      }

      /**
       * 
       * Method operation: Metodo donde realizamos las operaciones clave. Obtenemos el
       * fichero y, si todo va bien, se lee y se busca.
       *
       * @param args : Array de argumentos.
       */
      private static void initOperation(String[] args)
      {

            try (var fileReader = new FileReader(args[0]))
            {
                  var character = fileReader.read();
                  first_character = (char)character;
                  args_length = args[1].length();
                  buffer = new StringBuilder();
                  whilePosibleSearchResults(fileReader,character,args[1]);

            }
            catch (IOException ex)
            {
                  System.out.println("Imposible de leer el fichero, seguro que existe?, puede que esté corrupto");
            }
      }

      /**
       * 
       * Method whilePosibleSearch: Mientras no sea el fin de fichero buscamos la
       * cadena.
       *
       * @param fileReader  : Objeto usado para leer.
       * @param character   : Caracter leido.
       * @param strToSearch : Cadena a buscar.
       * @throws IOException
       */
      private static void whilePosibleSearchResults(FileReader fileReader,int character,String strToSearch) throws IOException
      {
            while (character != -1)
            {
                  var realCharacter = (char)character;
                  if (realCharacter == LINE_BREAK)
                  {
                        resetConstantsAndIncrementLine(realCharacter);
                  }
                  else
                  {
                        keepLookingAndPrintIfFound(strToSearch,realCharacter);
                  }
                  character = fileReader.read();
            }
      }

      /**
       * 
       * Method keepLookingAndPrintIfFound : Metodo que hace la lavor clave del
       * programa. Buscar la cadena paso por paso.
       *
       * @param strToSearch
       * @param realCharacter
       */
      private static void keepLookingAndPrintIfFound(String strToSearch,char realCharacter)
      {
            if (strToSearch.charAt(charAt_Index) == realCharacter)
            {
                  buffer.append(realCharacter);
                  charAt_Index++;
                  if (charAt_Index == args_length)
                  {
                        printLineAndColumn();
                        if (!foundPatternInBuffer(strToSearch))
                        {
                              resetCharAtIndex(realCharacter);
                        }
                  }
            }
            else
            {
                  resetCharAtIndex(realCharacter);
            }
            COLUMN++;
      }

      private static boolean foundPatternInBuffer(String strToSearch)
      {
            var found = false;
            var bufferStr = buffer.toString();
            var index = bufferStr.indexOf(bufferStr.charAt(0),1);
            if (index != -1)
            {
                  var newBufferStr = bufferStr.substring(index,bufferStr.length());
                  if (strToSearch.startsWith(newBufferStr))
                  {
                        buffer = new StringBuilder(newBufferStr);
                        charAt_Index = newBufferStr.length();
                        found = true;
                  }
            }
            return found;
      }

      /**
       * 
       * Method printLineAndColumn
       *
       * @param args
       */
      private static void printLineAndColumn()
      {
            System.out.println("Encontrado en la línea " + LINE + ", columna " + startColumn());
      }

      /**
       * 
       * Method startColumn
       *
       * @param args
       * @return
       */
      private static int startColumn()
      {
            return COLUMN - args_length + 1;
      }

      /**
       * 
       * Method resetConstantsAndIncrementLine : Reseteamos las constantes e
       * incrementamos el valor de la linea.
       *
       * @param args
       * @param realCharacter
       */
      private static void resetConstantsAndIncrementLine(char realCharacter)
      {
            resetCharAtIndex(realCharacter);
            COLUMN = 0;
            LINE++;
      }

      /**
       * 
       * Method resetCharAtIndex : reseteamos charAtIndex, si el caracter actual es
       * igual al inicial de la cadena se resetea a 1.
       *
       * @param args
       * @param realCharacter
       */
      private static void resetCharAtIndex(char realCharacter)
      {
            charAt_Index = first_character == realCharacter ? 1 : 0;
            buffer = new StringBuilder();

      }

}
