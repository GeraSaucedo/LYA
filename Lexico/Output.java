package Lexico;

import java.util.*;

public class Output {
	List lexemas; 
	
	public Output() {
		lexemas = new ArrayList();
	}
	
	
	
	public static void main(String [] KrustierDock) {
		Output op = new Output();
		op.agregarLexema("A");
		op.agregarLexema("");
		op.agregarLexema("B");
		op.agregarLexema("");
		op.agregarLexema("C");
		op.agregarLexema("");
		op.agregarLexema("D");
		op.imprimirLexemas();
		
		
	}
	
	public void agregarLexema(String lexema) {
		lexemas.add(lexema);
	}
	
	public void imprimirLexemas() {
		for(int i=0;i<lexemas.size();i++) {
			System.out.println("LEX:  "+"\"" + lexemas.get(i) + "\"" );
		}
	}
	
	public void limpiarLexemasFull(int Num) {
		for(int i=0;i<=Num;i++) {
			limpiarLexemas();
		}
	
	}
	
	public void limpiarLexemas() {
		String valorLex, valorQuitar;
		
		for(int i=0;i<lexemas.size();i++) {
			valorLex = (String)lexemas.get(i);
	
			if(valorLex.equals(" ") || valorLex.equals("") ) {
				lexemas.remove(i);
				
			}

		}
	}
	
	
	
	
	
}
