package Lexico;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class pruebas {
	public static void main(String [] args) {

		char cadena = '\"';
		
		 try{
		 FileInputStream FIS = new FileInputStream("C:\\Users\\Jerry\\Desktop\\LEXICO.txt");  //Abrimos el archivo
         DataInputStream DIS = new DataInputStream(FIS); //se crea el objeto de entrada
         BufferedReader buffer = new BufferedReader(new InputStreamReader(DIS)); //se crea buffer de lectura
         String strLinea;
         
         while ((strLinea = buffer.readLine()) != null)   {
        	 System.out.println(strLinea);
        	 
        	 List lista = new ArrayList<>();
        	 lista.add(strLinea.charAt(0));
        	 System.out.print(lista.toString());
        	 
        	  	 
        	 Token token = new Token();
        	 System.out.println(token.isComentario(strLinea));
         	
        	 
         }
         
		 }catch (Exception e){ //Catch de excepciones
	         System.err.println("Ocurrio un error: " + e.getMessage());
	     }
		
	}
}
