package com.jeycode.readfiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.StringTokenizer;

public class ReadWithBufferButWithoutIndexOf
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
						 * Path, y obtenemos un flujo de Strings con las lineas del fichero. Lo cargamos
						 * con cada linea. Por ultimo, buscamos.
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
												var tokenizer = new StringTokenizer(txtLine," ",true);
												var column = 0;
												while (tokenizer.hasMoreTokens())
												{
																		var token = tokenizer.nextToken();
																		if (token.contains(args))
																		{
																								column = searchColumnAndPrint(args,token,column);

																		}
																		else
																		{
																								column += token.length();
																		}
												}
						}

						/**
						 * 
						 * Method searchColumnAndPrint: Se busca dentro del token la cadena args. Si se
						 * encuentra se imprime mensaje determinado.
						 *
						 * @param args       : Cadena a buscar.
						 * @param token      : Token actual.
						 * @param lineNumber : Numero de linea.
						 * @param column     : Columna actual.
						 * @return el valor de la columna.
						 */
						private static int searchColumnAndPrint(String args,String token,int column)
						{
												var dimension = args.length();
												for (var i = 0; i < token.length(); i++)
												{
																		var endIndex = i + dimension;
																		try
																		{
																								var substring = token.substring(i,endIndex);
																								if (substring.equals(args))
																								{
																														System.out.println("Encontrado en la línea : " + LINE + "// Columna: " + column);
																														column++;
																								}
																								else
																								{
																														column++;
																								}
																		}
																		catch (StringIndexOutOfBoundsException e)
																		{
																								continue;
																		}
												}
												return column;
						}

}
