package Cassa;

import prog.io.ConsoleInputManager;

public class SezionePagamenti {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	ConsoleInputManager in = new ConsoleInputManager();
		while(true) {
			String Id_pagante= in.readLine("Digita codice_ID del cliente di cui si vuole registrare il pagamento effettuato: ");
			CASSA.Registra_pagamento(Id_pagante);
		}
		
		
		
	}
	
}
