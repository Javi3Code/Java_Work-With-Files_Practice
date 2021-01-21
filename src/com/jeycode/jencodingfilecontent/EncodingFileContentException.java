package com.jeycode.jencodingfilecontent;

public final class EncodingFileContentException extends Exception
{

      public static final String ARGS_ERROR = "Para ejecutar el programa se necesita un fichero pasado como argumento, compruebe el path";
      public static final String NOT_UTF8 = "El fichero pasado por argumento no cumple las espectativas";
      public static final String WRITTING_STEP_ERROR = "No se pudo realizar el proceso de recodificación con exito";
      public static final String READING_LINES_ERROR = "No se pudieron cargar las líneas de texto";
      private static final long serialVersionUID = 1L;

      public EncodingFileContentException(String message)
      {
            super(message);
      }

}
