package com.jeycode.rwcreatetemp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.StringTokenizer;

public final class MainTextFileFormatWithTempFile
{

      private static final String _OLD = "_old";
      private static final String TXT = ".txt";
      private static final String SIMPLE_SPACE = " ";
      private static int SEQUENCE_OF_SPACES = 0;
      private static Path ORIGINAL_FILE_PATH;
      private static String OLD_FILE_PATH_BEFORE;
      private static String OLD_FILE_NAME_BEFORE;
      private static String TEMP_FILE_PATH;
      private static String TEMP_FILE_NAME;
      private static String OLD_FILE_PATH;
      private static String OLD_FILE_NAME;
      private static String FORMATTED_FILE_PATH;
      private static String FORMATTED_FILE_NAME;

      /**
       * 
       * Method main : Metodo ejecutable, recibe argumentos externos, los
       * requerimientos son recibir el path a un fichero. Si se cumplen ciertos
       * requisitos {@link #ifIsAFileReturn(String)} retornamos un fichero, entonces
       * le renombramos {@link #renameOldFile(File)} y por ultimo, llevamos a cabo las
       * operaciones de lectura y escritura en otro fichero temporal formateando el
       * contenido. {@link #readFileAndWriteItInTempFileFormattingContent(String)} Si
       * todo sucede como se espera, reubicamos y nombramos ese fichero como y donde
       * estaba el fichero pasado por argumentos. {@link #relocateTempFile(File)}
       *
       * @param args : Argumentos externos al programa.
       */
      public static void main(String[] args)
      {
            try
            {
                  var file = ifIsAllOkReturnFile(args);
                  var newPath = renameOldFile(file);
                  var tempFile = readFileAndWriteItInTempFileFormattingContent(newPath);
                  relocateTempFile(tempFile);
                  printSuccesMessage();
            }
            catch (RWcreateTempException ex)
            {
                  System.out.println(ex.getMessage());
            }
      }

      /**
       * 
       * Method ifIsAllOkReturnFile : Se comprueba que los argumentos pasados al
       * programa cumplen con las expectativas. {@link #checkTheSize(String...)} Se
       * comprueba también si es un fichero, {@link #ifIsAFileReturn(String)} si lo
       * es, se retorna.
       *
       * @param args : Argumentos externos al programa.
       * @return : El fichero, si lo es.
       * @throws RWcreateTempException
       */
      private static File ifIsAllOkReturnFile(String...args) throws RWcreateTempException
      {
            checkTheSize(args);
            return ifIsAFileReturn(args[0]);
      }

      /**
       * 
       * Method ifIsAFileReturn : Comprueba si es un fichero, si lo es lo retorna.
       *
       * @param path : El path al fichero.
       * @return El fichero.
       * @throws RWcreateTempException
       */
      private static File ifIsAFileReturn(String path) throws RWcreateTempException
      {
            var file = new File(path);
            if (!file.isFile())
            {
                  throw new RWcreateTempException(RWcreateTempException.ARGS_ERROR);
            }
            return file;
      }

      /**
       * 
       * Method checkTheSize : Comprobamos que los argumentos cumplen los
       * requerimientos.
       *
       * @param args : Argumentos externos al programa.
       * @throws RWcreateTempException
       */
      private static void checkTheSize(String...args) throws RWcreateTempException
      {
            var invalidate = args == null || args.length != 1;
            if (invalidate)
            {
                  throw new RWcreateTempException(RWcreateTempException.ARGS_ERROR);
            }
      }

      /**
       * 
       * Method renameOldFile : Renombramos el fichero pasado por argumentos al
       * programa. Se comprueba si acaba en {@value #TXT} , si es asi, se reemplaza
       * .txt por {@value #_OLD} + {@value #TXT}, si no, simplemente se añade
       * {@value #_OLD} . Se renombra el fichero y retornamos el path absoluto del
       * fichero renombrado.
       *
       * @param file : Fichero a renombrar.
       * @return El path absoluto.
       */
      private static String renameOldFile(File file)
      {
            OLD_FILE_PATH_BEFORE = file.getAbsolutePath();
            OLD_FILE_NAME_BEFORE = file.getName();
            ORIGINAL_FILE_PATH = Paths.get(OLD_FILE_PATH_BEFORE);

            var ficName = OLD_FILE_NAME_BEFORE;
            ficName = ficName.endsWith(TXT) ? ficName.replaceAll(TXT,_OLD.concat(TXT)) : ficName.concat(_OLD);
            var newFile = new File(ficName);
            file.renameTo(newFile);

            OLD_FILE_NAME = newFile.getName();
            OLD_FILE_PATH = newFile.getAbsolutePath();

            return OLD_FILE_PATH;
      }

      /**
       * 
       * Method readFileAndWriteItInTempFileFormattingContent: Se trata de crear un
       * fichero temporal {@link #tryToCreateTempFile(File)} donde escribir el
       * contenido del fichero, pasado por argumentos , sin formatear,
       * {@link #writeIt(BufferedWriter, String)} , formateando cada linea.
       * {@link #formatter(String)}, por ultimo, devolvemos este fichero temporal, si
       * todo va correcto.
       *
       * @param path : El path del fichero donde vamos a leer.
       * @return El fichero temporal.
       * @throws RWcreateTempException
       */
      private static File readFileAndWriteItInTempFileFormattingContent(String path) throws RWcreateTempException
      {
            var pathObjt = Paths.get(path);
            var tempFile = tryToCreateTempFile();
            TEMP_FILE_PATH = tempFile.getAbsolutePath();
            TEMP_FILE_NAME = tempFile.getName();
            System.out.println("******DURANTE LA EJECUCIÓN******\nSe ha creado fichero temporal llamado " + TEMP_FILE_NAME
                                    + " en este path " + TEMP_FILE_PATH);
            // @formatter:off
												try (var lines = Files.lines(pathObjt); 
																	var writter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile))))// @formatter:on
            {
                  lines.forEach(line-> writeIt(writter,formatter(line)));
            }
            catch (IOException ex)
            {
                  throw new RWcreateTempException(RWcreateTempException.READING_LINES_ERROR);
            }

            return tempFile;

      }

      /**
       * 
       * Method relocateTempFile : Reubicamos el fichero temporal en el path del
       * fichero pasado por argumentos. Guardado en el {@value #ORIGINAL_FILE_PATH}.
       *
       * @param tempFile : Fichero temporal.
       * @throws RWcreateTempException
       */
      private static void relocateTempFile(File tempFile) throws RWcreateTempException
      {
            try
            {
                  var newPath = Files.move(Path.of(tempFile.getAbsolutePath()),ORIGINAL_FILE_PATH,StandardCopyOption.ATOMIC_MOVE);
                  FORMATTED_FILE_PATH = newPath.toString();
                  FORMATTED_FILE_NAME = newPath.getFileName()
                                               .toString();
            }
            catch (IOException ex)
            {
                  throw new RWcreateTempException(RWcreateTempException.RELOCATE_ERROR);
            }
      }

      /**
       * 
       * Method printSuccesMessage
       *
       */
      private static void printSuccesMessage()
      {
            System.out.println("\nOperación realizada satisfactoriamente.\nSe ha fomateado el fichero " + OLD_FILE_NAME_BEFORE
                                    + " cuyo path era " + OLD_FILE_PATH_BEFORE + ", este mismo se encuentra en este path " + OLD_FILE_PATH
                                    + " con este nuevo nombre " + OLD_FILE_NAME
                                    + ".\nLa operación de formateo realizada consta de corregir identación de espacios en blanco del margen izquierdo a 0, "
                                    + "\nla primera vocal encontrada en cada línea se ha puesto en mayúsculas,\n y las secuencias de espacios mayores a uno, en el resto de la línea,"
                                    + " se han reducido a 1.\nEl antiguo fichero se mantiene con el contenido original, mientras que el resultado del formateo\n"
                                    + "se ha guardado en el fichero " + FORMATTED_FILE_NAME + " , que se puede encontrar en este path "
                                    + FORMATTED_FILE_PATH + " para llevar a cabo esta operación, hemos usado un fichero temporal,\n"
                                    + " que luego se ha reubicado por el destino del fichero formateado comentado anteriormente.");
      }

      /**
       * 
       * Method tryToCreateTempFile : Se trata de crear el fichero temporal. Vemos
       * comentada la mismo operacion, pero pasandole la ruta donde ubicar ese fichero
       * temporal, para hacer las pruebas.
       *
       * @return El fichero temporal.
       * @throws RWcreateTempException
       */
      private static File tryToCreateTempFile() throws RWcreateTempException
      {
            try
            {
                  return File.createTempFile("FilePractice5","temp.txt");
            }
            catch (IOException ex1)
            {
                  throw new RWcreateTempException(RWcreateTempException.CREATE_TEMP_FILE_ERROR);
            }

      }

      /**
       * 
       * Method formatter : Formateamos las lineas segun los requerimientos. Primera
       * letra encontrada se pasa a mayusculas, los espacios del principio se
       * eliminan, el resto de secuencias de espacios se formatean a uno solo.
       * {@link #limitToASingleSpace(String)}
       *
       * @param line : Linea a formatear.
       * @return La linea ya formateada.
       */
      private static String formatter(String line)
      {
            var lineFormatted = upperFirstLetter(line.stripLeading());
            var tokenizer = new StringTokenizer(lineFormatted,SIMPLE_SPACE,true);
            var strBuilder = new StringBuilder(lineFormatted.length());
            while (tokenizer.hasMoreTokens())
            {
                  strBuilder.append(limitToASingleSpace(tokenizer.nextToken()));
            }
            return strBuilder.toString();
      }

      /**
       * 
       * Method limitToASingleSpace : Comprobamos el token, si es un espacio
       * comprobamos si el anterior lo era, llevando la cuenta en un atributo static
       * {@link #SEQUENCE_OF_SPACES}. Si es mayor a uno, entonces eliminamos ese
       * espacio de mas. En el caso de que encuentre una letra y el valor de este
       * atributo no sea 0, lo reseteamos.
       *
       * @param token : Token a formatear.
       * @return El token formateado si es que fuera necesario.
       */
      private static String limitToASingleSpace(String token)
      {
            if (token.equals(SIMPLE_SPACE))
            {
                  SEQUENCE_OF_SPACES++;
                  if (SEQUENCE_OF_SPACES > 1)
                  {
                        token = token.stripLeading();
                  }
            }
            else
            {
                  if (SEQUENCE_OF_SPACES != 0)
                  {
                        SEQUENCE_OF_SPACES = 0;
                  }
            }
            return token;
      }

      /**
       * 
       * Method upperFirstLetter : Ponemos la primera letra encontrada en la linea, en
       * mayuscula.
       *
       * @param line : Linea a formatear.
       * @return la linea formateada.
       */
      private static String upperFirstLetter(String line)
      {
            var index = 0;

            while (!Character.isLetter(line.charAt(index)))
            {
                  index++;
            }
            var oldChar = line.substring(index,index + 1);
            var newline = line.replaceFirst(oldChar,oldChar.toUpperCase());
            return newline;
      }

      /**
       * 
       * Method writeIt : Escribimos la linea en el fichero nuevo.
       * 
       * Para hacer las pruebas y forzar un error al escribir, dejo comentado esas 4
       * lineas de codigo.
       * 
       *
       * @param writter : El objeto encargado de escribir.
       * @param line    : Linea a escribir
       */
      private static void writeIt(BufferedWriter writter,String line)
      {
            try
            {
                  writter.write(line);
                  writter.newLine();
            }
            catch (IOException ex)
            {
                  System.out.println(RWcreateTempException.WRITTING_STEP_ERROR);
            }
      }

}
