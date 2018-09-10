package Lexico;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

public class AnalizadorLexico {
	public static void main (String [] krustierDock) {
			
		Output outputToken = new Output();
		 int linea = 0; //para contar las lineas
		//Leemos el archivo
		try{
			
	         FileInputStream FIS = new FileInputStream("C:\\Users\\Training\\Desktop\\Lex.txt");  //Abrimos el archivo
	         DataInputStream DIS = new DataInputStream(FIS); //se crea el objeto de entrada
	         BufferedReader buffer = new BufferedReader(new InputStreamReader(DIS)); //se crea buffer de lectura
	         String strLinea, caracter = ""; //CARACTER declarado para cuando los delimitadores vengan pegados
	        
	         List<Object> lista = new ArrayList<Object>(); // guadamos los que no son delimitadores
	         List<Object> listaDel = new ArrayList<Object>(); //guardamos delimitadores
	         
	        
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
	            		 else {	// SI NO es delimitador se guarda la lista en la variable cadena del para agregarlo a la lista de lexemas 
	            			cadenaDel = token.imprimirLista(listaDel); 
	            			listaDel.clear(); //Limpiamos la lista para que no se junten los valores en el siguiente ciclo
	            				            			
	            			//BLOQUE DELIMITADORES JUNTOS
	            			if(token.isOperador(cadenaDel)) {
	            				outputToken.agregarLexema(cadenaDel, linea); // 
	            			}
	            			else {
	            				if(token.isSpecialCharacter(cadenaDel))  outputToken.agregarLexema(cadenaDel, linea);
		            				else {
		            					//Si no fue operador ni caracter especial puede que llegaron simbolos pegados por lo que hay que identificarlos con  el siguiente for
		            					for(int k=0;k<cadenaDel.length();k++) {
		            						caracter = ""; //reinicializamos para que no se junten de nuevo los caracterers a verificar si son operadores o caracteres especiales 
		            						caracter += cadenaDel.charAt(k); //caracter a comparar
		            						outputToken.agregarLexema(caracter, linea);
		            					}
		            				//caracter = "";//para que no se repita al final, es decir, que no se quede ninguno //VERIFICAR SI AFECTA QUE LO QUITEMOS
		            				}
	            			}
	            		 }
	            		
	            		//SI el delimitador que llego no genera token procedemos a comparar lo que se guardo en la lista 
	            		String cadena = token.imprimirLista(lista); // guardamos la variable para no consumir tanta memoria
	            		outputToken.agregarLexema(cadena,linea);
	            		
	            		if(token.isComentario(cadena)) {
	            			i = strLinea.length(); //Terminamos de leer la linea porque fue un comentario, es decir, lo siguiente cuenta como el mismo comentario
	            		}
	            		lista.clear(); 
	            	} 
	            	else { //SI NO FUE DELIMITADOR  
	            	// PARA GUARDAR todos los caracteres de un string aunque tenga espacios
	            		if(strLinea.charAt(i) == '\"') { //si llegan comillas
	            			lista.add(strLinea.charAt(i)); //agregamos las comllas iniciales que indican el inicio del string
	            			
	            				if(strLinea.length()-1>i) { //if por si llega una comilla doble sola
	            					i++; //saltamos a la posicion siguiente para comenzar a meter los caracteres a la lista
	            			
	            					while(strLinea.charAt(i) != '\"') { //mientras no sean las comillas finales (finales porque en el if anterior fueron las iniciales
	            						if(strLinea.charAt(i) == '\\' ) { //verificar si hay algun caracter escapado
	            							if(strLinea.charAt(i+1) == '\\' || strLinea.charAt(i+1) == '\"') i++; //para no guardar en el string el backslash de escape es decir, lo saltamos
	            						}
	            						lista.add(strLinea.charAt(i)); //agregamos el caracter a la lista
	            						if(strLinea.length()-1>i)i++; //avanzamos de posicion para que el while valide de nuevo 
	            						else break;
	            					}
	            					if(strLinea.charAt(i) == '\"') lista.add(strLinea.charAt(i)); // si llegaron comillas significa que son las comillas finales por lo que las agregamos al string
	            				}
	            		}else {
	            			lista.add(strLinea.charAt(i)); //el caracter se agregara a la lista para despues ser evaluado
	            			}

	            		cadenaDel = token.imprimirLista(listaDel); //guardamos la variable 
	            		outputToken.agregarLexema(cadenaDel, linea);
	            		
	            		
            			listaDel.clear(); //limpiamos la lista para el proximo ciclo no se junten caracteres
            		}
	            
	             }//Cerrar for de leer caracteres de la linea
	             
	             
	             //Si quedo algo en las listas debe ser impreso ya que no llegara delimitador de EOLN
	             if(!lista.isEmpty() || !listaDel.isEmpty()) { //si las listas no estan vacias al terminar de leer la linea se imprime lo que hay dentro 
	            	 String cadena = token.imprimirLista(lista);
	            	 
	            	 String cadenaDelim = token.imprimirLista(listaDel);
	            	 outputToken.agregarLexema(cadena, linea);
	            		            	
	            	//BLOQUE DELIMITADORES JUNTOS AL FINAL DE LINEA
        			if(token.isOperador(cadenaDelim)) {
        				outputToken.agregarLexema(cadenaDelim, linea); // 
        			}
        			else {
        				if(token.isSpecialCharacter(cadenaDelim))  outputToken.agregarLexema(cadenaDelim, linea);
            			else {
            				//Si no fue operador ni caracter especial puede que llegaron simbolos pegados por lo que hay que identificarlos con  el siguiente for
            				for(int k=0;k<cadenaDelim.length();k++) {
            					caracter = ""; //reinicializamos para que no se junten de nuevo los caracterers a verificar si son operadores o caracteres especiales 
            					caracter += cadenaDelim.charAt(k); //caracter a comparar
            					outputToken.agregarLexema(caracter, linea);
            				}
            				//caracter = "";//para que no se repita al final, es decir, que no se quede ninguno //VERIFICAR SI AFECTA QUE LO QUITEMOS
            			}
        			}
	            	
	           }//END if lista empty
	            
			lista.clear(); //Limpiamos la lista por el EOLN
	        listaDel.clear();//limpiamos la lista de los delimitadores por el EOLN
	     }//Cerrar while leer linea
	         DIS.close(); // Cerramos el archivo
	       

		}catch (Exception e){ /*System.err.println("Ocurrio un error: " + e.getMessage());*/  e.printStackTrace(); }
		
		outputToken.imprimitTablaTokens();
		//Terminamos de leer el archivo
	}//MAIN
}//CLASS
