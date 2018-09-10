package Lexico;
//REGLAS
import java.util.ArrayList;
import java.util.List;

public class Token {
	
	String tipoOperador, listaString;
	boolean valor;
	List<String> lexemas;
	
	public Token() {
		tipoOperador = "";
		listaString = "";
		valor = false;
		lexemas = new ArrayList<String>();
	}

	public boolean isPalabraReservada(String palabra) {
		switch(palabra) { 
		case "PROG": case "VAR": case "PROC": case "INICIO": case "FIN": 
		case "ENTER": case "REAL": case "STRING": case "LIMPIAR": case "VEXY": 
		case "LEER": case "ESCRIBIR": case "REPITE": case "HASTA": case "MIENTRAS": 
		case "SI": case "SINO": case "EJECUTA": case "AND": case "OR": return true;
		default: return false;
		}
	}
	
	public boolean isOperador(String operador) {
		switch(operador) {
		case "+": case "-": case "*": case "/":
		case "<": case "<=": case "<>": case ">": case ">=": case "=": 
		case "&&": case "||": case "!": return true;     
		default: return false;
		}
	}
	
	public String getTipoOperador(String operador) {
		switch(operador) {
		case "+": case "-": case "*": case "/": tipoOperador = "Aritmetico"; break;
		case "<": case "<=": case "<>": case ">": case ">=": case "=": tipoOperador = "Relacional"; break;
		case "&&": case "||": case "!": tipoOperador = "Logico"; break;
		}
		return tipoOperador;
	}
	
	public boolean isSpecialCharacter(String character) {
		switch(character){
		case ";": case "[": case "]": case ",": case ":": case "(": case ")": case ":=": return true;
		default: return false;
		}
	}
	
	public boolean isSpecialCharacterNoToken(String character) {   ////////////REVIEW
		switch(character){
		case "\"": case ".": case "\\":return true;//AGREGAR BCO/TAB/EOLN/ EOF
		default: return false;
		}
	}
	
	public boolean isDelimitadorToken(char caracter) {
		//char EOL = System.getProperty("line.separator");
		switch(caracter) {
		case '+': case '-': case '*': case '/': case '<': case '=': 
		case '>': case '&': case '|': case '!': case ';': case '[': 
		case ']': case ',': case ':': case '(': case ')':  
		return true;
		default: return false;
		}
	}
	
	public boolean isDelimitadorNoToken(char caracter) {
		switch(caracter) {
		case ' ': case '\t': return true;
		default: return false;
		}
	}
	
	public boolean isIdentificador(String palabra) {
		if(palabra.matches("^[a-zA-Z].*") && palabra.matches("[^\"]*") && palabra.length()<=6) { //VERIFICAR QUE INICIA CON LETRA y TENGA MENOS o igual a 6 CARACTERES
			 valor = true;
		}
		else valor = false;
		return valor;
	}
	
	public boolean isNumeroEntero(String constante){
		if(constante.matches("\\d+")) return true; //EXPRESON REGULAR SOLO ACEPTA NUMEROS Equivalent to [0-9].
		else return false;
	}
	
	public boolean isNumeroReal(String numero) {
		if(numero.matches(("^\\d+\\.\\d+$"))) return true;
		else return false;	
	}
	
	public boolean isString(String cadena) {
		if(cadena.length() > 1 && cadena.charAt(0) == '\"' && cadena.charAt(cadena.length()-1) == '\"') valor = true;
		else valor = false;
		return valor;
	}
	
	public boolean isComentario(String cadena) {
		if(cadena.length()>2) {
			if(cadena.charAt(0) == '\\' && cadena.charAt(1) == '\\') valor = true;
			else valor = false;
		}else valor=false;
		
		return valor;
	}
	
	public String imprimirLista(List<Object> lista) {
		listaString = "";
		for (Object element: lista) {
           listaString = listaString + element;
        }
		return listaString;	
	}
}//END CLASS
