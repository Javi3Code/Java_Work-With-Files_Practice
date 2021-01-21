package com.jeycode.rwcreatetemp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.StringTokenizer;

public final class MainTextFileFormatWithoutTempFile
{

						private static final String _OLD = "_old";
						private static final String TXT = ".txt";
						private static final String SIMPLE_SPACE = " ";
						private static String ORIGINAL_FILE_NAME;
						private static int SEQUENCE_OF_SPACES = 0;
						private static String OLD_FILE_PATH_BEFORE;
						private static String OLD_FILE_NAME_BEFORE;
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
						 * operaciones de lectura y escritura en otro fichero formateando el contenido.
						 * {@link #readFileAndWriteItInAnotherFileFormattingContent(String)}
						 *
						 * @param args : Argumentos externos al programa.
						 */
						public static void main(String[] args)
						{
												try
												{
																		var file = ifIsAllOkReturnFile(args);
																		OLD_FILE_PATH_BEFORE = FORMATTED_FILE_PATH = file.getAbsolutePath();
																		OLD_FILE_NAME_BEFORE = FORMATTED_FILE_NAME = file.getName();
																		var newPath = renameOldFile(file);
																		readFileAndWriteItInAnotherFileFormattingContent(newPath);
																		printSuccesMessage();
												}
												catch (RWcreateTempException ex)
												{
																		System.out.println(ex.getMessage());
												}
						}

						/**
						 * 
						 * Method printSuccesMessage
						 *
						 */
						private static void printSuccesMessage()
						{
												System.out.println("Operación realizada satisfactoriamente.\nSe ha fomateado el fichero " + OLD_FILE_NAME_BEFORE + " cuyo path era "
																																				+ OLD_FILE_PATH_BEFORE + ", este mismo se encuentra en este path " + OLD_FILE_PATH
																																				+ " con este nuevo nombre " + OLD_FILE_NAME
																																				+ ".\nLa operación de formateo realizada consta de corregir identación de espacios en blanco del margen izquierdo a 0, "
																																				+ "\nla primera vocal encontrada en cada línea se ha puesto en mayúsculas,\n y las secuencias de espacios mayores a uno, en el resto de la línea,"
																																				+ " se han reducido a 1.\nEl antiguo fichero se mantiene con el contenido original, mientras que el resultado del formateo\n"
																																				+ "se ha guardado en el fichero " + FORMATTED_FILE_NAME + " , que se puede encontrar en este path "
																																				+ FORMATTED_FILE_PATH + ".");
						}

						/**
						 * 
						 * Method readFileAndWriteItInAnotherFileFormattingContent : Tratamos de leer el
						 * contenido del fichero sin formatear, asi mismo de escribirlo en otro fichero
						 * {@link #writeIt(BufferedWriter, String)} , formateando cada linea.
						 * {@link #formatter(String)}
						 *
						 * @param path : El path del fichero donde vamos a leer.
						 * @throws RWcreateTempException
						 */
						private static void readFileAndWriteItInAnotherFileFormattingContent(String path) throws RWcreateTempException
						{
												var pathObjt = Paths.get(path);
												// @formatter:off
												try (var lines = Files.lines(pathObjt); 
																	var writter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(ORIGINAL_FILE_NAME)))))// @formatter:on
												{
																		lines.forEach(line-> writeIt(writter,formatter(line)));
												}
												catch (IOException ex)
												{
																		throw new RWcreateTempException(RWcreateTempException.READING_LINES_ERROR);
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
												var lineFormatted = upperFirstLetter(line);
												var tokenizer = new StringTokenizer(lineFormatted.stripLeading(),SIMPLE_SPACE,true);
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
												var ficName = ORIGINAL_FILE_NAME = file.getName();
												ficName = ficName.endsWith(TXT) ? ficName.replaceAll(TXT,_OLD.concat(TXT)) : ficName.concat(_OLD);
												var newFile = new File(ficName);
												file.renameTo(newFile);
												OLD_FILE_PATH = newFile.getAbsolutePath();
												OLD_FILE_NAME = newFile.getName();
												return newFile.getAbsolutePath();
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

}
