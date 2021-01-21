package com.jeycode.rwcreatetemp;

public class RWcreateTempException extends Exception
{

      public static final String ARGS_ERROR = "Para ejecutar el programa se necesita un fichero pasado como argumento, compruebe el path";
      public static final String READING_LINES_ERROR = "No se pudieron cargar las líneas de texto";
      public static final String WRITTING_STEP_ERROR = "Error al escribir las líneas en el fichero";
      public static final String CREATE_TEMP_FILE_ERROR = "Error al crear el fichero temporal";
      public static final String RELOCATE_ERROR = "Error al mover el fichero temporal a su nueva ubicación";

      public RWcreateTempException(String message)
      {
            super(message);
      }

      private static final long serialVersionUID = 1L;
}
