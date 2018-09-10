package Lexico;
import java.util.*;

public class Output {
	List<Object> lexemas, numLinea; 
	Object lexema;
	int numToken = 0, linea;
	int contID = 80, contConst=100, contComent=150, contError = 400;
	Token tok = new Token();
	
	public Output() {
		lexemas = new ArrayList<Object>();
		lexema = "";
		numToken = 0;
		numLinea = new ArrayList<Object>();
		linea = 0;
	}
	
	public void agregarLexema(String lexema, int linea) {
		lexemas.add(lexema);
		numLinea.add(linea);
	}
	
	public void imprimirLexemas() {
		for(int i=0;i<lexemas.size();i++) {
			System.out.println("LEX:  " + lexemas.get(i)  + "   Linea: " + numLinea.get(i));
		}
	}
	
	public void limpiarLexemasFull(int Num) {
		for(int i=0;i<=Num;i++) {
			limpiarLexemas();
		}
	}
	
	public void limpiarLexemas() {
		for(int i=0;i<lexemas.size();i++) {
			if(lexemas.get(i).equals(" ") || lexemas.get(i).equals("") ) {
				lexemas.remove(i);
				numLinea.remove(i);
			}
		}
	}
	
	public void imprimitTablaTokens() {
		 System.out.format("%20s", "lexema" + " | " );
		 System.out.format("%25s", "Tipo" + " | " );
		 System.out.format("%10s", "Token" + " | " );
		 System.out.format("%10s", "Linea" + " | " );
		 System.out.println();
		
		 for(int i=0;i<lexemas.size();i++) {
			lexema = lexemas.get(i);
			numToken = getNumToken((String)lexema);
			linea = (int) numLinea.get(i);
			
			if(tok.isPalabraReservada((String) lexema)) imprimirEnTabla("%10s", lexema, "Palabra reservada", numToken, linea);
			else if(tok.isOperador((String) lexema)) imprimirEnTabla("%10s", lexema, "Operador " + tok.getTipoOperador((String) lexema), numToken, linea);
			else if(tok.isSpecialCharacter((String) lexema))  imprimirEnTabla("%10s", lexema, "Caracter especial", numToken, linea);
			else if(tok.isIdentificador((String) lexema))  imprimirEnTabla("%10s", lexema, "Identificador", numToken, linea);
			else if(tok.isString((String) lexema))  imprimirEnTabla("%10s", lexema,"Constante string", numToken, linea);
			else if(tok.isNumeroEntero((String) lexema))  imprimirEnTabla("%10s", lexema,"Constante entero", numToken, linea);
			else if(tok.isNumeroReal((String) lexema))  imprimirEnTabla("%10s", lexema,"Constante real", numToken, linea);
			else if(tok.isComentario((String) lexema))  imprimirEnTabla("%10s", lexema,"Comentario", numToken, linea);
			else  imprimirEnTabla("%10s", lexema, "Error",numToken, linea);
		}
	}
	
	public void imprimirEnTabla(String formato, Object lexem, String tipo, int numTok, int lin) {
		System.out.format("%20s", lexem + " | " );
		System.out.format("%25s", tipo + " | " );
		System.out.format("%10s", numTok + " | " );
		System.out.format("%10s", lin + " | " );
		System.out.println();
	}
	
	public int getNumToken(String token) {
		switch(token) {
		//PALABRAS RESERVADAS
		case "PROG": return -1; case "VAR": return -2; case "PROC": return -3; case "INICIO": return -4; case "FIN": return -5;
		case "ENTER":return -6; case "REAL": return -7; case "STRING": return -8; case "LIMPIAR": return -9; case "VEXY": return -10; 
		case "LEER": return -11; case "ESCRIBIR": return -12; case "REPITE": return -13; case "HASTA": return -14; case "MIENTRAS": return -15; 
		case "SI": return -16; case "SINO": return -17; case "EJECUTA": return -18; case "AND": return -19; case "OR": return -20;
		
		case "+": return -31; case "-": return -32; case "*": return -33; case "/": return -34;
		case "<": return -41; case "<=": return -42; case "<>": return -43; case ">": return -44; case ">=": return -45; case "=":  return -46;
		case "&&": return -51; case "||": return -52; case "!": return -53;
		
		case ";": return -61; case "[": return -62; case "]": return -63; case ",": return -64; 
		case ":": return -65; case "(": return -66; case ")": return -67; case ":=": return -68;
		default:
			
			if(tok.isIdentificador(token)) {
				return getTokenId();
			}else {
				if(tok.isString(token) || tok.isNumeroEntero(token) || tok.isNumeroReal(token)) {
					return getTokenConst();
				} else {
					if(tok.isComentario(token)) return getTokenComent();
					else return getTokenError();
				}
				
			}
		}//end switch  
	}//end getNumToken
	
	public int getTokenId() {
		contID+=1;
		return contID;
	}
	
	public int getTokenConst() {
		contConst+=1;
		return 	contConst;
	}
	
	public int getTokenComent() {
		contComent+=1;
		return 	contComent;
	}
	
	public int getTokenError() {
		contError+=1;
		return 	contError;
	}
} //END CLASS
