package CLIENTE;

import java.io.DataInputStream;
import java.net.InetAddress;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

import prog.io.ConsoleInputManager;

public class CLIENTE {
	static Ordine ordine = new Ordine(); 
	static int ID_CLIENTE=0; 
	static Socket mioSocket1 = null;
	static Socket mioSocket2 = null;

	static int porta1 = 6116; // porta server cucina
	static int porta2 = 6117; // porta server cassa
	static DataOutputStream out1 = null;
	static DataOutputStream out2 = null;


	  public static void main(String[] args) {
		// TODO Auto-generated method stub
		  ConsoleInputManager in= new ConsoleInputManager();
		  boolean invia=false; 
		  boolean logout=false;
           int fase=1;
           
  
         System.out.println("Gent. Cliente, grazie per averci scelto!\n");
       //ASSEGNA ID CLIENTE
         ID_CLIENTE = ASSEGNAZIONE_ID.assegna_id();
           
           
      while(!logout) {
  	
	  
	  switch(fase) {
	  
	  case 1:    // case 1 -----> Aggiungi ordini al carrello
	  String risposta= in.readLine("Scrivete a seguire i numeri associati alle pietanze che volete ordinare (utilizzare formato: n1,n2): \n");
    
	  StringTokenizer st = new StringTokenizer(risposta,",");
	  while(st.hasMoreTokens()){
	        ordine.add(st.nextToken());
	  }
	  clearScreen(); //pulisce console
	  
	   
	  case 2:
	     // Visualizza ordini nel carrello
		  ordine.print();
		  
		  fase = 3; //
		  break;

		  
	  case 3:   //------>  ordina ancora/ visualizza carrello/ invio ordine / elimina un ordine
	  String scelta = in.readLine("Opzioni possibili:\n1) Digita 1 per aggiungere un ordine al carrello\n2) Digita 2 per visualizzare resoconto carrello\n3) Digita 3 per rimuovere un ordine dal carrello\n4) Digita 4 per inviare l'ordine\n");
	  if(scelta.compareTo("1")==0)   fase=1;
	  else if(scelta.compareTo("2")==0)   fase=2; //------
	  else if (scelta.compareTo("4")==0) {
              fase = 5;
	  }
	  
	  else if(scelta.compareTo("3")==0) { 
	     fase=4;
	  }
	  break;
	  
	  
	  
	  case 4: //-------> Elimina degli ordini dal carrello
		  String pietanzedaeliminare = in.readLine("Digita numero pietanze da eliminare da carrello(divise ciascuna da ','): ");
		  StringTokenizer darimuovere = new StringTokenizer(pietanzedaeliminare, ",");
		  while(darimuovere.hasMoreTokens()) {
		  ordine.cancella(darimuovere.nextToken());
		  }
		  clearScreen();
        fase=2;
		  break;
		  
		  
		  
		  
	  case 5: //invio ordine e logout
		  logout=true;
		  InvioOrdine();
		  clear();
		  break;
		  
		  
	  
	  }//switch
	  

	  
		
	  }//while
		
	  
		
		
	} //main
	  
	  
	  

	private static void clear() {
		// TODO Auto-generated method stub
	ordine.cancellatutto();
	}

	
	
	
	
	
	
	private static void invio_ordine() {
		// TODO Auto-generated method stub
		
		try {
			//viene inviato messaggio tramite socket a cucina e cassa, nel formato: id_cliente,prezzo, lista ingredienti
			out1.writeUTF(""+ ID_CLIENTE +","+ ordine.toString());   //invio a cucina
			out1.flush();
			out1.close();
			out2.writeUTF(""+ ID_CLIENTE +","+ ordine.toString());    //invio a cassa
			out2.flush();
			out2.close();
			System.out.println("Dettagli ordine inviati a cassa e cucina: "+ "   "+ ID_CLIENTE +","+ ordine.toString());
		} catch (IOException e) {
		
		}	
		
	}
	
	
	
	public static Socket connetti_cucina() {
		
		try {
			 mioSocket1 = new Socket(InetAddress.getLocalHost(), porta1); //connessione al socket --> il socket lo registriamo nella variabile server, necessario per la comunicazione
			 
			 System.out.println("Connesso con cucina...");

			 out1= new DataOutputStream(mioSocket1.getOutputStream());
            
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
		System.err.println("Impossibile stabilire connessione");
		}
		
		return mioSocket1;
	}

	
	
	
	
	public static Socket connetti_cassa() {
		
		try {
			mioSocket2 = new Socket(InetAddress.getLocalHost(), porta2); //connessione al socket --> il socket lo registriamo nella variabile server, necessario per la comunicazione
			 
			 System.out.println("Connesso con cassa...");

			 out2= new DataOutputStream(mioSocket2.getOutputStream());
            
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
		System.err.println("Impossibile stabilire connessione");
		}
		
		return mioSocket2;
	}
	
	
	public static void InvioOrdine() {
	    
		connetti_cucina();	
	    connetti_cassa();
		invio_ordine();
	}
	
	
	//pulisce console
	public static void clearScreen() {  
		for (int i = 0; i < 50; ++i) System.out.println();
	}  
	
	

}
