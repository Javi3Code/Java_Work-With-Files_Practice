package com.jeycode.listoffilesanddirectories;

import java.io.File;
import java.util.Date;

public final class FileMain
{

      /**
       * Constantes con los String que se utilizan mas adelante
       */
      private static final String DIRECTORY = "Directory: ";
      private static final String FILE = "File: ";
      private static final String ERROR_MESSGE = "Argumentos 1 o 2, es decir ruta del directorio el primero,"
                              + " ó fichero, y opcional, -All";
      private static final String X = "x";
      private static final String W = "w";
      private static final String R = "r";
      private static final String NOT = "-";
      private static final String PERMISSIONS = "Permisos: ";
      private static final String ALL = "-all";
      private static final String KB = " KB";
      private static final String TOTAL_SPACE_FILE = "Espacio total del fichero: ";
      private static final String TOTAL_SPACE = "Espacio total: ";
      private static final String LAST_MODIFICATION_MSG = "Última modificación: ";
      private static final String SEPARATOR = "/";
      private static final String MB = " MB";
      private static final String YES_IDENTATION = "\t";
      private static final String NOT_IDENTATION = "";
      private File file;

      /**
       * 
       * Method main : Metodo ejecutable. Comprueba si los args son menores a 0,
       * mayores a 2, o si son 2 y el segundo no es igual a -all, en estos casos lanza
       * una excepcion con un mensaje de ayuda. mayores a 2
       *
       * @param args
       */
      public static void main(String[] args)
      {
            try
            {
                  if (args == null || args.length == 0)
                  {
                        args = new String[]{"."};
                  }
                  new FileMain(args);
            }
            catch (Exception ex)
            {
                  System.out.println(ex.getMessage());
            }
      }

      /**
       * 
       * Builder, Constructor de la clase que contiene el ejecutable. En el
       * constructor instanciamos File, imprimimos por pantalla el nombre del
       * directorio padre y llamamos a la operacion
       *
       * @param args Argumentos externos que pasamos al programa.
       * @throws Exception
       */
      public FileMain(String[] args) throws Exception
      {
            loadFile(args);
            if (file.isDirectory())

            {
                  System.out.println("Directorio Padre: " + file.getAbsolutePath() + SEPARATOR + "\n");
                  operation(file,true,args);
            }
            else if (file.isFile())
            {
                  System.out.println(file + " es un fichero.");
            }
            else
            {
                  throw new Exception("No existe ese Fichero o directorio, revisa el path");
            }
      }

      private void loadFile(String[] args) throws Exception
      {
            var invalid = args.length > 2 || args.length == 2 && !args[1].equals(ALL);
            if (invalid)
            {
                  throw new Exception(ERROR_MESSGE);
            }
            file = new File(args[0]);

      }

      /**
       * 
       * Method operation: obtenemos un array de String con el contenido del fichero,
       * comprobamos si es null, si no lo es, operamos.
       *
       * @param file       : Fichero a comprobar contenido.
       * @param firstlevel : Boolean para comprobar el nivel de directorios a partir
       *                   del padre en el que se encuentra el programa, restringido a
       *                   primer nivel.
       * @param args       : Array de argumentos del programa.
       */
      private void operation(File file,boolean firstlevel,String...args)
      {
            var allContent = file.listFiles();
            if (allContent != null)
            {
                  operate(firstlevel,allContent,args);
            }

      }

      /**
       * 
       * Method operate : Bucle forEach en el que se recorre todo el array de
       * contenido del directorio, aplicando a cada elemento ciertas operaciones.
       *
       * @param firstlevel : Boolean para comprobar el nivel de directorios a partir
       *                   del padre en el que se encuentra el programa, restringido a
       *                   primer nivel.
       * @param allContent : Array de File con el contenido del directorio.
       * @param args       : Array de argumentos del programa.
       */
      private void operate(boolean firstlevel,File[] allContent,String...args)
      {
            for (File piece : allContent)
            {
                  makeThisOperations(firstlevel,allContent,piece,args);
            }
      }

      /**
       * 
       * Method makeThisOperations : Conjunto de operaciones a aplicar a cada elemento
       * contenido en el directorio.
       *
       * @param firstlevel : Boolean para comprobar el nivel de directorios a partir
       *                   del padre en el que se encuentra el programa, restringido a
       *                   primer nivel.
       * @param allContent : Array de File con el contenido del directorio.
       * @param piece      : Elemento sobre el cual se realizan las operaciones, en
       *                   este momento es un String, pero se pasa por parametro al
       *                   constructor de File, para obtener el objeto File..
       * @param args       : Array de argumentos del programa.
       */
      private void makeThisOperations(boolean firstlevel,File[] allContent,File piece,String[] args)
      {
            var totalSpace = 0D;
            totalSpace = printNameAndCalculateSpaceIfIsFile(firstlevel,totalSpace,piece);
            printDate(firstlevel,piece);
            printPermissions(firstlevel,piece);
            ifCommandAllReadOtherLevel(firstlevel,piece,args);
            ifIsUltimateFilePrintTotalSpace(firstlevel,allContent,piece,totalSpace);
            System.out.println();
      }

      /**
       * 
       * Method printNameAndCalculateSpaceIfIsFile : Metodo que imprime el nombre del
       * elemento, comprueba si es un fichero, en tal caso, calcula el espacio ocupado
       * por el fichero y acumula su peso en un total del directorio.
       *
       * @param firstlevel : Boolean para comprobar el nivel de directorios a partir
       *                   del padre en el que se encuentra el programa, restringido a
       *                   primer nivel.
       * @param totalSpace : Variable para acumular el espacio total ocupado por
       *                   ficheros en este directorio.
       * @param thisFile   : Objeto de tipo File sobre el que se aplican las
       *                   operaciones.
       * @return totalSpace
       */
      private double printNameAndCalculateSpaceIfIsFile(boolean firstlevel,double totalSpace,File thisFile)
      {
            if (thisFile.isFile())
            {
                  totalSpace = printNameAndCalculateSpace(firstlevel,totalSpace,thisFile);
            }
            else
            {
                  printName(firstlevel,DIRECTORY,thisFile,true);
            }
            return totalSpace;
      }

      /**
       * 
       * Method printNameAndCalculateSpace : Metodo que imprime el nombre del Fichero
       * y calcula el espacio ocupado por el mismo, acumula su valor en una variable
       * totalSpace del directorio, y la regresa.
       *
       * @param firstlevel : Boolean para comprobar el nivel de directorios a partir
       *                   del padre en el que se encuentra el programa, restringido a
       *                   primer nivel.
       * @param totalSpace : Variable para acumular el espacio total ocupado por
       *                   ficheros en este directorio.
       * @param thisFile   : Objeto de tipo File sobre el que se aplican las
       *                   operaciones.
       * @return totalSpace
       */
      private double printNameAndCalculateSpace(boolean firstlevel,double totalSpace,File thisFile)
      {
            printName(firstlevel,FILE,thisFile,false);
            totalSpace = calculateTotalSpaceAndPrintFileSpace(firstlevel,totalSpace,thisFile);
            return totalSpace;
      }

      /**
       * 
       * Method printName : Metodo para imprimir el nombre de un fichero o directorio
       * por pantalla.
       *
       * @param firstlevel : Boolean para comprobar el nivel de directorios a partir
       *                   del padre en el que se encuentra el programa, restringido a
       *                   primer nivel.
       * @param type       : String que identifica el tipo de objeto File.
       * @param thisFile   : Objeto de tipo File sobre el que se aplican las
       *                   operaciones.
       */
      private void printName(boolean firstlevel,String type,File thisFile,boolean directory)
      {
            var separator = directory ? SEPARATOR : "";
            System.out.println(indent(firstlevel) + type + thisFile.getAbsolutePath() + separator);
      }

      /**
       * 
       * Method identate : metodo para identar la linea impresa, comprueba si es de
       * primer nivel, en el caso que no lo sea, identa.
       *
       * @param firstlevel : Boolean para comprobar el nivel de directorios a partir
       *                   del padre en el que se encuentra el programa, restringido a
       *                   primer nivel.
       * @return el valor de la identacion.
       */
      private String indent(boolean firstlevel)
      {
            if (firstlevel)
            {
                  return NOT_IDENTATION;
            }
            return YES_IDENTATION;
      }

      /**
       * 
       * Method ifCommandAllReadOtherLevel : Metodo que comprueba si el comando -all
       * ha sido pasado como argumento. Si fuera el caso, y es un directorio, utiliza
       * recursividad llamando de nuevo al metodo operation, actuando sobre este
       * directorio. En este punto firstlevel pasa a ser false.
       *
       * @param firstlevel : Boolean para comprobar el nivel de directorios a partir
       *                   del padre en el que se encuentra el programa, restringido a
       *                   primer nivel.
       * @param thisFile   : Objeto de tipo File sobre el que se aplican las
       *                   operaciones.
       * @param args       : Array de argumentos del programa.
       */
      private void ifCommandAllReadOtherLevel(boolean firstlevel,File thisFile,String...args)
      {
            if (args.length == 2 && firstlevel && args[1].equals(ALL) && thisFile.isDirectory())
            {
                  operation(thisFile,false,args);
            }
      }

      /**
       * 
       * Method ifIsUltimateFilePrintTotalSpace : Metodo que comprueba si es el ultimo
       * elemento del directorio, en ese caso imprime el valor de totalSpace.
       *
       * @param firstlevel : Boolean para comprobar el nivel de directorios a partir
       *                   del padre en el que se encuentra el programa, restringido a
       *                   primer nivel.
       * @param allContent : Array de File con el contenido del directorio.
       * @param piece      : Elemento sobre el cual se realizan las operaciones, en
       *                   este momento es un File, pero se pasa por parametro al
       *                   constructor de File, para obtener el objeto File...
       * @param totalSpace : Variable para acumular el espacio total ocupado por
       *                   ficheros en este directorio.
       */
      private void ifIsUltimateFilePrintTotalSpace(boolean firstlevel,File[] allContent,File piece,double totalSpace)
      {
            if (allContent[allContent.length - 1].equals(piece))
            {
                  System.out.println("\n" + indent(firstlevel) + TOTAL_SPACE + totalSpace / 1024 + MB);
            }
      }

      /**
       * 
       * Method calculateTotalSpaceAndPrintFileSpace : Metodo que se ocupa de calcular
       * el espacio total, pasar de KB a MB e imprimirlo por pantalla.
       *
       * @param firstlevel : Boolean para comprobar el nivel de directorios a partir
       *                   del padre en el que se encuentra el programa, restringido a
       *                   primer nivel.
       * @param totalSpace : Variable para acumular el espacio total ocupado por
       *                   ficheros en este directorio.
       * @param thisFile   : Objeto de tipo File sobre el que se aplican las
       *                   operaciones.
       * @return totalSpace
       */
      private double calculateTotalSpaceAndPrintFileSpace(boolean firstlevel,double totalSpace,File thisFile)
      {
            var space = thisFile.length() / 1024;
            totalSpace += space;
            System.out.println(indent(firstlevel) + TOTAL_SPACE_FILE + space + KB);
            return totalSpace;
      }

      /**
       * 
       * Method printDate : Metodo que obtiene un long con el valor de la ultima
       * modificacion del Objeto File y lo formatea a tipo Date, por ultimo lo
       * imprime.
       *
       * @param firstlevel : Boolean para comprobar el nivel de directorios a partir
       *                   del padre en el que se encuentra el programa, restringido a
       *                   primer nivel.
       * @param thisFile   : Objeto de tipo File sobre el que se aplican las
       *                   operaciones.
       */
      private void printDate(boolean firstlevel,File thisFile)
      {
            var date = new Date(thisFile.lastModified());
            System.out.println(indent(firstlevel) + LAST_MODIFICATION_MSG + date.toString());
      }

      /**
       * 
       * Method printPermissions : Metodo que comprueba permisos del objeto File e
       * imprime un string con los valores.
       *
       * @param firstlevel : Boolean para comprobar el nivel de directorios a partir
       *                   del padre en el que se encuentra el programa, restringido a
       *                   primer nivel.
       * @param thisFile   : Objeto de tipo File sobre el que se aplican las
       *                   operaciones.
       */
      private void printPermissions(boolean firstlevel,File thisFile)
      {
            var strb = new StringBuilder(PERMISSIONS);
            checkIf(thisFile.canRead(),strb,R);
            checkIf(thisFile.canWrite(),strb,W);
            checkIf(thisFile.canExecute(),strb,X);
            System.out.println(indent(firstlevel) + strb.toString());
      }

      /**
       * 
       * Method checkIf : Metodo que agrega un string u otro a un stringbuilder,
       * dependiendo de la condicion.
       *
       * @param can    : Booleano a comprobar.
       * @param strb   : StringBuilder al que se agrega cierto String.
       * @param letter : Letra a agregar si es true.
       */
      private void checkIf(boolean can,StringBuilder strb,String letter)
      {
            strb.append(can ? letter : NOT);
      }
}
