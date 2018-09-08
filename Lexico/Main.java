package Lexico;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
	public static void main (String [] krustier) {
			
		Output outputToken = new Output();
		 int linea = 0; //para contar las lineas
		//Leemos el archivo
		try{
			
	         FileInputStream FIS = new FileInputStream("C:\\Users\\Jerry\\Desktop\\LEXICO.txt");  //Abrimos el archivo
	         DataInputStream DIS = new DataInputStream(FIS); //se crea el objeto de entrada
	         BufferedReader buffer = new BufferedReader(new InputStreamReader(DIS)); //se crea buffer de lectura
	         String strLinea, caracter = ""; //CARACTER declarado para cuando los delimitadores vengan pegados
	         boolean juntos = false;
	         List lista = new ArrayList(); // guadamos los que no son delimitadores
	         List listaDel = new ArrayList(); //guardamos delimitadores
	         
	        
	         Token token = new Token(); 
	        
	         // Leer el archivo linea por linea
	         while ((strLinea = buffer.readLine()) != null)   { //while tenga lineas para leer  seguira leyendo
	             linea++; // indica la linea en la que esta leyendo util para la impresion
	             String cadenaDel = ""; // guardamos la cadena de los delimitadores en la variable para ocupar menos memoria
	             for(int i=0;i<strLinea.length();i++) { // FOR PARA LEER CADA CARACTER DE LA LINEA
	            	 
	            	if(token.isDelimitadorToken(strLinea.charAt(i)) || token.isDelimitadorNoToken(strLinea.charAt(i))) { //checamos que si es delimimtador o no
	            		
	            		if(token.isDelimitadorToken(strLinea.charAt(i))) { //si el caracter que llego genera token se guarda en la lista de los delimitadores
	            			listaDel.add(strLinea.charAt(i)); //AGREGAMOS DELIMITADOR A LA LISTA
	            		}
	            		else {	// SI NO se guarda la lista en la variable cadena del para que despues sea comparado he impreso
	            			
	            			cadenaDel = token.imprimirLista(listaDel); 
	            			outputToken.agregarLexema(cadenaDel);
	            			listaDel.clear(); //Limpiamos la lista para que no se junten los valores a comparar
	            				            			
	            			
	            			if(token.isOperador(cadenaDel)) {
	            				System.out.println("Operador " + token.getTipoOperador(cadenaDel) + ": " + cadenaDel + " Linea " + linea);
	            			}
	            			else if(token.isSpecialCharacter(cadenaDel)) { 
	            				System.out.println("Caracter especial:  " + cadenaDel + " Linea " + linea);
	            			}else {
	            				
	            				//Si no fue operador ni caracter especial significa que llegaron simbolos pegados por lo que hay que identificarlos con  el siguiente for
	            				for(int k=0;k<cadenaDel.length();k++) {
	            
	            					caracter = ""; //reinicializamos para que no se junten de nuevo los caracterers a verificar si son operadores o caracteres especiales 
	            					caracter += cadenaDel.charAt(k); //caracter a comparar
	            					
	            					if(token.isOperador(caracter)) System.out.println("Operador " + token.getTipoOperador(caracter) + ": " + caracter + " Linea " + linea);
		            				if(token.isSpecialCharacter(caracter)) System.out.println("Caracter especial:  " + caracter + " Linea " + linea);
		            			}
	            				caracter = "";//para que no se repita al final, es decir, que no se quede ninguno
	            				
	            			}
	            		}
	            		
	            		//SI el delimitador que llego no genera token procedemos a comparar lo que se guardo en la lista 
	            			            		
	            		String cadena = token.imprimirLista(lista); // guardamos la variable para no consumir tanta memoria
	            		outputToken.agregarLexema(cadena);
	            		
	            		if(token.isPalabraReservada(cadena)) System.out.println("Palabra reservada: " + cadena + " Linea " + linea);
	            		if(token.isNumeroEntero(cadena)) System.out.println("Numero entero:  " + cadena + " Linea " + linea);
	            		if(token.isNumeroReal(cadena)) System.out.println("Numero Real:  " + cadena + " Linea " + linea);
	            		if(!lista.isEmpty() && token.isString(cadena)) System.out.println("Constante string:  " + cadena + " Linea " + linea);
	            		if(token.isIdentificador(cadena) && !token.isPalabraReservada(cadena)) System.out.println("Identificador:  " + cadena + " Linea " + linea); 
		            	if(token.isComentario(cadena)) {
	            			System.out.println("Comentario:  " + cadena + "... Linea " + linea);
	            			i = strLinea.length(); //Terminamos de leer la linea porque fue un comentario, es decir, lo siguiente cuenta como el mismo comentario
	            		}
	            		lista.clear(); 
	            	} 
	            	else { //No fue delimitador  
	            		
	            		if(strLinea.charAt(i) == '\"') { // IF PARA GUARDAR todos los caracteres de un string aunque tenga espacios
	            			lista.add(strLinea.charAt(i));
	            			i++;
	            			while(strLinea.charAt(i) != '\"') {
	            				if(strLinea.charAt(i) == '\\' ) {
	            					if(strLinea.charAt(i+1) == '\\' || strLinea.charAt(i+1) == '\"') i++; //para no guardar en el string el escape
	            				}
	            				lista.add(strLinea.charAt(i));
	            				i++;
	            			}
	            			lista.add(strLinea.charAt(i)); // AGREGAMOS LAS COMILLAS FINALES
	            		}else {
	            			lista.add(strLinea.charAt(i)); //el caracter se agregara a la lista para despues ser evaluado
	            			}

	            		cadenaDel = token.imprimirLista(listaDel); //guardamos la variable para no consumir tanta memoria
	            		outputToken.agregarLexema(cadenaDel);
	            		
            			listaDel.clear();
            			if(token.isOperador(cadenaDel)) System.out.println("Operador " + token.getTipoOperador(cadenaDel) + ": " + cadenaDel + " Linea " + linea);
            			if(token.isSpecialCharacter(cadenaDel)) System.out.println("Caracter especial:  " + cadenaDel + " Linea " + linea);
	            		
            			
	            	}
	            
	             }//Cerrar for de leer caracteres de la linea
	             
	             //Si quedo algo en las listas debe ser impreso ya que no llegara delimitador de EOLN
	             
	             if(!lista.isEmpty() || !listaDel.isEmpty()) { //si las listas no estan vacias al terminar de leer la linea se imprime lo que hay dentro 
	            	 String cadena = token.imprimirLista(lista);
	            	 String cadenaDelim = token.imprimirLista(listaDel);
	            	if(!lista.isEmpty()) outputToken.agregarLexema(cadena);
	            	if(!listaDel.isEmpty()) outputToken.agregarLexema(cadenaDelim);
	            	 
	            	 if(token.isPalabraReservada(cadena)) System.out.println("Palabra reservada: " + cadena + " Linea " + linea);
	            	 if(token.isOperador(cadenaDelim))  System.out.println("Operador " + token.getTipoOperador(cadenaDelim) + ": " + cadenaDelim + " Linea " + linea); 
	            	 if(token.isNumeroEntero(cadena)) System.out.println("Numero entero:  " + cadena + " Linea " + linea);  
	            	 if(token.isNumeroReal(cadena)) System.out.println("Numero Real:  " + cadena + " Linea " + linea); 
	            	 if(token.isSpecialCharacter(cadenaDelim))  System.out.println("Caracter especial:  " + cadenaDelim + " Linea " + linea);
	            	 if(!lista.isEmpty() && token.isString(cadena))  System.out.println("Constante string:  " + cadena + " Linea " + linea); 
	            	 if(token.isComentario(cadena))  System.out.println("Comentario:  " + cadena + " Linea " + linea); 
	            	 if(token.isIdentificador(cadena) && !token.isPalabraReservada(cadena))  System.out.println("Identificador:  " + cadena + " Linea " + linea);
	            	 if(!token.isOperador(cadenaDelim) && !token.isSpecialCharacter(cadenaDelim)) { //SI llegaron delimitadores juntos 
	            		 for(int k=0;k<cadenaDelim.length();k++) {
	            			 caracter = ""; //reinicializamos para que no se junten de nuevo los caracterers a verificar si son operadores o caracteres especiales 
	            			 caracter += cadenaDelim.charAt(k); //caracter a comparar
     					
	            			 if(token.isOperador(caracter)) System.out.println("Operador " + token.getTipoOperador(caracter) + ": " + caracter + " Linea " + linea);
	            			 if(token.isSpecialCharacter(caracter)) System.out.println("Caracter especial:  " + caracter + " Linea " + linea);
	            		 }
	            	 }
	            	 
	            	 
	            }//END if lista empty
	            
			lista.clear(); //Limpiamos la lista por el EOLN
	             	listaDel.clear();//limpiamos la lista de los delimitadores por el EOLN
	             	
	       
		 }//Cerrar while leer linea
	         DIS.close(); // Cerramos el archivo
	   
	       
	        
		 }catch (Exception e){ //Catch de excepciones
	         System.err.println("Ocurrio un error: " + e.getMessage());
	     }
		 outputToken.limpiarLexemasFull(linea);
		 outputToken.imprimirLexemas();
		//Terminamos de leer el archivo
	}//MAIN
}//CLASS