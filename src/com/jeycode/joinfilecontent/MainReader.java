package com.jeycode.joinfilecontent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Consumer;

public class MainReader
{

      /**
       * Constante que indica el nombre del fichero donde se va a construir, el resto
       * de constantes usadas para enviar mensajes de error.
       */
      private static final String NEW_FIC_TXT = File.separatorChar + "joinfilecontentResult.txt";
      private static final String FILES_CAN_NOT_READ = "No se pudo leer el fichero, o no se encontró el directorio, revise todos los path";
      private static final String MESSAGE_ERROR = "Debes Introducir 3 argumentos, la ruta a dos ficheros, "
                              + "y por último la ruta del directorio dónde se guardará el nuevo fichero ";
      private static String PATH_NEW_FILE;

      /**
       * 
       * Method main : Validamos los parametros que nos pasen externamente, si no son
       * los que se necesitan lanzamos error con mensaje de ayuda. En el caso de que
       * se validen realizamos una segunda validacion, usando el metodo
       * {@link #revalidated(String[])} .
       *
       * @param args
       * @throws Exception
       */
      public static void main(String[] args) throws Exception
      {
            var invalidArgs = args == null || args.length == 0 || args.length < 3;
            if (invalidArgs)
            {
                  throw new Exception(MESSAGE_ERROR);
            }

            if (revalidated(args))
            {
                  readAndWriteOperation(args);
                  System.out.println("Se ha creado el fichero nuevo con éxito, puede encontrarlo en la siguiente ruta: " + PATH_NEW_FILE);
            }
      }

      /**
       * 
       * Method readAndWriteOperation : Leemos los ficheros, creamos el nuevo fichero
       * en un determinado directorio y escribimos en el, el contenido de ambos
       * ficheros.
       * 
       *
       * @param args
       */
      private static void readAndWriteOperation(String...args)
      { // @formatter:off
												var directoryPath = args[2];
												var pathFic1 = Paths.get(args[0]);
												var pathFic2 = Paths.get(args[1]);
												PATH_NEW_FILE = directoryPath + NEW_FIC_TXT;
												var newFile = new File(PATH_NEW_FILE);
												
												try (
  																	var linesFic1 = Files.lines(pathFic1); 
  																	var linesFic2 = Files.lines(pathFic2);
  																	var writter = new BufferedWriter(new FileWriter(newFile))
															 ) // @formatter:on
            {
                  linesFic1.forEach(appendLine(writter));
                  linesFic2.forEach(appendLine(writter));
            }
            catch (IOException ex)
            {
                  System.out.println(FILES_CAN_NOT_READ);
            }
      }

      /**
       * 
       * Method appendLine : Agregamos una linea leída, y salto de línea al fichero.
       *
       * @param writter
       * @return el Consumer a aplicar a cada linea.
       */
      private static Consumer<? super String> appendLine(BufferedWriter writter)
      {
            return line->
                  {
                        try
                        {
                              writter.append(line);
                              writter.newLine();
                        }
                        catch (IOException ex)
                        {
                              System.out.println("Error en el step de escritura");
                        }
                  };
      }

      /**
       * 
       * Method revalidate : Creamos objetos tipo file, comprobamos si son directorios
       * o ficheros, segun requerimientos del programa, utilizando los metodos
       * {@link #validateFile(File)} y {@link #validateDirectory(File)}.
       *
       * @param args
       * @return si se validan los argumentos.
       */
      private static boolean revalidated(String[] args)
      {
            var file = new File[3];
            try
            {
                  file[0] = new File(args[0]);
                  validateFile(file[0]);
                  file[1] = new File(args[1]);
                  validateFile(file[1]);
                  file[2] = new File(args[2]);
                  validateDirectory(file[2]);
                  return true;
            }
            catch (NotFileOrDirectoryException ex)
            {
                  System.out.println(ex.getMessage());
                  return false;

            }
      }

      /**
       * 
       * Method validateDirectory : En este metodo validamos si el File pasado como
       * argumento es un directorio.
       *
       * @param file
       * @throws NotFileOrDirectoryException
       */
      private static void validateDirectory(File file) throws NotFileOrDirectoryException
      {
            if (!file.isDirectory())
            {
                  throw new NotFileOrDirectoryException(file.getAbsolutePath() + " :: " + NotFileOrDirectoryException.NOT_A_DIRECTORY);
            }
      }

      /**
       * 
       * Method validateFile :En este metodo validamos si el File pasado como
       * argumento es un fichero.
       *
       * @param file
       * @throws NotFileOrDirectoryException
       */
      private static void validateFile(File file) throws NotFileOrDirectoryException
      {
            if (!file.isFile())
            {
                  throw new NotFileOrDirectoryException(file.getAbsolutePath() + " :: " + NotFileOrDirectoryException.NOT_A_FILE);
            }
      }
}
