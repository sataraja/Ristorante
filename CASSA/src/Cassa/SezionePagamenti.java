package Cassa;

import prog.io.ConsoleInputManager;

public class SezionePagamenti {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	ConsoleInputManager in = new ConsoleInputManager();
		while(true) {
			System.out.println("Dim. DaPagare all'inizio del while: " + CASSA.getdimensioneDaPagare());
			String Id_pagante= in.readLine("Digita codice_ID del cliente di cui si vuole registrare il pagamento effettuato: ");
			String pagante= CASSA.Registra_pagamento(Id_pagante);
			System.out.println("Dim. DaPagare dopo chiamata metodo .registra_pagamento: " + CASSA.getdimensioneDaPagare());

			if(pagante!=null) System.out.println("Il cliente avente ID "+ pagante + " è stato rimosso!");
		}
		
		
		
	}
	
}
