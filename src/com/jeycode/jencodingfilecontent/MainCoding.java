package com.jeycode.jencodingfilecontent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MainCoding
{

						private static final String FIC_ISO88591_TXT = "ficIso88591.txt";
                                    private static final String FIC_UTF16_TXT = "ficUTF16.txt";

                                    /**
						 * 
						 * Method main : Metodo ejecutable, verificamos los requerimientos
						 * {@link #verifyTheArgument(String...)}, en el caso de que sea todo correcto,
						 * leemos el fichero y escribimos los dos nuevos recodificados.
						 * {@link #readUTF8AndWriteUTF16AndISO88591(String)}
						 *
						 * @param args
						 */
						public static void main(String...args)
						{
												try
												{
																		verifyTheArgument(args);
																		readUTF8AndWriteUTF16AndISO88591(args[0]);
												}
												catch (EncodingFileContentException ex)
												{
																		System.out.println(ex.getMessage());
												}
						}

						/**
						 * 
						 * Method verifyTheArgument : Comprobamos si existe o si es la cantidad de
						 * parametros necesarios. {@link #checkTheSize(String...)} Si es correcto
						 * comprobamos que el argumento es un fichero. {@link #checkIfIsAFile(String)}
						 *
						 * @param args : El path del fichero.
						 * @throws EncodingFileContentException
						 */
						private static void verifyTheArgument(String...args) throws EncodingFileContentException
						{
												checkTheSize(args);
												checkIfIsAFile(args[0]);
						}

						/**
						 * 
						 * Method readUTF8AndWriteUTF16AndISO88591 : Tratamos de obtener las lineas del
						 * fichero original y escribimos los nuevos ficheros recodificados usando el
						 * metodo {@link #writeIt(BufferedWriter, String)}
						 *
						 * @param path : El path del fichero.
						 * @throws EncodingFileContentException
						 */
						private static void readUTF8AndWriteUTF16AndISO88591(String path) throws EncodingFileContentException
						{
												var pathObjt = Paths.get(path);
												var newUTF16ficPath = FIC_UTF16_TXT;
												var utf16File = new File(newUTF16ficPath);
												var newISO88591ficPath = FIC_ISO88591_TXT;
												var iso88591File = new File(newISO88591ficPath);
												// @formatter:off
												try (var lines = Files.lines(pathObjt);
																	var utf16 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(utf16File),Charset.forName("UTF-16")));
																 var iso88591 =new BufferedWriter(new OutputStreamWriter(new FileOutputStream(iso88591File),Charset.forName("ISO-8859-1"))))		// @formatter:on 
												{
																		lines.forEach(line-> {
																								writeIt(utf16,line);
																								writeIt(iso88591,line);
																		});

												}
												catch (IOException ex)
												{
																		throw new EncodingFileContentException(EncodingFileContentException.READING_LINES_ERROR);
												}
						}

						/**
						 * 
						 * Method writeIt : Tratamos de escribir la linea en el fichero.
						 *
						 * @param writter : Objeto que se encarga de escribir en el fichero.
						 * @param line    : Linea a escribir.
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
																		System.out.println(EncodingFileContentException.WRITTING_STEP_ERROR);
												}
						}

						/**
						 * 
						 * Method checkIfIsAFile : Comprobamos si el argumento pasado apunta a un
						 * fichero.
						 *
						 * @param path
						 * @throws EncodingFileContentException
						 */
						private static void checkIfIsAFile(String path) throws EncodingFileContentException
						{
												if (!new File(path).isFile())
												{
																		throw new EncodingFileContentException(EncodingFileContentException.ARGS_ERROR);
												}
						}

						/**
						 * 
						 * Method checkTheSize : Comprobamos si los argumentos pasados se ajustan a los
						 * requerimientos.
						 *
						 * @param args : Argumentos externos al programa.
						 * @throws EncodingFileContentException
						 */
						private static void checkTheSize(String...args) throws EncodingFileContentException
						{
												var invalidate = args == null || args.length == 0 || args.length > 1;
												if (invalidate)
												{
																		throw new EncodingFileContentException(EncodingFileContentException.ARGS_ERROR);
												}
						}

}
