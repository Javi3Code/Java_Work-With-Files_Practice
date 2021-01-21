package com.jeycode.joinfilecontent;

/**
 * 
 * @author Javier Pérez Alonso
 *
 *         15 oct. 2020
 *
 *         Excepcion creada para tratar la validacion de directorio o fichero
 *
 */
public class NotFileOrDirectoryException extends Exception

{

						/**
						 * 
						 */
						private static final long serialVersionUID = 1L;
						public static final String NOT_A_FILE = "Error al leer el ficheros, verifique el path";
						public static final String NOT_A_DIRECTORY = "Error al leer el directorio, verifique el path";

						public NotFileOrDirectoryException(String message)
						{
												super(message);
						}
}
